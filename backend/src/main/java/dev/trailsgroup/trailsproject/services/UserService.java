package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.*;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.ProfessorCourse;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private Pbkdf2PasswordEncoder pe;

    @Autowired
    private UserRepository repository;

    @Autowired
    private StaticFileService staticFileService;

    @Lazy
    @Autowired
    private UserCourseService userCourseService;

    @Lazy
    @Autowired
    private StudentCompetenceService studentCompetenceService;

    public List<User> findAll(Pageable pageable){
        return repository.findAll();
    }

    protected User findById(Integer id){
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return user;
    }

    public UserSSDTO findByIdAndVerify(Integer id){
        UserSS user = authenticated();

        if(user==null || !user.hasRole(UserProfiles.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado!");
        }

        return convertUsertoUserSS(findById(id));
    }

    public User findBySession(){
        return repository.findById(authenticated().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Houve um problema!"));
    }


    public static UserSS authenticated(){
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch(Exception e){
            return null;
        }
    }

    public boolean verifyPermission(Course course){
        User user = repository.findByEmail(authenticated().getUsername()).get();
        ProfessorCourse professorCourse = new ProfessorCourse(course, user);
        return userCourseService.findProfessorCourse(professorCourse).isEmpty();
    }

    public User insert(UserDTO obj){
        try {
            if(verifyEmailAvailability(obj.getEmail()))
                throw new DatabaseException("O email já foi cadastrado no sistema! Informe outro email.");

            User user = new User(null,
                    obj.getName(),
                    pe.encode(obj.getPassword()),
                    obj.getEmail(),
                    "default-user.png",
                    obj.getStatus());

            User dbUser = repository.save(user);
            if(dbUser.getId() == 1) {
                dbUser.addProfile(UserProfiles.PROFESSOR);
                dbUser.addProfile(UserProfiles.ADMIN);
                repository.save(dbUser);
            }
            return dbUser;

        }catch (DataIntegrityViolationException e ){
            throw new DatabaseException(e.getMessage());
        }
    }

    public boolean verifyEmailAvailability(String email){
        return repository.findByEmail(email).isPresent();
    }

    private void verifyUpdateInformationPermission(Integer id){
        UserSS user = authenticated();
        if(user == null || !Objects.equals(user.getId(), id) && !user.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Acesso negado! Você não pode editar as informações deste usuário");
        }
    }

    public void delete(Integer id){
        try{
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch  (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public void removeProfile(User user, UserProfiles profile){
        user.removeProfile(profile);
        save(user);
    }

    public User update(Integer id, UserDTO obj){
        try{
            User userDatabase = repository.getById(id);
            userUpdateInformation(userDatabase, obj);
            return repository.save(userDatabase);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void userUpdateInformation(User userDataBase, UserDTO obj){
        userDataBase.setName(obj.getName());
        userDataBase.setEmail(obj.getEmail());
        userDataBase.setStatus(obj.getStatus());
        userDataBase.setPassword(pe.encode(obj.getPassword()));
    }

    //Solution:
    //https://stackoverflow.com/questions/37749559/conversion-of-list-to-page-in-spring
    public Page<Course> getCourses(Integer id, Pageable pageable){
        UserSS user = authenticated();

        if(user == null || !Objects.equals(user.getId(), id) && !user.hasRole(UserProfiles.ADMIN)){
            throw new AuthorizationException("Acesso negado!");
        }

        List<Course> courses = new ArrayList<>(repository.getById(id).getCourses());
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), courses.size());
        //This solution doesn't allow Sort with Pageable. Need to see another way.
        //https://stackoverflow.com/questions/56946999/can-we-sort-listt-from-spring-boot-pageable-or-sort-page-from-pageimpl
        return new PageImpl<Course>(courses.subList(start, end), pageable, courses.size());
    }

    public List<ProfessorDTO> getProfessors() {
        List<User> users = repository.findAll();
        List<ProfessorDTO> professorDTOList = new ArrayList<>();
        for(User x : users){
            if(x.getProfiles().contains(UserProfiles.PROFESSOR)) {
                ProfessorDTO prof = new ProfessorDTO();
                prof.setEmail(x.getEmail());
                prof.setName(x.getName());
                prof.setId(x.getId());
                professorDTOList.add(prof);
            }
        }
        return professorDTOList;
    }

    public User findByEmail(String userEmail) {
        return repository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("Identificador "+ userEmail +" para usuário não encontrado!"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void makeProfessor(User user) {
        user.addProfile(UserProfiles.PROFESSOR);
        repository.save(user);
    }

    public void flush(){
        repository.flush();
    }

    public UserSSDTO updateEmail(Integer id, EmailDTO obj) {
        verifyUpdateInformationPermission(id);
        User user = findById(id);
        user.setEmail(obj.getEmail());
        return convertUsertoUserSS(save(user));
    }

    public UserSSDTO updateName(Integer id, UserNameDTO obj) {
        verifyUpdateInformationPermission(id);
        User user = findById(id);
        user.setName(obj.getName());
        return convertUsertoUserSS(save(user));
    }

    public UserSSDTO updatePassword(Integer id, UserPasswordDTO obj) {
        verifyUpdateInformationPermission(id);
        User user = findById(id);
        if(!pe.matches(obj.getOldPassword(), user.getPassword()))
            throw new AuthorizationException("A senha informada para autenticação da operação está errada!");

        user.setPassword(pe.encode(obj.getPassword()));
        return convertUsertoUserSS(save(user));
    }

    private UserSSDTO convertUsertoUserSS(User user ){
        List<CompetencePointsDTO> competencePointsDTO = studentCompetenceService.getUserPoints(user);
        UserSSDTO userSSDTO = new UserSSDTO(user.getId(), user.getEmail(), user.getName(), "xxxxxx", user.getProfiles(), user.getStatus(), user.getImagePath());
        userSSDTO.setCompetencePoints(competencePointsDTO);
        return userSSDTO;
    }

    public UserSSDTO insertImage(MultipartFile multipartFile, Integer id){
        verifyUpdateInformationPermission(id);
        User user = findById(id);
        user.setImage(staticFileService.update(multipartFile, user.getImageName()));
        save(user);
        return convertUsertoUserSS(user);
    }
}

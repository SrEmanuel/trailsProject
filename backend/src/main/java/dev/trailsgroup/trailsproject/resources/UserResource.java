package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.*;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.UserCourseService;
import dev.trailsgroup.trailsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @Autowired
    private UserCourseService userCourseService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id){
        User obj = service.findByIdAndVerify(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<User> insert(@Valid @RequestBody UserDTO user){
        User obj = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/{id}/add-course")
    public ResponseEntity<UserCourseDTO> addCourse(@PathVariable Integer id, @RequestParam(name = "course") Integer courseId){
        UserCourseDTO obj = userCourseService.insertProfessorCourse(id, courseId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/add-course")
                .buildAndExpand(obj).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}/remove-course")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id, @RequestParam(name = "course") Integer courseId){
        userCourseService.deleteProfessorCourse(id, courseId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>  delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/update/email")
    public ResponseEntity<UserSS> updateEmail(@PathVariable Integer id, @RequestBody @Valid EmailDTO email){
        UserSS obj = service.updateEmail(id, email);
        return ResponseEntity.ok().body(obj);
    }
    @PutMapping(value = "/{id}/update/name")
    public ResponseEntity<UserSS> updateName(@PathVariable Integer id, @RequestBody @Valid UserNameDTO nameDTO){
        UserSS obj = service.updateName(id, nameDTO);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping(value = "/{id}/update/password")
    public ResponseEntity<UserSS> updateName(@PathVariable Integer id, @RequestBody @Valid UserPasswordDTO userPasswordDTO){
        UserSS obj = service.updatePassword(id, userPasswordDTO);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/courses")
    public ResponseEntity<Page<Course>> getCourses(@PathVariable Integer id, Pageable pageable){
        return ResponseEntity.ok().body(service.getCourses(id, pageable));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping(value = "/{id}/add-image")
    public ResponseEntity<UserSS> insertImage(@RequestPart(value = "image") MultipartFile file, @PathVariable Integer id) {
        UserSS obj = service.insertImage(file, id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "/me")
    public ResponseEntity<User> getCurrentUser(){
        return ResponseEntity.ok().body(service.findBySession());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/professors")
    public ResponseEntity<List<ProfessorDTO>> getProfessors(){
        return ResponseEntity.ok().body(service.getProfessors());
    }
}

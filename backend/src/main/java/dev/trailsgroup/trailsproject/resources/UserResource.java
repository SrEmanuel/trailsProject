package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.dto.UserDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.services.UserCourseService;
import dev.trailsgroup.trailsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    //TODO IMPLEMENT AUTHENTICATION

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
        User obj = service.findById(id);
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
        UserCourseDTO obj = userCourseService.insert(id, courseId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/add-course")
                .buildAndExpand(obj).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}/remove-course")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id, @RequestParam(name = "course") Integer courseId){
        userCourseService.delete(id, courseId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>  delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update( @PathVariable Integer id, @Valid @RequestBody UserDTO user){
        User obj = service.update(id, user);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/courses")
    public ResponseEntity<Page<Course>> getCourses(@PathVariable Integer id, Pageable pageable){
        return ResponseEntity.ok().body(service.getCourses(id, pageable));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "/me")
    public ResponseEntity<User> getCurrentUser(){
        return ResponseEntity.ok().body(service.findBySession());
    }
}

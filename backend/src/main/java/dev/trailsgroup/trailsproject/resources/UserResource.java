package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.services.UserCourseService;
import dev.trailsgroup.trailsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    //TODO IMPLEMENT AUTHENTICATION

    @Autowired
    private UserService service;

    @Autowired
    private UserCourseService userCourseService;


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
    public ResponseEntity<User> insert(@RequestBody User obj){
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PostMapping(value = "/{id}/add-course")
    public ResponseEntity<UserCourseDTO> addCourse(@PathVariable Integer id, @RequestParam(name = "course") Integer courseId){
        UserCourseDTO obj = userCourseService.insert(id, courseId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/add-course")
                .buildAndExpand(obj).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>  delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update( @PathVariable Integer id, @RequestBody User obj){
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/courses")
    public ResponseEntity<Set<Course>> getCourses(@PathVariable Integer id){
        Set<Course> courses = service.getCourses(id);
        return ResponseEntity.ok().body(courses);
    }




}

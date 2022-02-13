package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.UserCourseDTO;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.entities.UserCourse;
import dev.trailsgroup.trailsproject.services.UserCourseService;
import dev.trailsgroup.trailsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    //TODO IMPLEMENT AUTHENTICATION

    //TODO IMPLEMENT ADD COURSE ENDPOINT

    @Autowired
    private UserService service;

    @Autowired
    private UserCourseService userCourseService;


    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
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

    @PostMapping(value = "/add-course")
    public ResponseEntity<UserCourseDTO> addCourse(@RequestBody UserCourseDTO obj){
        userCourseService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/add-course")
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




}

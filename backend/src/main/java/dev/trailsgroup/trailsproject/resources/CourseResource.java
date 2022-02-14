package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping(value = "/courses")
public class CourseResource {

    //TODO IMPLEMENT AUTHENTICATION

    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<Page<Course>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> findById(@PathVariable Integer id){
        Course obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Course> insert(@RequestBody Course obj){
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>  delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Course> update( @PathVariable Integer id, @RequestBody Course obj){
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/topics")
    public ResponseEntity<Set<Topic>> getCourses(@PathVariable Integer id){
        Set<Topic> courses = service.getTopics(id);
        return ResponseEntity.ok().body(courses);
    }



}

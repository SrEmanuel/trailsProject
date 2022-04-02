package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.CourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.services.CourseService;
import dev.trailsgroup.trailsproject.services.TopicService;
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

@RestController
@RequestMapping(value = "/courses")
public class CourseResource {


    @Autowired
    private CourseService service;

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<Page<Course>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{linkName}")
    public ResponseEntity<Course> findByName(@PathVariable String linkName){
        Course obj = service.findByName(linkName);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Course> insert(@Valid @RequestPart CourseDTO course, @RequestParam(value = "image", required = false) MultipartFile imageFile){

        Course obj = service.insert(course, imageFile);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @DeleteMapping(value = "/{linkName}")
    public ResponseEntity<Void>  delete(@PathVariable String linkName){
        service.delete(linkName);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PutMapping(value = "/{linkName}")
    public ResponseEntity<Course> update(@PathVariable String linkName, @Valid @RequestPart CourseDTO course, @RequestParam(value = "image", required = false) MultipartFile imageFile){
        Course obj = service.update(linkName, course, imageFile);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{linkName}/topics")
    public ResponseEntity<Page<Topic>> getTopics(@PathVariable String linkName, Pageable pageable){
        return ResponseEntity.ok().body(topicService.getTopicsByCourse(linkName, pageable));
    }



}

package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.CourseDTO;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.services.CourseService;
import dev.trailsgroup.trailsproject.services.StaticFileService;
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
import java.nio.file.Path;

@RestController
@RequestMapping(value = "/courses")
public class CourseResource {

    //TODO IMPLEMENT AUTHENTICATION

    @Autowired
    private CourseService service;

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<Page<Course>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> findById(@PathVariable Integer id){
        Course obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<Course> insert(@Valid @RequestPart CourseDTO course, @RequestParam(value = "image", required = false) MultipartFile imageFile){

        Course obj = service.insert(course, imageFile);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>  delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Course> update(@PathVariable Integer id, @Valid @RequestPart CourseDTO course, @RequestParam(value = "image", required = false) MultipartFile imageFile){
        Course obj = service.update(id, course, imageFile);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/topics")
    public ResponseEntity<Page<Topic>> getTopics(@PathVariable Integer id, Pageable pageable){
        return ResponseEntity.ok().body(topicService.getTopicsByCourse(id, pageable));
    }



}

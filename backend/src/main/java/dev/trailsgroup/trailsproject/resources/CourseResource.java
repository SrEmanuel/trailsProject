package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.CourseDTO;
import dev.trailsgroup.trailsproject.dto.SubjectPositionDTO;
import dev.trailsgroup.trailsproject.dto.TopicPositionDTO;
import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;
import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.services.CourseService;
import dev.trailsgroup.trailsproject.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
    public ResponseEntity<Course> insert(@Validated({CreateInfo.class, UpdateInfo.class})@RequestBody CourseDTO course){

        Course obj = service.insert(course);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping(value = "/{linkName}/add-image")
    public ResponseEntity<Course> insertImage(@RequestPart(value = "image") MultipartFile file, @PathVariable String linkName) {
        Course obj = service.insertImage(file, linkName);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }




    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PutMapping(value = "/{linkName}")
    public ResponseEntity<Course> update(@PathVariable String linkName, @Validated(CreateInfo.class) @RequestBody CourseDTO course){
        Course obj = service.update(linkName, course);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{linkName}/topics")
    public ResponseEntity<Page<Topic>> getTopics(@PathVariable String linkName, Pageable pageable){
        return ResponseEntity.ok().body(topicService.getTopicsByCourse(linkName, pageable));
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping(value = "/{linkName}/update-positions")
    public ResponseEntity<List<Topic>> updateSubjectPositions(@Valid @RequestBody List<TopicPositionDTO> topicPositionDTO, @PathVariable String linkName){
        List<Topic> obj = service.updateTopicPositions(linkName, topicPositionDTO);
        return ResponseEntity.ok().body(obj);
    }



}

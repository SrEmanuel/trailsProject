package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.SubjectPositionDTO;
import dev.trailsgroup.trailsproject.dto.TopicDTO;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/topics")
public class TopicResource {
    @Autowired
    private TopicService service;

    @GetMapping
    public ResponseEntity<Page<Topic>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{linkName}")
    public ResponseEntity<Topic> findById(@PathVariable String linkName){
        Topic obj = service.findByName(linkName);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<Topic> insert(@Validated @RequestBody TopicDTO topic){
        Topic obj = service.insert(topic);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping(value = "/{linkName}/update-positions")
    public ResponseEntity<Topic> updateSubjectPositions(@Valid @RequestBody List<SubjectPositionDTO> subjectPositionDTOList, @PathVariable String linkName){
        Topic obj = service.updateSubjectPositions(linkName, subjectPositionDTOList);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @DeleteMapping(value = "/{linkName}")
    public ResponseEntity<Void>  delete(@PathVariable String linkName){
        service.delete(linkName);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PutMapping(value = "/{linkName}")
    public ResponseEntity<Topic> update( @PathVariable String linkName,  @Validated(UpdateInfo.class) @RequestBody TopicDTO topic){
        Topic obj = service.update(linkName, topic);
        return ResponseEntity.ok().body(obj);
    }




}

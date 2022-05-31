package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.SubjectDTO;
import dev.trailsgroup.trailsproject.dto.validationGroups.CreateInfo;
import dev.trailsgroup.trailsproject.dto.validationGroups.UpdateInfo;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectResource {

    @Autowired
    private SubjectService service;

    @GetMapping
    public ResponseEntity<Page<Subject>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{linkName}")
    public ResponseEntity<Object> findByName(@PathVariable String linkName){
        return ResponseEntity.ok().body(service.findByName(linkName));
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<Subject> insert(@Validated(CreateInfo.class) @RequestBody SubjectDTO subject){
        Subject obj = service.insert(subject);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping(value = "/{linkName}/add-image")
    public ResponseEntity<Subject> insertImage(@RequestPart(value = "image") MultipartFile file, @PathVariable String linkName) {
        Subject obj = service.insertImage(file, linkName);
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
    public ResponseEntity<Subject> update(@PathVariable String linkName, @Validated(UpdateInfo.class) @RequestBody SubjectDTO subject){
        Subject obj = service.update(linkName, subject);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping(value = "/{linkName}/user/mark")
    public ResponseEntity<Void> markUserProgress(@PathVariable String linkName, @RequestParam(name = "state") boolean state){
        service.markUserProgress(state, linkName);
        return ResponseEntity.noContent().build();
    }
}

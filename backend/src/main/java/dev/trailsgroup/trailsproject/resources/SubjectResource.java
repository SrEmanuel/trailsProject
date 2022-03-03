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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectResource {

    //TODO IMPLEMENT AUTHENTICATION

    @Autowired
    private SubjectService service;

    @GetMapping
    public ResponseEntity<Page<Subject>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Subject> findById(@PathVariable Integer id){
        Subject obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Subject> insert(@Validated @RequestPart SubjectDTO subject, @RequestParam(value = "image") MultipartFile imageFile){
        Subject obj = service.insert(subject, imageFile);
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
    public ResponseEntity<Subject> update(@PathVariable Integer id, @Validated(UpdateInfo.class) @RequestPart SubjectDTO subject,  @RequestParam(value = "image") MultipartFile imageFile){
        Subject obj = service.update(id, subject, imageFile);
        return ResponseEntity.ok().body(obj);
    }




}

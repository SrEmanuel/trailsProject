package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.SubjectDTO;
import dev.trailsgroup.trailsproject.entities.Subject;
import dev.trailsgroup.trailsproject.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectResource {

    //TODO IMPLEMENT AUTHENTICATION

    @Autowired
    private SubjectService service;

    @GetMapping
    public ResponseEntity<List<Subject>> findAll(){
        List<Subject> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Subject> findById(@PathVariable Integer id){
        Subject obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Subject> insert(@RequestBody SubjectDTO Subject){
        Subject obj = service.insert(Subject);
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
    public ResponseEntity<Subject> update(@PathVariable Integer id, @RequestBody Subject obj){
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }




}

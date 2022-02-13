package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.TopicDTO;
import dev.trailsgroup.trailsproject.entities.Topic;
import dev.trailsgroup.trailsproject.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/topics")
public class TopicResource {

    //TODO IMPLEMENT AUTHENTICATION

    @Autowired
    private TopicService service;

    @GetMapping
    public ResponseEntity<List<Topic>> findAll(){
        List<Topic> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Topic> findById(@PathVariable Integer id){
        Topic obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Topic> insert(@RequestBody TopicDTO topic){
        Topic obj = service.insert(topic);
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
    public ResponseEntity<Topic> update( @PathVariable Integer id, @RequestBody Topic obj){
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }




}

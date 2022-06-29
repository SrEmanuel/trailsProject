package dev.trailsgroup.trailsproject.resources;


import dev.trailsgroup.trailsproject.dto.InputQuestionAnswerDTO;
import dev.trailsgroup.trailsproject.dto.OutputQuestionAnswerDTO;
import dev.trailsgroup.trailsproject.dto.QuestionCompetenceDTO;
import dev.trailsgroup.trailsproject.dto.QuestionDTO;

import dev.trailsgroup.trailsproject.entities.Question;
import dev.trailsgroup.trailsproject.entities.QuestionCompetence;
import dev.trailsgroup.trailsproject.services.QuestionCompetenceService;
import dev.trailsgroup.trailsproject.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/questions")
public class QuestionResource {

    @Autowired
    private QuestionService service;

    @Autowired
    private QuestionCompetenceService questionCompetenceService;

    @GetMapping
    public ResponseEntity<Page<Question>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findByName(@PathVariable Integer id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<Question> insert(@Valid @RequestBody QuestionDTO question){
        Question obj = service.insert(question);
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
    public ResponseEntity<Question> update(@PathVariable Integer id, @Valid @RequestBody QuestionDTO question){
        Question obj = service.update(id, question);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping(value = "/{id}/verifyAnswer")
    public ResponseEntity<OutputQuestionAnswerDTO> verifyAnswer(@PathVariable Integer id,
                                                                @Valid @RequestBody InputQuestionAnswerDTO answerDTO){
        OutputQuestionAnswerDTO obj = service.verifyAnswer(id, answerDTO);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping(value = "/{id}/addCompetence")
    public ResponseEntity<QuestionCompetence> addCompetence(@PathVariable Integer id, @RequestBody QuestionCompetenceDTO questionCompetence){
        QuestionCompetence obj = questionCompetenceService.save(id, questionCompetence);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

}
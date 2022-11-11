package br.com.introductionunittests.introductionunittestsspringboot.controllers;

import br.com.introductionunittests.introductionunittestsspringboot.models.UseerDTO;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerRequestDTO;
import br.com.introductionunittests.introductionunittestsspringboot.services.UseerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UseerController {

    @Autowired
    private UseerService service;


    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UseerDTO> findbyId(@PathVariable Integer id){

        return ResponseEntity.ok(service.findById(id));

    }
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<UseerDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody @Valid UseerRequestDTO dto){
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UseerDTO> create(@RequestBody @Valid UseerRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
}

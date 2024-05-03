package com.tecnico.attus.controller;

import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.PersonAddressDTO;
import com.tecnico.attus.model.dto.PersonDTO;
import com.tecnico.attus.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/person")
@Api(value = "API Person", description = "Teste técnico Java")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonAddressDTO> createPerson(@RequestBody PersonAddressDTO requestDTO) throws ParseException {
        return ResponseEntity.ok(personService.createPerson(requestDTO));
    }

    @GetMapping("getPeopleWithMainAddress")
    public ResponseEntity<List<PersonAddressDTO>> getPeopleWithMainAddress() {
        return ResponseEntity.ok(personService.getPeopleWithMainAddress());
    }

    @Operation(summary = "Atualizar pessoa", description = "Atualiza pessoa passando o ID da pessoa como parâmetro")
    @PutMapping("/{id}")
    public ResponseEntity<PersonAddressDTO> updatePerson(@PathVariable Integer id, @RequestBody PersonDTO person) throws ParseException {
        return ResponseEntity.ok(personService.updatePerson(id, person));
    }

    @Operation(summary = "Consultar pessoa por ID", description = "Consulta por ID, será realizado a busca com base no id passado nos parâmetros")
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @Operation(summary = "Consulta pessoa", description = "Realiza a listagem de todas as pessoas, juntamente com seus endereços")
    @GetMapping
    public ResponseEntity<List<PersonAddressDTO>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

}

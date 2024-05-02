package com.tecnico.attus.controller;

import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.PersonDTO;
import com.tecnico.attus.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PersonDTO createPerson(@RequestBody PersonDTO requestDTO) throws ParseException {
        return personService.createPerson(requestDTO);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @Operation(
            summary = "Consult all Persons",
            description = "Consult a list of people, along with all their associated addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return personService.getAllPersons();
    }
}

package com.tecnico.attus.controller;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.services.AddressService;
import com.tecnico.attus.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

}

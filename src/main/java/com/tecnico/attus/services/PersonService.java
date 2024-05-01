package com.tecnico.attus.services;

import com.tecnico.attus.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    Person createPerson(Person person);
    Person updatePerson(Integer id, Person person);
    Person getPersonById(Long id);
    List<Person> getAllPersons();

}
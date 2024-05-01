package com.tecnico.attus.services.impl;

import com.tecnico.attus.model.Person;
import com.tecnico.attus.repository.PersonRepository;
import com.tecnico.attus.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person createPerson(Person person) {
        return null;
    }

    @Override
    public Person updatePerson(Integer id, Person person) {
        Person personRequest = Person.builder()
                .id(person.getId())
                .fullName(person.getFullName())
                .birthDate(person.getBirthDate())
                .build();

        return null;
    }

    @Override
    public Person getPersonById(Long id) {
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
        return List.of();
    }

}

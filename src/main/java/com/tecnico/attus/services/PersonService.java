package com.tecnico.attus.services;

import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

public interface PersonService {

    PersonDTO createPerson(PersonDTO PersonWithAddressesRequest) throws ParseException;
    Person updatePerson(Integer id, Person person);
    Person getPersonById(Long id);
    List<PersonDTO> getAllPersons();

}
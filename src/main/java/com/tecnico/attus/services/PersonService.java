package com.tecnico.attus.services;

import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.PersonAddressDTO;
import com.tecnico.attus.model.dto.PersonDTO;

import java.text.ParseException;
import java.util.List;

public interface PersonService {

    PersonAddressDTO createPerson(PersonAddressDTO PersonWithAddressesRequest) throws ParseException;
    Person updatePerson(Integer id, PersonDTO person) throws ParseException;
    Person getPersonById(Long id);
    List<PersonAddressDTO> getAllPersons();

}
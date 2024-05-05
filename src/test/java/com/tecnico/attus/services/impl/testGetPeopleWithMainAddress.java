//package com.tecnico.attus.services.impl;
//
//import com.tecnico.attus.model.Adresses;
//import com.tecnico.attus.model.Person;
//import com.tecnico.attus.model.dto.PersonAddressDTO;
//import com.tecnico.attus.model.dto.PersonDTO;
//import com.tecnico.attus.repository.PersonRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class testGetPeopleWithMainAddress {
//
//    @Mock
//    private PersonRepository personRepository;
//
//    @InjectMocks
//    private PersonServiceImpl service;
//
//    @Mock
//    private Person person;
//    private PersonDTO personDTO;
//
//    private PersonAddressDTO personAddressDTO;
//
//
//    @BeforeEach
//    void setUp() throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = dateFormat.parse("10/10/2001");
//        Set<Adresses> addresses = new HashSet<>();
//        Adresses address = new Adresses();
//        address.setId(1);
//        address.setStreetAddress("123 Main St");
//        address.setMain(true);
//        address.setCity("Cidade alerta");
//        address.setNumber(14);
//        address.setState("Stados");
//        addresses.add(address);
//        person = new Person();
//        person.setId(1);
//        person.setFullName("Marlon");
//        person.setBirthDate(date);
//        person.setAddresses(addresses);
//        List<Person> personList1 = new ArrayList<>();
//        personList1.add(person);
//        when(personRepository.findAll()).thenReturn(personList1);
//        MockitoAnnotations.openMocks(this);
//        MockitoAnnotations.openMocks(this.person);
//    }
//
//    @Test
//    void testGetPeopleWithMainAddress() throws ParseException {
//        List<PersonAddressDTO> result = service.getPeopleWithMainAddress();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        PersonAddressDTO personAddressDTO = result.get(0);
//        assertNotNull(personAddressDTO);
//        assertEquals(1, personAddressDTO.id());
//        assertEquals("Marlon", personAddressDTO.fullName());
//    }
//
//}

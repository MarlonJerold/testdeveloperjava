//package com.tecnico.attus.services.impl;
//
//import com.tecnico.attus.model.Adresses;
//import com.tecnico.attus.model.Person;
//import com.tecnico.attus.model.dto.AddressDTO;
//import com.tecnico.attus.model.dto.PersonAddressDTO;
//import com.tecnico.attus.model.dto.PersonDTO;
//import com.tecnico.attus.repository.PersonRepository;
//import org.junit.jupiter.api.Assertions;
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
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class PersonServiceImpl2Test {
//    @Mock
//    private PersonRepository personRepository;
//
//    @InjectMocks
//    private PersonServiceImpl service;
//    @InjectMocks
//    private AdressesServiceImpl adressesService;
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
//        startPerson();
//        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));
//        MockitoAnnotations.openMocks(this);
//        MockitoAnnotations.openMocks(this.person);
//    }
//
//    @Test
//    void whanFindByIdThenReturneAnPersonInstance() {
//        PersonAddressDTO response = service.getPersonById(1);
//        Assertions.assertEquals(PersonAddressDTO.class, response.getClass());
//    }
//
//    @Test
//    void setPrimaryAddressForPerson() {
//        adressesService.setPrimaryAddressForPerson(1, 1);
//    }
//    private void startPerson() throws ParseException {
//
//        List<AddressDTO> addressesListDTO = new ArrayList<>();
//
//        AddressDTO address1 = new AddressDTO(1, "ENDERECO 1", "12345", 24, "Fortaleza", "CE", true);
//        AddressDTO address2 = new AddressDTO(2, "ENDERECO 2", "12345", 24, "Fortaleza", "CE", false);
//        addressesListDTO.add(address1);
//        addressesListDTO.add(address2);
//
//        Set<Adresses> addresses = new HashSet<>();
//
//        Adresses address = new Adresses();
//        address.setId(1);
//        address.setStreetAddress("123 Main St");
//        address.setMain(true);
//        address.setCity("Cidade alerta");
//        address.setNumber(14);
//        address.setState("Stados");
//        addresses.add(address);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = dateFormat.parse("10/10/2001");
//
//        person = new Person();
//        person.setId(1);
//        person.setFullName("Marlon");
//        person.setBirthDate(date);
//        person.setAddresses(addresses);
//
//        personAddressDTO = new PersonAddressDTO(person.getId(), person.getFullName(),"15/10/2001", addressesListDTO);
//
//        personDTO = new PersonDTO("Marlon", "15/10/2001");
//
//    }
//}

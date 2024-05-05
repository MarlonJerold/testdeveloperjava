package com.tecnico.attus.services.impl;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.PersonAddressDTO;
import com.tecnico.attus.model.dto.PersonDTO;
import com.tecnico.attus.repository.AddressRepository;
import com.tecnico.attus.repository.PersonRepository;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl service;
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private Person person;
    private PersonDTO personDTO;

    private PersonAddressDTO personAddressDTO;
    private Optional<Person> optionalPerson;

    @Mock
    private List<Person> personList;

    private List<AddressDTO> addressesListDTO;

    private List<Adresses> addressesList;


    @BeforeEach
    void setUp() throws ParseException {
        startPerson();
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(this.personList);
        MockitoAnnotations.openMocks(this.person);
    }


    @Test
    void whenCreatePersonThenReturnPersonAddressDTO() throws ParseException {
        PersonAddressDTO response = service.createPerson(personAddressDTO);
        assertNotNull(response);
        assertEquals(personAddressDTO.id(), response.id());
        assertEquals(personAddressDTO.fullName(), response.fullName());
    }

    @Test
    void whenUpdatePersonThenReturnPersonAddressDTO () throws ParseException {
        List<PersonAddressDTO> getAllPersons = service.getAllPersons();
        Assert.assertNotNull(getAllPersons);
    }

    @Test
    public void testFindPersonById_notFound() throws Exception {
        Integer id = 1;
        PersonAddressDTO person = service.getPersonById(id);
        assertNull(person);
    }

    @Test
    void testMapPersonToPersonAddressDTO() throws ParseException {

        Set<Adresses> addresses = new HashSet<>();

        Adresses address = new Adresses();
        address.setId(1);
        address.setStreetAddress("123 Main St");
        address.setMain(true);
        address.setCity("Cidade alerta");
        address.setNumber(14);
        address.setZipCode("12345");
        address.setState("Stados");
        addresses.add(address);


        // Define o formato da data
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("10/10/2001");

        Person person1 = new Person();
        person1.setId(1);
        person1.setFullName("Marlon");
        person1.setBirthDate(date);
        person1.setAddresses(addresses);

        PersonAddressDTO result = service.mapPersonToPersonAddressDTO(person1);


        assertNotNull(result); // Verifique se o resultado não é nulo
        assertEquals(1, result.id()); // Verifique o ID
        assertEquals("Marlon", result.fullName()); // Verifique o nome
        assertEquals("Wed Oct 10 00:00:00 BRT 2001", result.birthDate()); // Verifique a data de nascimento (formato string)
        assertEquals(1, result.addresses().size()); // Verifique se há apenas um endereço na lista
        AddressDTO addressDTO = result.addresses().get(0); // Obtenha o primeiro endereço da lista
        assertNotNull(addressDTO); // Verifique se o endereço não é nulo
        assertEquals(1, addressDTO.id()); // Verifique o ID do endereço
        assertEquals("123 Main St", addressDTO.streetAddress()); // Verifique o endereço
        assertEquals("12345", addressDTO.zipCode()); // Verifique o CEP
        assertEquals(14, addressDTO.number()); // Verifique o número
        assertEquals("Cidade alerta", addressDTO.city()); // Verifique a cidade
        assertEquals("Stados", addressDTO.state()); // Verifique o estado
        assertTrue(addressDTO.isMain()); // Verifique se é o endereço principal
    }

    @Test
    void testMapToDTO() throws ParseException {

        Set<Adresses> addresses = new HashSet<>();

        Adresses address = new Adresses();
        address.setId(1);
        address.setStreetAddress("123 Main St");
        address.setMain(true);
        address.setCity("Cidade alerta");
        address.setNumber(14);
        address.setZipCode("12345");
        address.setState("Stados");
        addresses.add(address);


        // Define o formato da data
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("10/10/2001");

        Person person1 = new Person();
        person1.setId(1);
        person1.setFullName("Marlon");
        person1.setBirthDate(date);
        person1.setAddresses(addresses);
        PersonAddressDTO result = service.mapToDTO(person1);
        assertNotNull(result);
        assertEquals(1, result.id());
    }

    @Test
    public void testUpdatePerson_success() throws Exception {
        Mockito.when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));
        PersonAddressDTO response = service.updatePerson(1, personDTO);
        System.out.println(response);
    }

    private void startPerson() throws ParseException {

        List<AddressDTO> addressesListDTO = new ArrayList<>();

        AddressDTO address1 = new AddressDTO(1, "ENDERECO 1", "12345", 24, "Fortaleza", "CE", true);
        AddressDTO address2 = new AddressDTO(2, "ENDERECO 2", "12345", 24, "Fortaleza", "CE", false);
        addressesListDTO.add(address1);
        addressesListDTO.add(address2);

        Set<Adresses> addresses = new HashSet<>();

        Adresses address = new Adresses();
        address.setId(1);
        address.setStreetAddress("123 Main St");
        address.setMain(true);
        address.setZipCode("12345");
        address.setCity("Cidade alerta");
        address.setNumber(14);
        address.setState("Stados");
        addresses.add(address);

        String dateString = "2001-10-10 00:00:00";

        // Define o formato da data
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("10/10/2001");

        person = new Person();
        person.setId(1);
        person.setFullName("Marlon");
        person.setBirthDate(date);
        person.setAddresses(addresses);

        personAddressDTO = new PersonAddressDTO(person.getId(), person.getFullName(),"15/10/2001", addressesListDTO);

        personDTO = new PersonDTO("Marlon", "15/10/2001");

        personList.add(person);
    }
}
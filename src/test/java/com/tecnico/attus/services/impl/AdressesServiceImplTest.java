package com.tecnico.attus.services.impl;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.AddressRequestDTO;
import com.tecnico.attus.model.dto.PersonAddressDTO;
import com.tecnico.attus.model.dto.PersonDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AdressesServiceImplTest {

    @InjectMocks
    private AdressesServiceImpl serviceImplTest;

    @Mock
    private Person person;

    @Mock
    private List<Person> personList;

    private PersonDTO personDTO;

    private PersonAddressDTO personAddressDTO;
    private Optional<Person> optionalPerson;

    private List<AddressDTO> addressesListDTO;

    private List<Adresses> addressesList;


    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void whenMapToAddresModelForAddressDTO() {
        Adresses address = new Adresses();
        address.setId(1);
        address.setStreetAddress("123 Main St");
        AddressDTO addressDTO = serviceImplTest.mapAddressToDTO(address);
        assertEquals(1, addressDTO.id());
        assertEquals("123 Main St", addressDTO.streetAddress());
    }

    @Test
    void whenSetMainAddressForPersonThenSetMainAddressCorrectly() {
        Adresses address = new Adresses();
        address.setId(1);
        address.setStreetAddress("123 Main St");
        serviceImplTest.setMainAddressForPerson(person, address);
        assertEquals(true, person.getAddresses().stream().filter(a -> a.id().equals(1)).findFirst().get().isMain());
        assertEquals(true, address.isMain());
    }

    @Test
    void whenCreateAddressForPersonThenReturnAddressDTO() {

        AddressRequestDTO addressRequestDTO = new AddressRequestDTO("RUA DO PAO", "454", 12, "Cidade", "CE");
        AddressDTO response = serviceImplTest.createAddressForPerson(1, addressRequestDTO);


    }

    @Test
    public void testMapAddressToDTO_allFields() {
        Integer id = 1;
        String streetAddress = "123 Main St";
        String zipCode = "12345-6789";
        Integer number = 12;
        String city = "Anytown";
        String state = "CA";
        boolean isMain = true;

        Adresses addresses = new Adresses();
        addresses.setId(id);
        addresses.setZipCode(zipCode);
        addresses.setState(state);
        addresses.setMain(isMain);
        addresses.setCity(city);
        addresses.setStreetAddress(streetAddress);
        addresses.setNumber(number);

        AddressDTO addressDTO = serviceImplTest.mapAddressToDTO(addresses);

        assertEquals(id, addressDTO.id());
        assertEquals(streetAddress, addressDTO.streetAddress());
        assertEquals(zipCode, addressDTO.zipCode());
        assertEquals(number, addressDTO.number());
        assertEquals(city, addressDTO.city());
        assertEquals(state, addressDTO.state());
        assertEquals(isMain, addressDTO.isMain());
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
        addresses.add(address);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("10/10/2001");

        person = new Person();
        person.setId(1);
        person.setFullName("Marlon");
        person.setBirthDate(date);
        person.setAddresses(addresses);

        personAddressDTO = new PersonAddressDTO(person.getId(), person.getFullName(),"15/10/2001", addressesListDTO);

        personDTO = new PersonDTO("Marlon", "Jerold");

        personList.add(person);

    }

}

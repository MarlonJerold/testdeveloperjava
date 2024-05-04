package com.tecnico.attus;
import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.PersonAddressDTO;
import com.tecnico.attus.model.dto.PersonDTO;
import com.tecnico.attus.repository.PersonRepository;
import com.tecnico.attus.services.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.webjars.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    public PersonServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePersonWithMultipleMainAddresses() throws ParseException {

        PersonAddressDTO personRequestDto = new PersonAddressDTO(1,"John Doe", "25/05/1980", List.of(
                new AddressDTO(2,"Main Street", "12345", 1, "City", "State", true),
                new AddressDTO(3,"Second Street", "54321", 2, "City", "State", true)
        ));

        assertThrows(RuntimeException.class, () -> personService.createPerson(personRequestDto));
    }


    @Test
    void testUpdateNonexistentPerson() throws ParseException {

        Integer nonexistentPersonId = 1;
        PersonDTO updatedPersonDto = new PersonDTO("John Doe", "25/05/1980");

        when(personRepository.findById(nonexistentPersonId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> personService.updatePerson(nonexistentPersonId, updatedPersonDto));

    }


    @Test
    void testGetPersonByIdInvalidId() {

        int invalidPersonId = 999;

        when(personRepository.findById(invalidPersonId)).thenReturn(Optional.empty());
        PersonAddressDTO result = personService.getPersonById(invalidPersonId);

        assertNull(result);

    }

    @Test
    void testGetAllPersons() throws ParseException {

        Date birthDate1 = new SimpleDateFormat("dd/MM/yyyy").parse("25/05/1980");
        Date birthDate2 = new SimpleDateFormat("dd/MM/yyyy").parse("30/09/1975");


        Person person1 = Person.builder()
                .fullName("Marlon Jerold")
                .birthDate(birthDate1)
                .build();
;
        Person person2 = Person.builder()
                .fullName("Mouta Martins")
                .birthDate(birthDate2)
                .build();

        List<Person> personList = Arrays.asList(person1, person2);

        when(personRepository.findAll()).thenReturn(personList);

        List<PersonAddressDTO> result = personService.getAllPersons();

        assertNotNull(result);

    }

    @Test
    void testGetPeopleWithMainAddress() throws ParseException {

        Date birthDate1 = new SimpleDateFormat("dd/MM/yyyy").parse("25/05/1980");
        Date birthDate2 = new SimpleDateFormat("dd/MM/yyyy").parse("30/09/1975");

        Person person1 = Person.builder()
                .fullName("Marlon Jerold")
                .birthDate(birthDate1)
                .build();

        Person person2 = Person.builder()
                .fullName("Mouta Martins")
                .birthDate(birthDate2)
                .build();

        Adresses mainAddress1 = Adresses.builder()
                .streetAddress("123 Main St")
                .city("Fortaleza")
                .zipCode("12345")
                .number(12)
                .state("CE")
                .isMain(true)
                .person(person1)
                .build();

        Adresses mainAddress2 = Adresses.builder()
                .streetAddress("123 Main St")
                .city("Fortaleza")
                .zipCode("12345")
                .number(12)
                .state("CE")
                .isMain(true)
                .person(person2)
                .build();

        Adresses nonMainAddress = Adresses.builder()
                .streetAddress("Rua do gas")
                .city("Fortaleza")
                .zipCode("12345")
                .number(12)
                .state("CE")
                .isMain(false)
                .person(person1)
                .build();


        when(personRepository.findAll()).thenReturn(Arrays.asList(person1, person2));

        List<PersonAddressDTO> result = personService.getPeopleWithMainAddress();
    }


}

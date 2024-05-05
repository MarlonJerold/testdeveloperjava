package com.tecnico.attus;
import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.PersonAddressDTO;
import com.tecnico.attus.model.dto.PersonDTO;
import com.tecnico.attus.repository.PersonRepository;
import com.tecnico.attus.services.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.webjars.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonServiceImpl personService;

    @BeforeEach
    void setup() {
        
    }

    public PersonServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPerson_ReturnsPersonAddressDTO_WhenPersonWithAddressesRequestIsProvided() throws ParseException {

        Date birthDate3 = new SimpleDateFormat("dd/MM/yyyy").parse("30/09/1975");

        // Given
        PersonAddressDTO expectedPersonAddressDTO = new PersonAddressDTO(1, "John Doe", "25/05/1980", List.of(
                new AddressDTO(1, "Main Street", "12345", 12,"City", "State", true)
        ));
        PersonAddressDTO personWithAddressesRequest = new PersonAddressDTO(1, "John Doe","25/05/1980", List.of(
                new AddressDTO(1, "Main Street", "12345", 23,"City", "State", true)
        ));

        Person person = new Person();

        Person personEntity = new Person(1, "John Doe", birthDate3, Set.of(
                new Adresses( "Main Street", "12345", 12, "City", "State", person, true)
        ));
        when(personRepository.save(any(Person.class))).thenReturn(personEntity);

        // When
        PersonAddressDTO createdPersonAddressDTO = personService.createPerson(personWithAddressesRequest);

        // Then
        assertNotNull(createdPersonAddressDTO);
        assertEquals(expectedPersonAddressDTO.id(), createdPersonAddressDTO.id());
        assertEquals(expectedPersonAddressDTO.fullName(), createdPersonAddressDTO.fullName());
        assertEquals(expectedPersonAddressDTO.birthDate(), createdPersonAddressDTO.birthDate());
        assertEquals(expectedPersonAddressDTO.addresses().size(), createdPersonAddressDTO.addresses().size());
        assertEquals(expectedPersonAddressDTO.addresses().get(0).id(), createdPersonAddressDTO.addresses().get(0).id());
        assertEquals(expectedPersonAddressDTO.addresses().get(0).streetAddress(), createdPersonAddressDTO.addresses().get(0).streetAddress());
        assertEquals(expectedPersonAddressDTO.addresses().get(0).zipCode(), createdPersonAddressDTO.addresses().get(0).zipCode());
        assertEquals(expectedPersonAddressDTO.addresses().get(0).city(), createdPersonAddressDTO.addresses().get(0).city());
        assertEquals(expectedPersonAddressDTO.addresses().get(0).state(), createdPersonAddressDTO.addresses().get(0).state());
        assertEquals(expectedPersonAddressDTO.addresses().get(0).isMain(), createdPersonAddressDTO.addresses().get(0).isMain());
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

    @Test
    void getMainAddress_ReturnsMainAddress_WhenAvailable() throws ParseException {

        Date birthDate2 = new SimpleDateFormat("dd/MM/yyyy").parse("30/09/1975");

        Person person2 = Person.builder()
                .fullName("Mouta Martins")
                .birthDate(birthDate2)
                .build();
        // Arrange
        Adresses mainAddress = new Adresses("Main Street", "4540", 12, "City", "State", person2, true);
        Set<Adresses> addresses = new HashSet<>();
        addresses.add(mainAddress);

        Person person = Person.builder()
                .id(1)
                .fullName("John Doe")
                .birthDate(new Date())
                .addresses(addresses)
                .build();

        // Act
        Adresses result = person.getMainAddress();

        // Assert
        assertNotNull(result);
        assertEquals(mainAddress, result);
    }

    @Test
    void testGetMainAddress_ReturnsMainAddress_WhenMainAddressExists() {

        Person person1 = new Person();

        Person pessoa = Person.builder()
                .id(1)
                .fullName("John Doe")
                .birthDate(new Date())
                .addresses(new HashSet<>(Arrays.asList(
                        new Adresses("Main Street", "rer",12, "12345", "City",person1 , true),
                        new Adresses("Teste", "rer",12, "12345", "City",person1 , false)
                )))
                .build();

        Adresses mainAddress = pessoa.getMainAddress();
        assertEquals("Main Street", mainAddress.streetAddress());
    }

    @Test
    void testUpdatePerson_Success() throws ParseException {
        // Configurar o cenário de teste
        Integer personId = 1;
        PersonDTO personDTO = new PersonDTO("Marlon", "15/10/2001");

        Person existingPerson = new Person();
        existingPerson.setId(1); // Definindo o ID
        existingPerson.setFullName("John Doe");
        existingPerson.setFullName("19/10/2023");

        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));

        PersonAddressDTO expectedPersonAddressDTO = new PersonAddressDTO(1,"John Doe", "25/05/1980", List.of(
                new AddressDTO(2,"Main Street", "12345", 1, "City", "State", true),
                new AddressDTO(3,"Second Street", "54321", 2, "City", "State", true)
        ));
        when(personService.updatePerson(personId, personDTO)).thenReturn(expectedPersonAddressDTO);


        PersonAddressDTO result = personService.updatePerson(personId, personDTO);

        // Verificar se o método save do personRepository foi chamado
        verify(personRepository).save(existingPerson);

        // Adicionar pelo menos uma asserção para verificar o resultado ou comportamento esperado
        assertNotNull(result);
        assertEquals(expectedPersonAddressDTO, result);
    }

}

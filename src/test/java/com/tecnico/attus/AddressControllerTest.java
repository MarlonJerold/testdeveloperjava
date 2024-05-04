package com.tecnico.attus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.AddressDTO;


import com.tecnico.attus.model.dto.AddressRequestDTO;
import com.tecnico.attus.model.dto.PersonAddressDTO;
import com.tecnico.attus.repository.AddressRepository;
import com.tecnico.attus.repository.PersonRepository;
import com.tecnico.attus.services.AddressService;
import com.tecnico.attus.services.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;

    private AddressDTO mockAddress;


    @Mock
    private PersonRepository personRepository;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetAllAddressForPerson() throws Exception {

        Integer personId = 1;
        List<Adresses> addresses = new ArrayList<>();

        Adresses adressesBuild = Adresses.builder()
                .id(1)
                .streetAddress("Rua 10")
                .zipCode("1234")
                .number(12)
                .city("City")
                .state("State")
                .isMain(true)
                .build();

        addresses.add(adressesBuild);

        Adresses adressesBuild2 = Adresses.builder()
                .id(2)
                .streetAddress("Rua 20")
                .zipCode("1234")
                .number(12)
                .city("City")
                .state("State")
                .isMain(false)
                .build();

        addresses.add(adressesBuild2);

        List<AddressDTO> addressDTOListDTOs = new ArrayList<>();

        for (Adresses adresses : addresses) {
            AddressDTO addressDTO = new AddressDTO(adresses.id(), adresses.streetAddress(), adresses.zipCode(), adresses.number(), adresses.city(), adresses.state(), adresses.isMain());
            addressDTOListDTOs.add(addressDTO);
        }

        when(addressService.getAddressesForPerson(personId)).thenReturn(addressDTOListDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/address/{personId}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(addressDTOListDTOs)));
    }

    @Test
    void testGetPeopleWithMainAddress() throws Exception {

        List<PersonAddressDTO> expectedPeople = new ArrayList<>();

        List<Adresses> addresses = new ArrayList<>();

        Adresses adressesBuild = Adresses.builder()
                .id(1)
                .streetAddress("Rua 10")
                .zipCode("1234")
                .number(12)
                .city("City")
                .state("State")
                .isMain(true)
                .build();

        addresses.add(adressesBuild);

        List<AddressDTO> addressDTOs = new ArrayList<>();

        for (Adresses address : addresses) {
            AddressDTO addressDTO = new AddressDTO(
                    address.id(),
                    address.streetAddress(),
                    address.zipCode(),
                    address.number(),
                    address.city(),
                    address.state(),
                    address.isMain()
            );
            addressDTOs.add(addressDTO);


        expectedPeople.add(new PersonAddressDTO(
                1,
                "John Doe",
                "25/05/1980",
                addressDTOs)
        );

        Mockito.when(personService.getPeopleWithMainAddress()).thenReturn(expectedPeople);


            mockMvc.perform(MockMvcRequestBuilders.get("/person/getPeopleWithMainAddress")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedPeople)));
    }

    }
    @Test
    void testSetPrimaryAddressForPerson() {
        // Dados de exemplo
        Integer personId = 1;
        Integer addressId = 1;

        // Criando uma instância de Pessoa com um endereço
        Person person = new Person();
        Adresses address = new Adresses();
        person.getAddresses().add(address);
        address.setPerson(person);

        // Configurando o comportamento simulado do personRepository
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        // Chamando o método a ser testado
        addressService.setPrimaryAddressForPerson(personId, addressId);

        assertFalse(address.isMain());
    }

    @Test
    public void testCreateAddressForPerson() {
        when(addressRepository.save(any())).thenReturn(mockAddress);

        AddressDTO result = addressService.createAddressForPerson(1, new AddressRequestDTO("Main St", "12345", 12, "City", "State"));

        assertEquals(mockAddress, result);
    }

    @Test
    public void testUpdateAddressForPerson() {
        when(addressRepository.existsById(any())).thenReturn(true);
        when(addressRepository.save(any())).thenReturn(mockAddress);

        AddressDTO result = addressService.updateAddressForPerson(1, 1, new AddressRequestDTO("Updated St", "54321", 12,"NewCity", "NewState"));

        assertEquals(mockAddress, result);
    }


}

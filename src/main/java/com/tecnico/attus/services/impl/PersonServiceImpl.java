package com.tecnico.attus.services.impl;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.PersonAddressDTO;
import com.tecnico.attus.model.dto.PersonDTO;
import com.tecnico.attus.repository.AddressRepository;
import com.tecnico.attus.repository.PersonRepository;
import com.tecnico.attus.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;



    @Override
    public PersonAddressDTO createPerson(PersonAddressDTO personRequestDto) throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada = formato.parse(personRequestDto.birthDate());

        Person person = Person.builder()
                .fullName(personRequestDto.fullName())
                .birthDate(dataFormatada)
                .build();

        List<AddressDTO> addressDtos = personRequestDto.addresses();

        long mainAddressCount = addressDtos.stream().filter(AddressDTO::isMain).count();

        if (mainAddressCount > 1) {
            throw new RuntimeException("More than one address is marked as main");
        }

        List<Adresses> addresses = personRequestDto.addresses().stream()
                .map(addressDto -> new Adresses(
                        addressDto.streetAddress(),
                        addressDto.zipCode(),
                        addressDto.number(),
                        addressDto.city(),
                        addressDto.state(),
                        person,
                        addressDto.isMain()
                ))
                .collect(Collectors.toList());

        person.getAddresses().addAll(addresses);

        personRepository.save(person);
        addressRepository.saveAll(addresses);

        return personRequestDto;

    }

    @Override
    public PersonAddressDTO updatePerson(Integer id, PersonDTO person) throws ParseException {

        Optional<Person> optionalPerson = personRepository.findById(id);

        if (optionalPerson.isEmpty()) {
            throw new NotFoundException("Pessoa não encontrada com o ID: " + id);
        }

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada = formato.parse(person.birthDate());

        Person existingPerson = optionalPerson.get();
        existingPerson.setFullName(person.fullName());
        existingPerson.setBirthDate(dataFormatada);

        return mapToDTO(personRepository.save(existingPerson));
    }

    @Override
    public Person getPersonById(Integer id) {
        Optional<Person> personById = personRepository.findById(id);
        return personById.orElse(null);
    }

    @Override
    public List<PersonAddressDTO> getAllPersons() {
        List<Person> personList = personRepository.findAll();
        return mapToDTOList(personList);
    }

    @Override
    public List<PersonAddressDTO> getPeopleWithMainAddress() {
        List<Person> personList = personRepository.findAll();

        List<PersonAddressDTO> dtoList = personList.stream()
                .map(person -> {
                    Adresses mainAddress = person.getAddresses().stream()
                            .filter(Adresses::isMain)
                            .findFirst()
                            .orElse(null);

                    if (mainAddress != null) {
                        LocalDateTime birthDate = LocalDateTime.parse(person.getBirthDate().toString());
                        AddressDTO addressDTO = new AddressDTO(
                                mainAddress.id(),
                                mainAddress.streetAddress(),
                                mainAddress.zipCode(),
                                mainAddress.number(),
                                mainAddress.city(),
                                mainAddress.state(),
                                mainAddress.isMain()
                        );
                        return new PersonAddressDTO(person.getId(), person.getFullName(), birthDate.toString(), Collections.singletonList(addressDTO)
                        );
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return dtoList;
    }
    public List<PersonAddressDTO> mapToDTOList(List<Person> personList) {
        return personList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PersonAddressDTO mapToDTO(Person person) {
        List<AddressDTO> addressDTOs = person.getAddresses().stream()
                .map(address -> new AddressDTO(address.id(), address.streetAddress(), address.zipCode(), address.number(), address.city(), address.state(), address.isMain()
                ))
                .collect(Collectors.toList());
        return new PersonAddressDTO(person.getId(), person.getFullName(), person.getBirthDate().toString(), addressDTOs
        );
    }

}

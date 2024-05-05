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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final AddressRepository addressRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }


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

        List<Adresses> addresses = mapToAdressesList(personRequestDto.addresses(), person);

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
    public PersonAddressDTO getPersonById(Integer id) {

        return personRepository.findById(id)
                .map(person -> {
                    List<AddressDTO> addressDTOs = person.getAddresses().stream()
                            .map(address -> new AddressDTO(
                                    address.id(),
                                    address.streetAddress(),
                                    address.zipCode(),
                                    address.id(),
                                    address.city(),
                                    address.state(),
                                    address.isMain()
                            ))
                            .toList();
                    return new PersonAddressDTO(person.getId(), person.getFullName(),  person.getBirthDate() != null ? person.getBirthDate().toString() : "Unknown", addressDTOs);
                })
                .orElse(null);
    }

    @Override
    public List<PersonAddressDTO> getAllPersons() {
        List<Person> personList = personRepository.findAll();
        return mapToDTOList(personList);
    }

    @Override
    public List<PersonAddressDTO> getPeopleWithMainAddress() {
        return personRepository.findAll().stream()
                .map(this::mapPersonToPersonAddressDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    PersonAddressDTO mapPersonToPersonAddressDTO(Person person) {
        Adresses mainAddress = findMainAddress(person);
        if (mainAddress != null) {
            AddressDTO addressDTO = mapAddressToAddressDTO(mainAddress);
            return new PersonAddressDTO(
                    person.getId(),
                    person.getFullName(),
                    person.getBirthDate().toString(),
                    Collections.singletonList(addressDTO)
            );
        } else {
            return null;
        }
    }

    private Adresses findMainAddress(Person person) {
        return person.getAddresses().stream()
                .filter(Adresses::isMain)
                .findFirst()
                .orElse(null);
    }

    private AddressDTO mapAddressToAddressDTO(Adresses address) {
        return new AddressDTO(
                address.id(),
                address.streetAddress(),
                address.zipCode(),
                address.number(),
                address.city(),
                address.state(),
                address.isMain()
        );
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

    private List<Adresses> mapToAdressesList(List<AddressDTO> addressDTOList, Person person) {
        return addressDTOList.stream()
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
    }
}

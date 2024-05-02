package com.tecnico.attus.services.impl;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.PersonDTO;
import com.tecnico.attus.repository.AddressRepository;
import com.tecnico.attus.repository.PersonRepository;
import com.tecnico.attus.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public PersonDTO createPerson(PersonDTO personRequestDto) throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada = formato.parse(personRequestDto.birthDate());

        Person person = Person.builder()
                .fullName(personRequestDto.fullName())
                .birthDate(dataFormatada)
                .build();

        List<Adresses> addresses = personRequestDto.addresses().stream()
                .map(addressDto -> new Adresses(
                        addressDto.streetAddress(),
                        addressDto.zipCode(),
                        addressDto.number(),
                        addressDto.city(),
                        addressDto.state(),
                        person
                ))
                .collect(Collectors.toList());

        person.getAddresses().addAll(addresses);

        personRepository.save(person);
        addressRepository.saveAll(addresses);

        return personRequestDto;

    }

    @Override
    public Person updatePerson(Integer id, Person person) {
        Person personRequest = Person.builder()
                .id(person.getId())
                .fullName(person.getFullName())
                .birthDate(person.getBirthDate())
                .build();

        return null;
    }

    @Override
    public Person getPersonById(Long id) {
        return null;
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        List<Person> personList = personRepository.findAll();
        return mapToDTOList(personList);
    }

    public List<PersonDTO> mapToDTOList(List<Person> personList) {
        return personList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO mapToDTO(Person person) {

        List<AddressDTO> addressDTOs = person.getAddresses().stream()
                .map(address -> new AddressDTO(
                        address.id(),
                        address.streetAddress(),
                        address.zipCode(),
                        address.number(),
                        address.city(),
                        address.state()))
                .collect(Collectors.toList());

        return new PersonDTO(
                person.getId(),
                person.getFullName(),
                person.getBirthDate().toString(),
                addressDTOs
        );
    }

}

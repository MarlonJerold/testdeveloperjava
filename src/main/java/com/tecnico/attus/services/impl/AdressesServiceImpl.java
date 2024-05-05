package com.tecnico.attus.services.impl;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.Person;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.AddressRequestDTO;
import com.tecnico.attus.repository.AddressRepository;
import com.tecnico.attus.repository.PersonRepository;
import com.tecnico.attus.services.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdressesServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;

    @Autowired
    AdressesServiceImpl(AddressRepository addressRepository, PersonRepository personRepository) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
    }

    @Override
    public AddressDTO createAddressForPerson(Integer personId, AddressRequestDTO address) {

        Optional<Person> person = personRepository.findById(personId);
        Adresses adresses = Adresses.builder()
                .city(address.city())
                .streetAddress(address.streetAddress())
                .number(address.number())
                .zipCode(address.zipCode())
                .state(address.state())
                .person(person.orElse(null))
                .build();

        Adresses adressesSave = addressRepository.save(adresses);

        return mapAddressToDTO(adressesSave);
    }

    @Override
    public AddressDTO updateAddressForPerson(Integer personId, Integer addressId, AddressRequestDTO address) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        Adresses adresses = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));

        if (!adresses.person().getId().equals(person.getId())) {
            throw new IllegalArgumentException("O endereço não pertence à pessoa especificada");
        }

        adresses.setCity(address.city());
        adresses.setStreetAddress(address.streetAddress());
        adresses.setNumber(address.number());
        adresses.setZipCode(address.zipCode());
        adresses.setState(address.state());

        Adresses adressesSave = addressRepository.save(adresses);

        return mapAddressToDTO(adressesSave);
    }

    @Override
    public List<AddressDTO> getAddressesForPerson(Integer personId) {

        List<Adresses> adressesList = addressRepository.findByPersonId(personId);
        List<AddressDTO> addressDTOListDTOs = new ArrayList<>();

        for (Adresses adresses : adressesList) {
            AddressDTO addressDTO = new AddressDTO(adresses.id(), adresses.streetAddress(), adresses.zipCode(), adresses.number(), adresses.city(), adresses.state(), adresses.isMain());
            addressDTOListDTOs.add(addressDTO);
        }

        return addressDTOListDTOs;
    }



    @Override
    public void setPrimaryAddressForPerson(Integer personId, Integer addressId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        Adresses address = person.getAddresses().stream()
                .filter(a -> a.id().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        setMainAddressForPerson(person, address);
        personRepository.save(person);
    }

    public void setMainAddressForPerson(Person person, Adresses address) {
        person.getAddresses().forEach(a -> a.setMain(a.id().equals(address.id())));
        address.setMain(true);
    }


    public AddressDTO mapAddressToDTO(Adresses adresses) {
        return new AddressDTO(
                adresses.id(),
                adresses.streetAddress(),
                adresses.zipCode(),
                adresses.number(),
                adresses.city(),
                adresses.state(),
                adresses.isMain()
        );
    }

}

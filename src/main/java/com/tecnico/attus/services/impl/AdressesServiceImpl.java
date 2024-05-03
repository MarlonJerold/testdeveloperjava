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
import org.webjars.NotFoundException;

import java.text.SimpleDateFormat;
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
    public Adresses createAddressForPerson(Integer personId, AddressRequestDTO address) {

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
        return adressesSave;
    }

    @Override
    public Adresses updateAddressForPerson(Integer personId, Integer addressId, AddressRequestDTO address) {

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

        return addressRepository.save(adresses);

    }

    @Override
    public List<AddressDTO> getAddressesForPerson(Integer personId) {

        List<Adresses> adressesList = addressRepository.findByPersonId(personId);
        List<AddressDTO> addressDTOListDTOs = new ArrayList<>();

        for (Adresses adresses : adressesList) {
            AddressDTO addressDTO = new AddressDTO(adresses.id(), adresses.streetAddress(), adresses.zipCode(), adresses.number(), adresses.city(), adresses.state());
            addressDTOListDTOs.add(addressDTO);
        }

        return addressDTOListDTOs;
    }

    @Override
    public Adresses getPrimaryAddressForPerson(Integer personId) {
        return null;
    }

    @Override
    public void setPrimaryAddressForPerson(Integer addressId) {

    }

}

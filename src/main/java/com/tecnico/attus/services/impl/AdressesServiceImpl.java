package com.tecnico.attus.services.impl;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.repository.AddressRepository;
import com.tecnico.attus.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdressesServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Adresses createAddressForPerson(Long personId, Adresses address) {
        return null;
    }

    @Override
    public Adresses updateAddressForPerson(Long personId, Adresses address) {
        return null;
    }

    @Override
    public List<Adresses> getAddressesForPerson(Long personId) {
        return List.of();
    }

    @Override
    public Adresses getPrimaryAddressForPerson(Long personId) {
        return null;
    }

    @Override
    public void setPrimaryAddressForPerson(Long personId, Long addressId) {

    }

}

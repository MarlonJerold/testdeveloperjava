package com.tecnico.attus.services;

import com.tecnico.attus.model.Adresses;

import java.util.List;

public interface AddressService {

    Adresses createAddressForPerson(Long personId, Adresses address);
    Adresses updateAddressForPerson(Long personId, Adresses address);
    List<Adresses> getAddressesForPerson(Long personId);
    Adresses getPrimaryAddressForPerson(Long personId);
    void setPrimaryAddressForPerson(Long personId, Long addressId);

}

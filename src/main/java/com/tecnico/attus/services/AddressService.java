package com.tecnico.attus.services;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    Adresses createAddressForPerson(Integer personId, AddressDTO address);
    Adresses updateAddressForPerson(Integer personId, AddressDTO address);
    List<Adresses> getAddressesForPerson(Integer personId);
    Adresses getPrimaryAddressForPerson(Long personId);
    void setPrimaryAddressForPerson(Long personId, Long addressId);

}

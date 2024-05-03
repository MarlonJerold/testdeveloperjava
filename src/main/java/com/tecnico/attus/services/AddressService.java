package com.tecnico.attus.services;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.AddressRequestDTO;

import java.util.List;

public interface AddressService {

    Adresses createAddressForPerson(Integer personId, AddressRequestDTO address);
    Adresses updateAddressForPerson(Integer personId, Integer addressId, AddressRequestDTO address);
    List<AddressDTO> getAddressesForPerson(Integer personId);
    Adresses getPrimaryAddressForPerson(Integer personId);
    void setPrimaryAddressForPerson(Integer personId, Integer addressId);

}

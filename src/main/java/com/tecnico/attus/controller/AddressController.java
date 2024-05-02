package com.tecnico.attus.controller;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.services.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AddressController {

    private AddressService addressService;

    @GetMapping("/{id}")
    public List<Adresses> getAllAddress(@PathVariable Integer personId) {
        return addressService.getAddressesForPerson(personId);
    }

    @PostMapping("/{id}")
    public Adresses createAddressForPerson(@PathVariable Integer personId, @RequestBody AddressDTO addressDTO) {
        return addressService.createAddressForPerson(personId, addressDTO);
    }

    @PutMapping("/{id}")
    public Adresses updateAddressForPerson(@PathVariable Integer personId, @RequestBody AddressDTO addressDTO) {
        return addressService.updateAddressForPerson(personId, addressDTO);
    }

}

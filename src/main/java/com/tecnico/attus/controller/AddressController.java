package com.tecnico.attus.controller;

import com.tecnico.attus.model.Adresses;
import com.tecnico.attus.model.dto.AddressDTO;
import com.tecnico.attus.model.dto.AddressRequestDTO;
import com.tecnico.attus.services.AddressService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{personId}")
    public ResponseEntity<List<AddressDTO>> getAllAddressForPerson(@PathVariable Integer personId) {
        return ResponseEntity.ok(addressService.getAddressesForPerson(personId));
    }

    @PostMapping("/{personId}")
    public ResponseEntity<Adresses> createAddressForPerson(@PathVariable Integer personId, @RequestBody AddressRequestDTO addressDTO) {
        return ResponseEntity.ok(addressService.createAddressForPerson(personId, addressDTO));
    }

    @PutMapping("/persons/{personId}/addresses/{addressId}")
    public ResponseEntity<Adresses> updateAddressForPerson(@PathVariable Integer personId, @PathVariable Integer addressId, @RequestBody AddressRequestDTO addressDTO) {
        return ResponseEntity.ok(addressService.updateAddressForPerson(personId, addressId, addressDTO));
    }

    @PutMapping("addressForPerson/{id}")
    public void setPrimaryAddressForPerson(@PathVariable Integer addressId) {
        addressService.setPrimaryAddressForPerson(addressId);
    }

    @GetMapping("addressForPerson/{id}")
    public ResponseEntity<Adresses> getPrimaryAddressForPerson(@PathVariable Integer personId) {
        return ResponseEntity.ok(addressService.getPrimaryAddressForPerson(personId));
    }

}

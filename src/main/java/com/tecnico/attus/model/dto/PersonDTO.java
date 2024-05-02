package com.tecnico.attus.model.dto;

import com.tecnico.attus.model.Adresses;

import java.util.Date;
import java.util.List;

public record PersonDTO(Integer id, String fullName, String birthDate, List<AddressDTO> addresses) {

}

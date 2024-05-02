package com.tecnico.attus.model.dto;

import java.util.List;

public record PersonAddressDTO(Integer id, String fullName, String birthDate, List<AddressDTO> addresses) {
}

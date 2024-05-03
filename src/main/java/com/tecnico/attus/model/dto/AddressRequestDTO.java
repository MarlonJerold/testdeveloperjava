package com.tecnico.attus.model.dto;

public record AddressRequestDTO(String streetAddress, String zipCode, Integer number, String city, String state) {
}

package com.tecnico.attus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "adresses")
public class Adresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String streetAddress;
    private String zipCode;
    private Integer number;
    private String city;
    private String state;

    Adresses() {}

    public static class Builder {

        private Adresses adresses;

        private Builder() {
            adresses = new Adresses();
        }

        public Builder streetAddress(String streetAddress) {
            adresses.streetAddress = streetAddress;
            return this;
        }

        public Builder zipCode(String zipCode) {
            adresses.zipCode = zipCode;
            return this;
        }

        public Builder number(Integer number) {
            adresses.number = number;
            return this;
        }

        public Builder city(String city) {
            adresses.city = city;
            return this;
        }

        public Builder state(String state) {
            adresses.state = state;
            return this;
        }

        public Adresses build() {
            return adresses;
        }

    }

}


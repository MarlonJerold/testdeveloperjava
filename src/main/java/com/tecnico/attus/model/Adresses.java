package com.tecnico.attus.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "adresses") // Corrigido o nome da entidade para "addresses"
public class Adresses {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String streetAddress;
    private String zipCode;
    private Integer number;
    private String city;
    private String state;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    protected Adresses() {}

    public Adresses(String streetAddress, String zipCode, Integer number, String city, String state, Person person) {
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.number = number;
        this.city = city;
        this.state = state;
        this.person = person;
    }


    public Integer id() {
        return id;
    }

    public String streetAddress() {
        return streetAddress;
    }

    public String zipCode() {
        return zipCode;
    }

    public Integer number() {
        return number;
    }

    public String city() {
        return city;
    }

    public String state() {
        return state;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String streetAddress;
        private String zipCode;
        private Integer number;
        private String city;
        private String state;
        private Person person;

        public Builder streetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder number(Integer number) {
            this.number = number;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder person(Person person) {
            this.person = person;
            return this;
        }

        public Adresses build() {
            return new Adresses(streetAddress, zipCode, number, city, state, person);
        }
    }
}


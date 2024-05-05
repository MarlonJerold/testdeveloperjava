package com.tecnico.attus.model;

import jakarta.persistence.*;

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

    @Column(name = "is_main", nullable = false, columnDefinition = "boolean default false")
    private boolean isMain;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Adresses() {}

    public Adresses(String streetAddress, String zipCode, Integer number, String city, String state, Person person, boolean isMain) {
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.number = number;
        this.city = city;
        this.state = state;
        this.person = person;
        this.isMain = isMain;
    }

    public Integer id() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
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

    public boolean isMain() {
        return isMain;
    }
    public Person person() {
        return person;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public void setMain(boolean main) {
        isMain = main;
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
        private boolean isMain;
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

        public Builder isMain(boolean isMain) {
            this.isMain = isMain;
            return this;
        }

        public Adresses build() {
            return new Adresses(streetAddress, zipCode, number, city, state, person, isMain);
        }
    }

}


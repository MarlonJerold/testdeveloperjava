package com.tecnico.attus.model;

import jakarta.persistence.*;

import java.util.*;

@Entity(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private Date birthDate;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "person_address",
            joinColumns = { @JoinColumn(name = "person_id") },
            inverseJoinColumns = { @JoinColumn(name = "address_id") }
    )
    private Set<Adresses> addresses = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Set<Adresses> getAddresses() {
        return addresses;
    }

    public static class Builder {
        private Person person;

        private Builder() {
            person = new Person();
        }

        public Builder id(Integer id) {
            person.id = id;
            return this;
        }

        public Builder fullName(String fullName) {
            person.fullName = fullName;
            return this;
        }

        public Builder birthDate(Date birthDate) {
            person.birthDate = birthDate;
            return this;
        }

        public Builder addresses(Set<Adresses> addresses) {
            person.addresses = addresses;
            return this;
        }

        public Person build() {
            return person;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}

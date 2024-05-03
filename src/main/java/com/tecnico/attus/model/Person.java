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

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Set<Adresses> addresses = new HashSet<>();

    @Transient
    public Adresses getMainAddress() {
        return addresses.stream()
                .filter(Adresses::isMain)
                .findFirst()
                .orElse(null);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

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

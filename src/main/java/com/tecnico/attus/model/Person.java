package com.tecnico.attus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private Date birthDate;

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getBirthDate() {
        return birthDate;
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

        public Person build() {
            return person;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}

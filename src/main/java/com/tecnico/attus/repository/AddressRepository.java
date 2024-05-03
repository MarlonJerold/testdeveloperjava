package com.tecnico.attus.repository;

import com.tecnico.attus.model.Adresses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Adresses, Integer> {
    List<Adresses> findByPersonId(Integer personId);
}

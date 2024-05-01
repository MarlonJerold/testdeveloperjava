package com.tecnico.attus.repository;

import com.tecnico.attus.model.Adresses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Integer, Adresses> {
}

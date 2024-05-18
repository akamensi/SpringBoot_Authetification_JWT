package com.akamensi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akamensi.entities.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

}

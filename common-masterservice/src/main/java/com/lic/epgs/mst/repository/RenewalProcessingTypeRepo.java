package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.RenewalProcessingType;

@Repository
public interface RenewalProcessingTypeRepo extends JpaRepository<RenewalProcessingType, Long> {

	public Optional<RenewalProcessingType> findByRenewalCode(String renewalCode);
}

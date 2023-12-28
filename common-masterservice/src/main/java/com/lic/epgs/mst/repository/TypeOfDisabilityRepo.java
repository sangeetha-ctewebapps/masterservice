package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TypeOfDisability;

@Repository
public interface TypeOfDisabilityRepo extends JpaRepository<TypeOfDisability, Long> {

	Optional<TypeOfDisability> findByDisabilityCode(String disabilityCode);

}

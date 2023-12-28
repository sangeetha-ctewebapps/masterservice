package com.lic.epgs.mst.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lic.epgs.mst.entity.TypeOfLifeAssured;

public interface TypeOfLifeAssuredRepo extends JpaRepository<TypeOfLifeAssured, Long> {
	//Optional<TypeOfLifeAssured> findByTypeCode(String Code);
	
	@Query("FROM TypeOfLifeAssured WHERE typeCode = :typeCode")
	public Optional<TypeOfLifeAssured> findByTypeCode(String typeCode);
}

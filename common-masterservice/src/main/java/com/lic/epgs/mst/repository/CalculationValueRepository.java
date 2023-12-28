package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CalculationValueMst;

@Repository
public interface CalculationValueRepository extends JpaRepository<CalculationValueMst, Long> {

	@Query(value = "SELECT * FROM MASTER_CALCULATION_VALUE WHERE CALCULATION_VALUE_CODE = :calculationValueCode", nativeQuery = true)
	Optional<CalculationValueMst> findByCode(String calculationValueCode);

}

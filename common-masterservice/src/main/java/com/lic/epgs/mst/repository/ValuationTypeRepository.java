package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ValuationTypeMst;

@Repository
public interface ValuationTypeRepository extends JpaRepository<ValuationTypeMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM VALUATION_MST WHERE VALUATION_TYPE_CODE = :valutionTypeCode",
	 * nativeQuery = true) Optional<ValuationTypeMst> findByCode(String
	 * valutionTypeCode);
	 */
	
	@Query("FROM ValuationTypeMst WHERE valutionTypeCode = :code")
	public Optional<ValuationTypeMst> findByCode(String code);

}

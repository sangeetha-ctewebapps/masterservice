package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.GratuityTypeMst;

@Repository
public interface GratuityBenefitTypeRepository extends JpaRepository<GratuityTypeMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM GRATUITY_MST WHERE GRATUITY_TYPE_CODE = :gratuityTypeCode",
	 * nativeQuery = true) Optional<GratuityTypeMst> findByCode(String
	 * gratuityTypeCode);
	 */
	
	@Query("FROM GratuityTypeMst WHERE gratuityTypeCode = :code")
	public Optional<GratuityTypeMst> findByCode(String code);
	
}

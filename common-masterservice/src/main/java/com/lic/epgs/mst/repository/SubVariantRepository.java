package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.SubVariantMst;

@Repository
public interface SubVariantRepository extends JpaRepository<SubVariantMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM SUB_VARIANT WHERE SUB_VARIANT_CODE = :subVariantCode",
	 * nativeQuery = true) Optional<SubVariantMst> findByCode(String
	 * subVariantCode);
	 */
	
	@Query("FROM SubVariantMst WHERE subVariantCode = :code")
	public Optional<SubVariantMst> findByCode(String code);

}

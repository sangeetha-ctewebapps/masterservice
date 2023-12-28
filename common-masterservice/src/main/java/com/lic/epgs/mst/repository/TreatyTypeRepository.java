package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TreatySubTypeMst;
import com.lic.epgs.mst.entity.TreatyTypeMst;

@Repository
public interface TreatyTypeRepository extends JpaRepository<TreatyTypeMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM TREATY_TYPE WHERE TREATY_TYPE_CODE = :treatyTypeCode",
	 * nativeQuery = true) Optional<TreatyTypeMst> findByCode(String
	 * treatyTypeCode);
	 */

	@Query("FROM TreatyTypeMst WHERE treatyTypeCode = :code")
	public Optional<TreatyTypeMst> findByCode(String code);
}

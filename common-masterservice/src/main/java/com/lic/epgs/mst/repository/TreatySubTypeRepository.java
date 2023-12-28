package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TreatySubTypeMst;

@Repository
public interface TreatySubTypeRepository extends JpaRepository<TreatySubTypeMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM TREATY_SUB_TYPE WHERE TREATY_SUB_TYPE_CODE = :treatySubTypeCode"
	 * , nativeQuery = true) Optional<TreatySubTypeMst> findByCode(String
	 * treatySubTypeCode);
	 */
	
	@Query("FROM TreatySubTypeMst WHERE treatySubTypeCode = :code")
	public Optional<TreatySubTypeMst> findByCode(String code);

}

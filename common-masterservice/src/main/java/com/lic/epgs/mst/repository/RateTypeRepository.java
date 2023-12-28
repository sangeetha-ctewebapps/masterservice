package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.RateTypeMst;

@Repository
public interface RateTypeRepository extends JpaRepository<RateTypeMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM RATE_TYPE WHERE RATE_TYPE_CODE = :rateTypeCode", nativeQuery =
	 * true) Optional<RateTypeMst> findByCode(String rateTypeCode);
	 */
	
	@Query("FROM RateTypeMst WHERE rateTypeCode = :code")
	public Optional<RateTypeMst> findByCode(String code);

}

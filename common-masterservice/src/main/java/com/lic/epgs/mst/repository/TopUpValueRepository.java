package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TopUpValueMst;
import com.lic.epgs.mst.entity.TreatySubTypeMst;

@Repository
public interface TopUpValueRepository extends JpaRepository<TopUpValueMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM TOP_UP_VALUE WHERE TOP_UP_VALUE_CODE = :TopupvalueCode",
	 * nativeQuery = true) Optional<TopUpValueMst> findByCode(String
	 * TopupvalueCode);
	 */
	
	@Query("FROM TopUpValueMst WHERE TopupvalueCode = :code")
	public Optional<TopUpValueMst> findByCode(String code);

}

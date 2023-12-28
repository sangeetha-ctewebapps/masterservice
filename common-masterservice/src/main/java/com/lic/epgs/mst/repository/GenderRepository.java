package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.GenderMst;

@Repository
public interface GenderRepository extends JpaRepository<GenderMst, Long> {

	/*
	 * @Query(value = "SELECT * FROM GENDER_MST WHERE GENDER_CODE = :genderCode",
	 * nativeQuery = true) Optional<GenderMst> findByCode(String genderCode);
	 */
	
	@Query("FROM GenderMst WHERE genderCode = :code")
	public Optional<GenderMst> findByGender(String code);

}

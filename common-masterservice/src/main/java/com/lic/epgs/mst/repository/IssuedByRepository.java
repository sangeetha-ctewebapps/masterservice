package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.IssuedByMst;

@Repository
public interface IssuedByRepository extends JpaRepository<IssuedByMst, Long> {

	/*
	 * @Query(value = "SELECT * FROM ISSUED_BY WHERE ISSUED_BY_CODE = :issueByCode",
	 * nativeQuery = true) Optional<IssuedByMst> findByCode(String issueByCode);
	 */
	
	@Query("FROM IssuedByMst WHERE issueByCode = :code")
	public Optional<IssuedByMst> findByCode(String code);
}

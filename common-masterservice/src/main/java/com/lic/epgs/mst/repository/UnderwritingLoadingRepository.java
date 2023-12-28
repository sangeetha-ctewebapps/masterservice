package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.UnderwritingLoadingMst;

@Repository
public interface UnderwritingLoadingRepository extends JpaRepository<UnderwritingLoadingMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM UNDERWRITING_LOADING WHERE UNDERWRITING_LOADING_CODE = :underWritingLoadingCode"
	 * , nativeQuery = true) Optional<UnderwritingLoadingMst> findByCode(String
	 * underWritingLoadingCode);
	 */
	
	@Query("FROM UnderwritingLoadingMst WHERE underWritingLoadingCode = :code")
	public Optional<UnderwritingLoadingMst> findByCode(String code);
	
}

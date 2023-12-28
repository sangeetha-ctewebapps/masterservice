package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.UnderwritingDecisionMst;

@Repository
public interface UnderwritingDecisionRepository extends JpaRepository<UnderwritingDecisionMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM UNDERWRITING_DECISION WHERE UNDERWRITING_DECISION_CODE = :underWritingDecisionCode"
	 * , nativeQuery = true) Optional<UnderwritingDecisionMst> findByCode(String
	 * underWritingDecisionCode);
	 */
	
	@Query("FROM UnderwritingDecisionMst WHERE underwritingDecisionCode = :code")
	public Optional<UnderwritingDecisionMst> findByCode(String code);

}

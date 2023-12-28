package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ModeOfSettlementMst;

@Repository
public interface ModeOfSettlementRepository extends JpaRepository<ModeOfSettlementMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM MODE_OF_SETTLEMENT WHERE MODE_OF_SETTLEMENT_CODE = :modeOfSettlementCode"
	 * , nativeQuery = true) Optional<ModeOfSettlementMst> findByCode(String
	 * modeOfSettlementCode);
	 */
	
	@Query("FROM ModeOfSettlementMst WHERE modeOfSettlementCode = :code")
	public Optional<ModeOfSettlementMst> findByCode(String code);

}

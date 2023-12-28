package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lic.epgs.mst.entity.FundNameMaster;

public interface FundNameRepository extends JpaRepository<FundNameMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_FUND_NAME WHERE FUND_NAME_CODE = :fundNameCode", nativeQuery = true)
	public Optional<FundNameMaster> findByCode(String fundNameCode);

}

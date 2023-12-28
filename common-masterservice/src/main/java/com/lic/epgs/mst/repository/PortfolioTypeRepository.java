package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.PortfolioTypeMaster;

@Repository
public interface PortfolioTypeRepository extends JpaRepository<PortfolioTypeMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_PORTFOLIO_TYPE WHERE PORTFOLIO_TYPE_CODE = :portfolioTypeCode", nativeQuery = true)
	Optional<PortfolioTypeMaster> findByCode(String portfolioTypeCode);
}

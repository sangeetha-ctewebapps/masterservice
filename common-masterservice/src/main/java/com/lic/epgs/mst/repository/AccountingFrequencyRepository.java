package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.AccountingFrequencyMaster;

@Repository
public interface AccountingFrequencyRepository extends JpaRepository<AccountingFrequencyMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_ACCOUNTING_FREQUENCY WHERE ACCOUNTING_FREQUENCY_CODE = :accountingFrequencyCode", nativeQuery = true)
	Optional<AccountingFrequencyMaster> findByCode(String accountingFrequencyCode);

}

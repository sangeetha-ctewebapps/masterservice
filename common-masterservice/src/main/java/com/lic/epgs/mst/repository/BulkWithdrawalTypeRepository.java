package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.BulkTypeMst;

@Repository
public interface BulkWithdrawalTypeRepository extends JpaRepository<BulkTypeMst, Long> {

	@Query(value = "SELECT * FROM MASTER_BULK WHERE BULK_WITHDRAWAL_TYPE_CODE = :bulkWithdrawalTypeCode", nativeQuery = true)
	 public Optional<BulkTypeMst> findByCode(String bulkWithdrawalTypeCode);

}

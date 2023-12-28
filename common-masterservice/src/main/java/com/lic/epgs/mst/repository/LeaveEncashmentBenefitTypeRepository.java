package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.LeaveEncashmentBenefitTypeMaster;

@Repository
public interface LeaveEncashmentBenefitTypeRepository extends JpaRepository<LeaveEncashmentBenefitTypeMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_LEAVE_ENCASHMENT_BENEFIT_TYPE WHERE MASTER_LEAVE_ENCASHMENT_BENEFIT_TYPE_CODE = :leaveEncashmentBenefitTypeCode", nativeQuery = true)
	public Optional<LeaveEncashmentBenefitTypeMaster> findByCode(String leaveEncashmentBenefitTypeCode);

}

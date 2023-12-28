package com.lic.epgs.mst.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ReasonForRefundEntity;

@Repository

public interface ReasonForRefundRepository extends JpaRepository<ReasonForRefundEntity, Long> {
	
	
	@Query(value = "select * from REASON_FOR_REFUND WHERE IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public List<ReasonForRefundEntity> getAllReasonForRefund();
	
	@Query(value ="SELECT * FROM REASON_FOR_REFUND WHERE REFUND_CODE =:refundCode and IS_ACTIVE='Y' and IS_DELETED='N' order by MODIFIED_ON DESC ", nativeQuery = true)
	public ReasonForRefundEntity getRefundReasonByRefundCode(@Param("refundCode") String refundCode);
	
	
	
	

}

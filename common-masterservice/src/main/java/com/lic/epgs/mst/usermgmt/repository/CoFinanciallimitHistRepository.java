package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitHistoryEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersHistoryDetailsEntity;

@Repository
public interface CoFinanciallimitHistRepository extends JpaRepository<CoFinancialLimitHistoryEntity, Long> {

	@Query(value = "select * from CO_FINANCIAL_LIMIT_HISTORY order by CREATEDON desc", nativeQuery = true) 
	List<CoFinancialLimitHistoryEntity> getAllHistoryDetails();
	
	
	@Query(value = "select * from CO_FINANCIAL_LIMIT_HISTORY where (:adminSrNo is null or LOGGEDINUSER_SRNUMBER = :adminSrNo) order by CREATEDON desc", nativeQuery = true) 
	List<CoFinancialLimitHistoryEntity> getAllHistoryDetailsBasedOnAdminSrNo(@Param("adminSrNo") String adminSrNo);
	
	@Query(value = "select * from CO_FINANCIAL_LIMIT_HISTORY where (:adminSrNo is null or LOGGEDINUSER_SRNUMBER = :adminSrNo) and trunc(CREATEDON) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD')  order by CREATEDON desc", nativeQuery = true) 
	List<CoFinancialLimitHistoryEntity> getAllHistoryDetailsBasedOnAdminSrNoAndDate(@Param("adminSrNo") String adminSrNo,@Param("fromDate") String fromDate,@Param("toDate") String toDate );
}

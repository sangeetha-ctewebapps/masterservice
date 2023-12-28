package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitEntity;
import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;

@Repository
public interface CoFinancialLimitRepository extends JpaRepository<CoFinancialLimitEntity, Long> {

	@Query(value = "SELECT * FROM CO_FINANCIAL_LIMIT WHERE COFINLIMIT_ID=:cofinLimitId and IS_ACTIVE='Y'", nativeQuery = true)
	public CoFinancialLimitEntity getCofinancialLimitDetail(@Param("cofinLimitId") long cofinLimitId);
    
	
	@Query(value = "SELECT * FROM CO_FINANCIAL_LIMIT WHERE COFINLIMIT_ID=:cofinLimitId and IS_ACTIVE='Y'", nativeQuery = true)
	public CoFinancialLimitEntity getAllCofinancialLimitDetail(@Param("cofinLimitId") long cofinLimitId);
	
	@Transactional
	@Modifying	
	@Query(value = "DELETE FROM CO_FINANCIAL_LIMIT  WHERE COFINLIMIT_ID=:cofinlimitId", nativeQuery = true) 
	public void findAndDeletedById(@Param("cofinlimitId") Long cofinlimitId);

	@Query(value = "SELECT * FROM CO_FINANCIAL_LIMIT WHERE COFINLIMIT_ID=:cofinlimitId and IS_ACTIVE='Y'", nativeQuery = true)
	public List<UOAdminEntity> findDuplicate(@Param("cofinlimitId") long cofinlimitId);
	
	 @Query(value = "select * from CO_FINANCIAL_LIMIT where FUNCTION_NAME = :functionName AND DESIGNATION = :designation", nativeQuery = true)
     public List<CoFinancialLimitEntity> findDuplicate(@Param("functionName") String functionName, @Param("designation") String designation);

	 @Query(value = "SELECT * FROM CO_FINANCIAL_LIMIT ", nativeQuery = true)
	 public List<CoFinancialLimitEntity> findAll();
	 
	 @Query(value = "SELECT * FROM CO_FINANCIAL_LIMIT WHERE WORKFLOW_STATUS = '2' and STATUS ='approved' and DESIGNATION=:designation and IS_ACTIVE='Y' and (OFFICENAME is null or OFFICENAME=:locationType) and lower(FUNCTION_NAME)=lower(:functionName) and (:productCode is null or lower(PRODUCT) like '%' || lower(:productCode) || '%') and (:groupVariantCode is null or lower(GROUP_VARIANT)=lower(:groupVariantCode))", nativeQuery = true)
	 public CoFinancialLimitEntity getCofinancialLimitDetailUsingMultipleParameters(@Param("designation") String designation, @Param("locationType") String locationType, @Param("functionName") String functionName,@Param("productCode") String productCode,@Param("groupVariantCode") String groupVariantCode);
	 
	 @Query(value = "SELECT * FROM CO_FINANCIAL_LIMIT WHERE WORKFLOW_STATUS = '2' and STATUS ='approved' and IS_ACTIVE='Y' and (OFFICENAME is null or OFFICENAME=:locationType) and lower(FUNCTION_NAME)=lower(:functionName)", nativeQuery = true)
	 public CoFinancialLimitEntity getCofinancialLimitDetailUsingMultipleParametersForUW(@Param("locationType") String locationType, @Param("functionName") String functionName);
	 
	 @Query(value = "SELECT * FROM CO_FINANCIAL_LIMIT WHERE WORKFLOW_STATUS = '2' and STATUS ='approved' and IS_ACTIVE='Y' and (OFFICENAME is null or OFFICENAME=:locationType) and lower(FUNCTION_NAME)=lower(:functionName) and lower(GROUP_VARIANT)=lower(:groupVariantCode) and DESIGNATION='ALL'", nativeQuery = true)
	 public CoFinancialLimitEntity getCofinancialLimitDetailUsingMultipleParametersWithGroupForUW(@Param("locationType") String locationType, @Param("functionName") String functionName, @Param("groupVariantCode") String groupVariantCode);

}

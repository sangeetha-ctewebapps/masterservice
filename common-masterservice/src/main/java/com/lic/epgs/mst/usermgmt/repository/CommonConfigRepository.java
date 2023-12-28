package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.CommonConfig;

@Repository
public interface CommonConfigRepository extends JpaRepository<CommonConfig, Long> {

	@Query(value = "SELECT value FROM USERMGMT_COMMON_CONFIG WHERE  UPPER(KEY1)=UPPER(:key1)", nativeQuery = true)
	 public Long getValueFromCommonConfig(@Param("key1") String key1);
	
	@Query(value = "SELECT * FROM USERMGMT_COMMON_CONFIG WHERE UPPER(MODULE)=UPPER(:module)", nativeQuery = true)
	 public List<CommonConfig> getCommonConfigDetailsBasedOnModule(@Param("module") String module);
	
	@Query(value = "SELECT * FROM USERMGMT_COMMON_CONFIG WHERE UPPER(KEY2)=UPPER(:key2)", nativeQuery = true)
	 public CommonConfig getDataFromCommonConfig(@Param("key2") String key2);
}

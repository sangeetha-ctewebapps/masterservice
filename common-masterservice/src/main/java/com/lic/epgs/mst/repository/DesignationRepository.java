package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.AddressType;
import com.lic.epgs.mst.entity.DesignationMaster;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;

@Repository
public interface DesignationRepository extends JpaRepository<DesignationMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_DESIGNATION WHERE DESIGNATION_CODE =:designationCode", nativeQuery = true)
	public DesignationMaster findByCode(String designationCode);
	
	@Transactional
	@Modifying	
	@Query(value = "  DELETE FROM MASTER_DESIGNATION  WHERE DESIGNATION_ID= :designationId", nativeQuery = true) 
	public void deleteBydesignationId(@Param("designationId")Long designationId);
	
	@Query(value = "select * from MASTER_DESIGNATION where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	public List<DesignationMaster> findAllByCondition();
	
	@Query(value="SELECT * FROM MASTER_DESIGNATION ORDER BY created_on desc", nativeQuery = true)
	public List<DesignationMaster> getAllDesignation();
	
	@Query(value = "select * from MASTER_DESIGNATION where IS_ACTIVE = 'Y' and IS_DELETED = 'N'  AND DESCRIPTION =:description OR DESIGNATION_CODE = :designationCode", nativeQuery = true)
    public List<DesignationMaster> findDuplicate(@Param("description") String description,@Param("designationCode") String designationCode);

 
	@Query(value = "  SELECT * FROM MASTER_DESIGNATION  WHERE DESIGNATION_ID= :designationId", nativeQuery = true) 
	public DesignationMaster getAllDesignationBydesignationId(@Param("designationId")Long designationId);

}

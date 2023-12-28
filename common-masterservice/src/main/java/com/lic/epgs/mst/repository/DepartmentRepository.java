package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.AddressType;
import com.lic.epgs.mst.entity.DepartmentMaster;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_DEPARTMENT WHERE DEPARTMENT_CODE =:departmentCode", nativeQuery = true)
	Optional<DepartmentMaster> findByCode(String departmentCode);
	
	@Modifying
	@Query(value = "UPDATE MASTER_DEPARTMENT SET IS_DELETED ='Y' WHERE DEPARTMENT_ID =:departmentId", nativeQuery = true)
	Integer findDeletedById(long departmentId);
	
	@Query(value = "select * from MASTER_DEPARTMENT where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	public List<DepartmentMaster> findAllByCondition();

}

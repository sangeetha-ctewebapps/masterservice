package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.MedicalTestMst;

@Repository
public interface MedicalTestRepository extends JpaRepository<MedicalTestMst, Long> {

	@Query(value = "SELECT * FROM MEDICAL_TEST WHERE MEDICAL_TEST_CODE = :medicalTestCode", nativeQuery = true)
	Optional<MedicalTestMst> findByCode(String medicalTestCode);
	
	@Modifying
	@Query(value = "UPDATE MASTER_MEDICAL_TEST SET IS_DELETED ='Y',IS_ACTIVE = 'N' WHERE MEDICAL_TEST_ID= :medicalTestId", nativeQuery = true)
	Integer findDeletedById(long medicalTestId);
	
	@Modifying	
	@Query(value = "UPDATE MASTER_MEDICAL_TEST SET IS_DELETED ='Y',IS_ACTIVE = 'N' WHERE MEDICAL_TEST_ID= :medicalTestId", nativeQuery = true) 
	public void deleteByMedicalTestId(@Param("medicalTestId")Long medicalTestId);
	
	@Query(value = "select * from MASTER_MEDICAL_TEST where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	  public List<MedicalTestMst> findAllByCondition();


}

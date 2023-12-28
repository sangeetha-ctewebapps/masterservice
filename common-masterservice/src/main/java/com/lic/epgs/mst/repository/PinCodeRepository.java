package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.PinCode;
import com.lic.epgs.mst.entity.StateMaster;

@Repository
public interface PinCodeRepository extends JpaRepository<PinCode, Long> {

	@Query("SELECT DISTINCT r FROM PinCode r WHERE r.pinCode = :pincode")
	public Optional<PinCode> findByPinCode(String pincode);
	
	public List<StateMaster> findByCity(@Param("id") long cityId);
	
	@Modifying
	@Query(value = "UPDATE MASTER_PIN_CODE SET IS_DELETED ='Y' WHERE PIN_ID=:id", nativeQuery = true)
	Integer findDeletedById(long id);
	
	@Modifying	
	@Query(value = "UPDATE MASTER_PIN_CODE SET IS_DELETED ='Y',IS_ACTIVE = 'N' WHERE PIN_ID=:pinId", nativeQuery = true) 
	public void deleteByPinId(@Param("pinId")Long pinId);
	
	@Query(value = "select * from MASTER_PIN_CODE where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	  public List<PinCode> findAllByCondition();
	
		/*
		 * @Query(value =
		 * "SELECT MP.PIN_ID, MP.PIN_CODE, MC.CITY_CODE, MC.DESCRIPTION CITY_DESCRIPTION, MD.DISTRICT_CODE, MD.DESCRIPTION DISTRICT_DESCRIPTION, MS.STATE_CODE, MS.DESCRIPTION STATE_DESCRIPTION, MCC.COUNTRY_CODE, MCC.DESCRIPTION COUNTRY_DESCRIPTION\r\n"
		 * +
		 * "FROM MASTER_PIN_CODE MP, MASTER_CITY MC, MASTER_DISTRICT MD, MASTER_STATE MS, MASTER_COUNTRY MCC\r\n"
		 * +
		 * "WHERE MP.CITY_ID = MC.CITY_ID AND MC.DISTRICT_ID = MD.DISTRICT_ID AND MD.STATE_ID = MS.STATE_ID AND MS.COUNTRY_ID = MCC.COUNTRY_ID\r\n"
		 * +
		 * "AND (MP.PIN_CODE = :pPINCODE OR :pPINCODE IS NULL) AND ( MD.DISTRICT_ID = :pDISTRICTID OR :pDISTRICTID IS NULL) \r\n"
		 * + "AND (MS.STATE_ID = :pSTATEID OR :pSTATEID IS NULL)", nativeQuery = true)
		 * public List<PinSearchEntity> findPinCodeByPinCode();
		 */

}

package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.AddressType;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {

	@Query("SELECT DISTINCT r FROM AddressType r WHERE r.addressCode = :addresscode")
	public Optional<AddressType> findByAddressTypeCode(String addresscode);
	
	@Modifying
	@Query(value = "UPDATE MASTER_ADDRESS_TYPE SET IS_DELETED ='Y' WHERE ADDRESS_ID= :id", nativeQuery = true)
	Integer findDeletedById(long id);

	@Query(value = "select * from MASTER_ADDRESS_TYPE where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	public List<AddressType> findAllByCondition();
	
}

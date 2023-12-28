package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CountryMaster;

@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<CountryMaster, Long> {

	Optional<CountryMaster> findByCountryCode(String countryCode);
	
	@Query(value = "select * from MASTER_COUNTRY where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	  public List<CountryMaster> findAllByCondition();
	
	@Query(value="SELECT * FROM MASTER_COUNTRY ORDER BY DESCRIPTION asc", nativeQuery = true)
	List<CountryMaster> findAllCountry();

	
}

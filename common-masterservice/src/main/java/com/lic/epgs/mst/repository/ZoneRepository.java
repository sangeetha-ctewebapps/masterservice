package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ZoneMaster;

@Repository
public interface ZoneRepository extends JpaRepository<ZoneMaster, Long> {

	/*
	 * @Query(value = "SELECT * FROM MASTER_ZONE WHERE ZONE_CODE = :zoneCode",
	 * nativeQuery = true) Optional<ZoneMaster> findByCode(String zoneCode);
	 */
	
	@Query("FROM ZoneMaster WHERE zoneCode = :code")
	public Optional<ZoneMaster> findByCode(String code);

}

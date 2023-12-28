package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.EncashmentFrequencyMaster;

@Repository
public interface EncashmentFrequencyRepository extends JpaRepository<EncashmentFrequencyMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_ENCASHMENT_FREQUENCY WHERE ENCASHMENT_FREQUENCY_CODE = :encashmentFrequencyCode", nativeQuery = true)
	public Optional<EncashmentFrequencyMaster> findByCode(String encashmentFrequencyCode);

}

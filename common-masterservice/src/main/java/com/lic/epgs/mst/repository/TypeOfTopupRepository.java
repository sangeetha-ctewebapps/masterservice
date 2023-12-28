package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TypeOfTopupMaster;

@Repository
public interface TypeOfTopupRepository extends JpaRepository<TypeOfTopupMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_TYPE_TOPUP WHERE TYPE_TOPUP_CODE = :typeTopupCode", nativeQuery = true)
	 Optional<TypeOfTopupMaster> findByCode(String typeTopupCode);

}

package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TypeOfSwitchMaster;

@Repository
public interface TypeOfSwitchRepository extends JpaRepository<TypeOfSwitchMaster, Long> {
	
	/*
	 * @Query(value =
	 * "SELECT * FROM MASTER_TYPE_SWITCH WHERE TYPE_SWITCH_CODE = :typeSwitchCode",
	 * nativeQuery = true) Optional<TypeOfSwitchMaster> findByCode(String
	 * typeSwitchCode);
	 */
	
	@Query("FROM TypeOfSwitchMaster WHERE typeSwitchCode = :code")
	public Optional<TypeOfSwitchMaster> findByCode(String code);

}

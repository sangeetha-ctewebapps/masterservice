package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ModeTypeMst;

@Repository
public interface ModeOfExitRepository extends JpaRepository<ModeTypeMst, Long> {

	/*
	 * @Query(value = "SELECT * FROM MODE_MST WHERE MODE_TYPE_CODE = :modeTypeCode",
	 * nativeQuery = true) Optional<ModeTypeMst> findByCode(String modeTypeCode);
	 */
	
	 @Query("FROM ModeTypeMst WHERE modeTypeCode = :code")
	public Optional<ModeTypeMst> findByCode(String code);

}

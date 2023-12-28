package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.FclTypeMst;

@Repository
public interface FclTypeRepository extends JpaRepository<FclTypeMst, Long> {

	/*
	 * @Query(value = "SELECT * FROM FCL_TYPE WHERE FCL_TYPE_CODE = :fclTypeCode",
	 * nativeQuery = true) Optional<FclTypeMst> findByFclTypeCode(String
	 * fclTypeCode);
	 */
	
	@Query("FROM FclTypeMst WHERE fclTypeCode = :code")
	public Optional<FclTypeMst> findByFclTypeCode(String code);
	
	

}

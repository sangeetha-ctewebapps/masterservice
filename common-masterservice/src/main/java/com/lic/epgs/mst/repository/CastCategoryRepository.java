package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CastCategoryMst;

@Repository
public interface CastCategoryRepository extends JpaRepository<CastCategoryMst, Long> {

	@Query(value = "SELECT * FROM MASTER_CAST_CATEGORY WHERE CAST_CATEGORY_CODE = :castCategoryCode", nativeQuery = true)
	Optional<CastCategoryMst> findByCode(String castCategoryCode);

}

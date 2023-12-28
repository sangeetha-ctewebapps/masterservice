package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.RequirementCategoryMst;

@Repository
 public interface RequirementCategoryRepository extends JpaRepository<RequirementCategoryMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM REQUIREMENT_CATEGORY WHERE REQUIREMENT_CATEGORY_CODE = :requirementCategoryCode"
	 * , nativeQuery = true) Optional<RequirementCategoryMst> findByCode(String
	 * requirementCategoryCode);
	 */
	
	@Query("FROM RequirementCategoryMst WHERE requirementCategoryCode = :requirementcategorycode")
	public Optional<RequirementCategoryMst> findByCode(String requirementcategorycode);
}

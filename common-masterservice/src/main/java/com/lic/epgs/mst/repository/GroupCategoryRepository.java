package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.GroupCategoryMst;

@Repository
public interface GroupCategoryRepository extends JpaRepository<GroupCategoryMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM GROUP_CATEGORY WHERE GROUP_CATEGORY_CODE = :groupCategoryCode"
	 * , nativeQuery = true) Optional<GroupCategoryMst> findByCode(String
	 * groupCategoryCode);
	 */
	
	@Query("FROM GroupCategoryMst WHERE groupCategoryCode = :code")
	public Optional<GroupCategoryMst> findByCode(String code);

}

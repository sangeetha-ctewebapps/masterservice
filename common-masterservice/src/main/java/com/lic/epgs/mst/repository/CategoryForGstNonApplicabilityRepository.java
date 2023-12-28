package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CategoryForGstNonApplicabilityMst;

@Repository
public interface CategoryForGstNonApplicabilityRepository
		extends JpaRepository<CategoryForGstNonApplicabilityMst, Long> {

	@Query(value = "SELECT * FROM MASTER_CATEGORY_FOR_GST_NON_APPLICABILITY WHERE CATEGORY_FOR_GST_NON_APPLICABILITY_CODE = :categoryForGstNonApplicabilityMstCode", nativeQuery = true)
	Optional<CategoryForGstNonApplicabilityMst> findByCode(String categoryForGstNonApplicabilityMstCode);

}

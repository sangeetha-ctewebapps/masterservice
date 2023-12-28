package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CategoryForGstNonApplicabilityMst;

public interface CategoryForGstNonApplicabilityService {
	List<CategoryForGstNonApplicabilityMst> getAllCategoryForGstNonApplicability();

	public CategoryForGstNonApplicabilityMst getCategoryForGstNonApplicabilityById(long id);

	public CategoryForGstNonApplicabilityMst getCategoryForGstNonApplicabilityByCode(String code);
}

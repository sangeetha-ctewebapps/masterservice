package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.RequirementCategoryMst;

public interface RequirementCategoryService {

	List<RequirementCategoryMst> getAllRequirementCategory();

	public RequirementCategoryMst getRequirementCategoryById(long requirementcategoryId);
	
	public RequirementCategoryMst getRequirementCategoryByCode(String requirementcategorycode);

}

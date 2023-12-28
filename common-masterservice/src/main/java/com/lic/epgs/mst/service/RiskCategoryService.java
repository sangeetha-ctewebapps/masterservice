package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.RiskCategoryMaster;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.RiskCategoryServiceException;

public interface RiskCategoryService {

	List<RiskCategoryMaster> getAllRiskCategory() throws ResourceNotFoundException, RiskCategoryServiceException;

	public RiskCategoryMaster getRiskCategoryById(long id);

	public RiskCategoryMaster getRiskCategoryByCode(String code);

}

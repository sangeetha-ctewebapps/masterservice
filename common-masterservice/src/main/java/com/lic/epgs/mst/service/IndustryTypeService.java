package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.IndustryType;
import com.lic.epgs.mst.exceptionhandling.IndustryTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface IndustryTypeService {

	List<IndustryType> getAllIndustryType() throws ResourceNotFoundException, IndustryTypeServiceException;

	public IndustryType getIndustryTypeById(long industryId);

	public IndustryType getIndustryTypeByCode(String industryCode);

}

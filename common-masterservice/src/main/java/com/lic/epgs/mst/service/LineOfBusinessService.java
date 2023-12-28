package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LineOfBusinessMaster;

public interface LineOfBusinessService {

	List<LineOfBusinessMaster> getAllLineOfBusiness();
	
	List<LineOfBusinessMaster> getAllLineOfBusinessByCondition();

	public LineOfBusinessMaster getLineOfBusinessById(long id);

	public LineOfBusinessMaster getLineBusinessByCode(String code);

	LineOfBusinessMaster createLineOfBusiness(LineOfBusinessMaster lineOfBusiness);

	LineOfBusinessMaster updateLineOfBusiness(LineOfBusinessMaster lineOfBusiness);

	void deleteLineOfBusiness(Long id);

}

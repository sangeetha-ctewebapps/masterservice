package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CoverageType;

public interface CoverageTypeService {

	List<CoverageType> getAllCoverageType();

	public CoverageType getCoverageTypeById(long coverageId);

	public CoverageType findByCoverageCode(String coveragecode);

}

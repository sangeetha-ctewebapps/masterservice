package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ExtractionParameterMaster;

public interface ExtractionParameterService {

	List<ExtractionParameterMaster> getAllExtractionParameter();

	public ExtractionParameterMaster getExtractionParameterById(long id);

	public ExtractionParameterMaster getExtractionParameterByCode(String code);

}

package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CessionType;
import com.lic.epgs.mst.exceptionhandling.CessionTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface CessionTypeService {

	List<CessionType> getAllCession() throws ResourceNotFoundException, CessionTypeServiceException;

	public CessionType getCessionById(long id);

	public CessionType getCessionByCode(String code);
}

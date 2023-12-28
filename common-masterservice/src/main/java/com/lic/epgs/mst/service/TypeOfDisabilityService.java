package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfDisability;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfDisabilityServiceException;

public interface TypeOfDisabilityService {

	List<TypeOfDisability> getAllDisability() throws ResourceNotFoundException, TypeOfDisabilityServiceException;

	public TypeOfDisability getDisabilityById(long id);

	public TypeOfDisability getDisabilityByCode(String code);
}

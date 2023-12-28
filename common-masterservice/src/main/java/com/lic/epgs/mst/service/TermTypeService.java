package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TermType;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TermTypeServiceException;

public interface TermTypeService {

	List<TermType> getAllTerm() throws ResourceNotFoundException, TermTypeServiceException;

	public TermType getTermById(long termId);

	public TermType getTermByCode(String termCode);
}

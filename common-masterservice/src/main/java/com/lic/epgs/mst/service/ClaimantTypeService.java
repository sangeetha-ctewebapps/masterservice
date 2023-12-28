package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ClaimantType;
import com.lic.epgs.mst.exceptionhandling.ClaimantTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface ClaimantTypeService {
	List<ClaimantType> getAllClaimant() throws ResourceNotFoundException, ClaimantTypeServiceException;

	public ClaimantType getClaimantById(long id);

	public ClaimantType getClaimantByCode(String code);
}

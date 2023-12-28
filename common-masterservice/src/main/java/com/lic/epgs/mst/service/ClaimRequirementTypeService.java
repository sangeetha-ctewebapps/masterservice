package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ClaimRequirementType;
import com.lic.epgs.mst.exceptionhandling.ClaimRequirementTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface ClaimRequirementTypeService {

	List<ClaimRequirementType> getAllClaim() throws ResourceNotFoundException, ClaimRequirementTypeServiceException;

	public ClaimRequirementType getClaimById(long claimId);

	public ClaimRequirementType getClaimByCode(String claimCode);

}

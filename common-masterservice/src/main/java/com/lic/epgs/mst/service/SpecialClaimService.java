package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.SpecailClaimReasonEntity;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface SpecialClaimService {
	
	public List<SpecailClaimReasonEntity> getAllDetails() throws ResourceNotFoundException;

}

package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ClaimRecommendation;
import com.lic.epgs.mst.exceptionhandling.ClaimRecommendationServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface ClaimRecommendationService {

	List<ClaimRecommendation> getAllClaim() throws ResourceNotFoundException, ClaimRecommendationServiceException;

	public ClaimRecommendation getClaimById(long id);

	public ClaimRecommendation getClaimByCode(String claimCode);

}

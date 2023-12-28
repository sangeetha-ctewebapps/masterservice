package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ReasonForClaim;

public interface ReasonForClaimService {
	ReasonForClaim createReasonForClaim(ReasonForClaim reasonforclaim);

	List<ReasonForClaim> getAllReasonForClaim();

	public ReasonForClaim getById(long reasonforclaimId);
	
	public ReasonForClaim getReasonForClaimByCode(String code);

}

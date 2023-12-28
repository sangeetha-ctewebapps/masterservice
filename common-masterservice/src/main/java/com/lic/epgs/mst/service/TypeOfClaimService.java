package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfClaim;

public interface TypeOfClaimService {

	List<TypeOfClaim> getAllTypeOfClaim();

	public TypeOfClaim getTypeOfClaimById(long claimId);

	public TypeOfClaim findByTypeOfClaimCode(String claimcode);

}

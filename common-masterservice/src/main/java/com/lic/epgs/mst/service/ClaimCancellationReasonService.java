package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ClaimTypeMst;

public interface ClaimCancellationReasonService {

	List<ClaimTypeMst> getAllClaimCancellationReason();

	public ClaimTypeMst getClaimCancellationReasonById(long id);

	public ClaimTypeMst getClaimCancellationReasonByCode(String code);

}

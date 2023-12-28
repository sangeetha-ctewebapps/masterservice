package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.UnderwritingLoadingMst;

public interface UnderwritingLoadingService {
	
	List<UnderwritingLoadingMst> getAllUnderwritingLoading();

	public UnderwritingLoadingMst getUnderwritingLoadingById(long underwritingId);
	
	public UnderwritingLoadingMst getUnderwritingLoadingByCode(String code);

}

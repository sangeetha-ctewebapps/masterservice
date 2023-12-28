package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.PremiumAdjustmentTypeMaster;

public interface PremiumAdjustmentTypeService {

	List<PremiumAdjustmentTypeMaster> getAllPremium();

	public PremiumAdjustmentTypeMaster getPremiumById(long id);

	public PremiumAdjustmentTypeMaster getpremiumByCode(String code);

}

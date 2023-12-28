package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.AnnuityPayoutMethodMst;

public interface AnnuityPayoutMethodService {

	List<AnnuityPayoutMethodMst> getAllAnnuityPayoutMethod();

	public AnnuityPayoutMethodMst getAnnuityPayoutMethodById(long id);

	public AnnuityPayoutMethodMst getAnnuityPayoutMethodByCode(String code);

}

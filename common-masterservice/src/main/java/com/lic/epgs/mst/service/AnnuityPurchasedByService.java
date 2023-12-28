package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.AnnuityPurchasedBy;

public interface AnnuityPurchasedByService {

	List<AnnuityPurchasedBy> getAllAnnuityPurchasedBy();

	public AnnuityPurchasedBy getAnnuityPurchasedByById(long id);

	public AnnuityPurchasedBy findByCode(String code);

}

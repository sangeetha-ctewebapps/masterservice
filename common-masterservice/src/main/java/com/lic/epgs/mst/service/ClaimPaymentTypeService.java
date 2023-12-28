package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ClaimPaymentTypeMst;

public interface ClaimPaymentTypeService {

	List<ClaimPaymentTypeMst> getAllClaimPaymentType();

	public ClaimPaymentTypeMst getClaimPaymentTypeById(long id);

	public ClaimPaymentTypeMst getClaimPaymentTypeByCode(String code);

}



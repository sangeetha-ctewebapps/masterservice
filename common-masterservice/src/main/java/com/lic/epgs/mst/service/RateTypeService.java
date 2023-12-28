package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.RateTypeMst;

public interface RateTypeService {

	List<RateTypeMst> getAllRateType();

	public RateTypeMst getRateTypeById(long ratetypeId);
	
	public RateTypeMst getRateTypeByCode(String code);

}

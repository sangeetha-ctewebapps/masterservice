package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.SubVariantMst;

public interface SubVariantService {

	List<SubVariantMst> getAllSubVariant();

	public SubVariantMst getSubVariantById(long id);
	
	public SubVariantMst getSubVariantByCode(String code);

}

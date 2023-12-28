package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.GratuityTypeMst;

public interface GratuityBenefitTypeService {

	List<GratuityTypeMst> getAllGratuityBenefitType();

	public GratuityTypeMst getGratuityBenefitTypeById(long gratuityId);

	public GratuityTypeMst getGratuityBenefitTypeByCode(String code);
}

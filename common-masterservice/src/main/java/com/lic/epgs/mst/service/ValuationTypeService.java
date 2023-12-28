package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ValuationTypeMst;

public interface ValuationTypeService {

	List<ValuationTypeMst> getAllValuationType();

	public ValuationTypeMst getValuationTypeById(long valuationId);

	public ValuationTypeMst getValuationTypeByCode(String code);
}

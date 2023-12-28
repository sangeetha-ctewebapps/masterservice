package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TreatyTypeMst;

public interface TreatyTypeService {
	TreatyTypeMst createTreatyType(TreatyTypeMst treatytype);

	List<TreatyTypeMst> getAllTreatyType();

	public TreatyTypeMst getTreatyTypeById(long treatytypeId);
	
	public TreatyTypeMst getTreatyTypeByCode(String code);

}

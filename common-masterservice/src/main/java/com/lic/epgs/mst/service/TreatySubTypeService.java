package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TreatySubTypeMst;

public interface TreatySubTypeService {

	List<TreatySubTypeMst> getAllTreatySubType();

	public TreatySubTypeMst getTreatySubTypeById(long id);
	
	public TreatySubTypeMst getTreatySubTypeByCode(String code);



}

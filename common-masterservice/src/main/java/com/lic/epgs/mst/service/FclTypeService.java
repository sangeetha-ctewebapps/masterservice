package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.FclTypeMst;

public interface FclTypeService {

	List<FclTypeMst> getAllFclType();

	public FclTypeMst getFclTypeById(long id);

	public FclTypeMst getFclTypeByCode(String code);

}

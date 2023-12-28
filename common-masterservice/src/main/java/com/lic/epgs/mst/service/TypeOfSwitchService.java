package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfSwitchMaster;

public interface TypeOfSwitchService {

	List<TypeOfSwitchMaster> getAllTypeOfSwitchType();

	public TypeOfSwitchMaster getTypeOfSwitchById(long id);

	public TypeOfSwitchMaster getTypeOfSwitchByCode(String code);

}

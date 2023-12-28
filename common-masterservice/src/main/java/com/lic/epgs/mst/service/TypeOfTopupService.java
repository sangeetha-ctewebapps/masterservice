package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfTopupMaster;

public interface TypeOfTopupService {

	List<TypeOfTopupMaster> getAllTypeOfTopup();

	public TypeOfTopupMaster getTypeOfTopupById(long id);

	public TypeOfTopupMaster getTypeOfTopupByCode(String code);

}

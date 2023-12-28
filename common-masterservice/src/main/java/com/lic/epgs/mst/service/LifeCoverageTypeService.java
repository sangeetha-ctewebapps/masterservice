package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LifeCoverageTypeMst;

public interface LifeCoverageTypeService {

	List<LifeCoverageTypeMst> getAllLifeCoverageType();

	public LifeCoverageTypeMst getLifeCoverageTypeById(long id);

	public LifeCoverageTypeMst getLifeCoverageTypeByCode(String code);
}

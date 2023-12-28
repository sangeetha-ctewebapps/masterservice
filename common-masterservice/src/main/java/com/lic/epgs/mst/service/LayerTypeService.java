package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LayerTypeMaster;

public interface LayerTypeService {

	List<LayerTypeMaster> getAllLayerType();

	public LayerTypeMaster getLayerTypeById(long id);

	public LayerTypeMaster getLayerTypeByCode(String code);

}

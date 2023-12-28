package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LayerNameMaster;

public interface LayerNameService {

	List<LayerNameMaster> getAllLayerName();

	public LayerNameMaster getLayerNameById(long id);

	public LayerNameMaster getLayerNameByCode(String code);

}

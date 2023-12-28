package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.EncashPayModeMst;

public interface EncashPayModeService {

	List<EncashPayModeMst> getAllEncashPayMode();

	public EncashPayModeMst getEncashPayModeById(long id);

	public EncashPayModeMst getEncashPayModeByCode(String code);

}

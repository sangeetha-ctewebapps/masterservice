package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.PortfolioTypeMaster;

public interface PortfolioTypeService {

	List<PortfolioTypeMaster> getAllPortfolioType();

	public PortfolioTypeMaster getPortfolioTypeById(long id);

	public PortfolioTypeMaster getPortfolioTypeByCode(String code);

}

package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.FundNameMaster;

public interface FundNameService {

	List<FundNameMaster> getAllFundName();

	public FundNameMaster getFundNameById(long id);

	public FundNameMaster getFundNameByCode(String code);

}

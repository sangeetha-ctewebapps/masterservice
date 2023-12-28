package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TopUpValueMst;

public interface TopUpValueService {

	List<TopUpValueMst> getAllTopUpValue();

	public TopUpValueMst getTopUpValueById(long TopupvalueId);

	public TopUpValueMst getTopUpValueByCode(String code);
}

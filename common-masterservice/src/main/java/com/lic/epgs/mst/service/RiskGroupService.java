package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.RiskGroupMaster;

public interface RiskGroupService {

	List<RiskGroupMaster> getAllRiskGroup();

	public RiskGroupMaster getRiskGroupById(long id);

	public RiskGroupMaster getRiskGroupByCode(String code);

}

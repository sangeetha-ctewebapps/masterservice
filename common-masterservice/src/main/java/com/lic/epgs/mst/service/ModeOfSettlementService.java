package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ModeOfSettlementMst;

public interface ModeOfSettlementService {

	List<ModeOfSettlementMst> getAllModeOfSettlement();

	public ModeOfSettlementMst getModeOfSettlementById(long id);


	public ModeOfSettlementMst getModeOfSettlementByCode(String code);
}

package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.UnderwritingDecisionMst;

public interface UnderwritingDecisionService {
	UnderwritingDecisionMst createUnderwritingDecision(UnderwritingDecisionMst underwritingdecision);

	List<UnderwritingDecisionMst> getAllUnderwritingDecision();

	public UnderwritingDecisionMst getUnderwritingDecisionById(long underwritingdecisionId);
	
	public UnderwritingDecisionMst getUnderwritingDecisionByCode(String code);



}

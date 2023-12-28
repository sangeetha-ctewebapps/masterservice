package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.IssuedByMst;

public interface IssuedByService {

	List<IssuedByMst> getAllIssuedBy();

	public IssuedByMst getIssuedByById(long issuedbyId);

	public IssuedByMst getIssuedByByCode(String code);
}

package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.BulkTypeMst;

public interface BulkWithdrawalTypeService {

	List<BulkTypeMst> getAllBulkWithdrawalType();

	public BulkTypeMst getBulkWithdrawalTypeById(long id);

	public BulkTypeMst getBulkWithdrawalTypeByCode(String code);

}


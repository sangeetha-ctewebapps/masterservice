package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.MergerTypeMaster;

public interface MergerTypeService {

	List<MergerTypeMaster> getAllMergerType();

    public MergerTypeMaster getMergerTypeById(long id);

	public MergerTypeMaster getMergerTypeByCode(String code);

}

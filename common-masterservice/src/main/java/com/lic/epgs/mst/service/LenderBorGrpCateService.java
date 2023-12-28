package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LenderBorGrpCate;

public interface LenderBorGrpCateService {
	
	List<LenderBorGrpCate> getAllLenderBorGrpCate();

	public LenderBorGrpCate getLenderBorGrpCateById(long id);

	public LenderBorGrpCate getLenderBorGrpCateByCode(String code);

}

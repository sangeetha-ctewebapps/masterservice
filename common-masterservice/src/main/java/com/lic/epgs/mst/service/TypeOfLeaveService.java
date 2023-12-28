package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfLeaveMaster;

public interface TypeOfLeaveService {

	List<TypeOfLeaveMaster> getAllTypeOfLeave();

	public TypeOfLeaveMaster getTypeOfLeaveById(long id);

	public TypeOfLeaveMaster getTypeOfLeaveByCode(String code);

}

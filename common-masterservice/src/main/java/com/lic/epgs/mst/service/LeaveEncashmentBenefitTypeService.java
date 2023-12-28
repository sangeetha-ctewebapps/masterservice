package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LeaveEncashmentBenefitTypeMaster;

public interface LeaveEncashmentBenefitTypeService {

	List<LeaveEncashmentBenefitTypeMaster> getAllLeaveEncashmentBenefitType();

	public LeaveEncashmentBenefitTypeMaster getLeaveEncashmentBenefitTypeById(long id);

	public LeaveEncashmentBenefitTypeMaster getLeaveEncashmentBenefitTypeByCode(String code);

}

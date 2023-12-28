package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ReasonForRefundEntity;
import com.lic.epgs.mst.usermgmt.entity.CoAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;

public interface ReasonForRefundService {

	//List<ReasonForRefundEntity> getAllReasonForRefund();
	
	List<ReasonForRefundEntity> getAllReasonForRefund();

	
	public ReasonForRefundEntity getRefundReasonByRefundCode(String refundCode) throws Exception;

}



package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LockPeriodWhileInServiceMst;

public interface LockPeriodWhileInServiceService {

	List<LockPeriodWhileInServiceMst> getAllLockPeriodWhileInService();

	public LockPeriodWhileInServiceMst getLockPeriodWhileInServiceById(long id);


	public LockPeriodWhileInServiceMst getLockPeriodWhileInServiceByCode(String code);
}

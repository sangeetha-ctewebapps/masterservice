package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.LockPeriodWhileInServiceController;
import com.lic.epgs.mst.entity.LockPeriodWhileInServiceMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LockPeriodWhileInServiceRepository;

@Service
@Transactional
public class LockPeriodWhileInServiceImpl implements LockPeriodWhileInServiceService {

	@Autowired
	private LockPeriodWhileInServiceRepository lockPeriodWhileInServiceRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LockPeriodWhileInServiceController.class);

	@Override
	public List<LockPeriodWhileInServiceMst> getAllLockPeriodWhileInService() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get LockPeriodWhileInService list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return lockPeriodWhileInServiceRepository.findAll();
	}

	@Override
	public LockPeriodWhileInServiceMst getLockPeriodWhileInServiceById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LockPeriodWhileInServiceMst> lockperiodwhileinserviceDb = this.lockPeriodWhileInServiceRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for LockPeriodWhileInService By Id" + id);
		if (lockperiodwhileinserviceDb.isPresent()) {
			logger.info("LockPeriodWhileInService is  found with id" + id);
			return lockperiodwhileinserviceDb.get();
		} else {
			throw new ResourceNotFoundException("LockPeriodWhileInService not found with id:" + id);
		}
	}

	@Override
	public LockPeriodWhileInServiceMst getLockPeriodWhileInServiceByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LockPeriodWhileInServiceMst> lockperiodwhileinserviceDb = this.lockPeriodWhileInServiceRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for LockPeriodWhileInService By code" + code);
		if (lockperiodwhileinserviceDb.isPresent()) {
			logger.info("LockPeriodWhileInService is  found with code" + code);
			return lockperiodwhileinserviceDb.get();
		} else {
			throw new ResourceNotFoundException("LockPeriodWhileInService not found with code:" + code);
		}
	}

}

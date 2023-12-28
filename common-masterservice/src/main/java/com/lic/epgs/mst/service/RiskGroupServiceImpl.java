package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.RiskGroupController;
import com.lic.epgs.mst.entity.RiskGroupMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RiskGroupRepository;

@Transactional
@Service
public class RiskGroupServiceImpl implements RiskGroupService {

	@Autowired
	private RiskGroupRepository riskGroupRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RiskGroupController.class);

	@Override
	public List<RiskGroupMaster> getAllRiskGroup() {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get risk group list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return riskGroupRepository.findAll();

	}

	@Override
	public RiskGroupMaster getRiskGroupById(long id) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RiskGroupMaster> riskGroupDb = this.riskGroupRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for risk group By Id" + id);
		if (riskGroupDb.isPresent()) {
			logger.info("risk group is  found with id" + id);
			return riskGroupDb.get();
		} else {
			throw new ResourceNotFoundException("risk group not found with id:" + id);
		}
	}

	@Override
	public RiskGroupMaster getRiskGroupByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RiskGroupMaster> riskGroupCodeDb = this.riskGroupRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for risk group By code" + code);
		if (riskGroupCodeDb.isPresent()) {
			logger.info(" risk group is  found with code" + code);
			return riskGroupCodeDb.get();
		} else {
			throw new ResourceNotFoundException(" risk group not found with code:" + code);
		}
	}

}

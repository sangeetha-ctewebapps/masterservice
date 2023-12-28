package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.LeaveEncashmentBenefitTypeController;
import com.lic.epgs.mst.entity.LeaveEncashmentBenefitTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LeaveEncashmentBenefitTypeRepository;

@Service
@Transactional
public class LeaveEncashmentBenefitTypeServiceImpl implements LeaveEncashmentBenefitTypeService {

	@Autowired
	private LeaveEncashmentBenefitTypeRepository leaveEncashmentBenefitTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LeaveEncashmentBenefitTypeController.class);

	@Override
	public List<LeaveEncashmentBenefitTypeMaster> getAllLeaveEncashmentBenefitType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get leave encashment type list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return leaveEncashmentBenefitTypeRepository.findAll();
	}

	@Override
	public LeaveEncashmentBenefitTypeMaster getLeaveEncashmentBenefitTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LeaveEncashmentBenefitTypeMaster> leaveEncashmentTypeDb = this.leaveEncashmentBenefitTypeRepository
				.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for leave encashment type By Id" + id);
		if (leaveEncashmentTypeDb.isPresent()) {
			logger.info(" leave encashment type is  found with id" + id);
			return leaveEncashmentTypeDb.get();
		} else {
			throw new ResourceNotFoundException(" leave encashment type not found with id:" + id);
		}
	}

	@Override
	public LeaveEncashmentBenefitTypeMaster getLeaveEncashmentBenefitTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LeaveEncashmentBenefitTypeMaster> leaveEncashmentTypeCodeDb = this.leaveEncashmentBenefitTypeRepository
				.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for leave EncashmentType By code" + code);
		if (leaveEncashmentTypeCodeDb.isPresent()) {
			logger.info(" leave encashment type is  found with code" + code);
			return leaveEncashmentTypeCodeDb.get();
		} else {
			throw new ResourceNotFoundException(" leave encashment type not found with code:" + code);
		}
	}

}

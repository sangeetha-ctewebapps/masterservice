package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.TypeOfLeaveController;
import com.lic.epgs.mst.entity.TypeOfLeaveMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TypeOfLeaveRepository;

@Service
@Transactional
public class TypeOfLeaveServiceImpl implements TypeOfLeaveService {
	@Autowired
	private TypeOfLeaveRepository typeOfLeaveRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TypeOfLeaveController.class);

	@Override
	public List<TypeOfLeaveMaster> getAllTypeOfLeave() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get type of leave list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return typeOfLeaveRepository.findAll();
	}

	@Override
	public TypeOfLeaveMaster getTypeOfLeaveById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfLeaveMaster> typeLeaveDb = this.typeOfLeaveRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for type of leave By Id" + id);
		if (typeLeaveDb.isPresent()) {
			logger.info("  type leave is  found with id" + id);
			return typeLeaveDb.get();
		} else {
			throw new ResourceNotFoundException("  type  of leave not found with id:" + id);
		}
	}

	@Override
	public TypeOfLeaveMaster getTypeOfLeaveByCode(String code) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfLeaveMaster> typeLeaveCodeDb = this.typeOfLeaveRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for type leave By code" + code);
		if (typeLeaveCodeDb.isPresent()) {
			logger.info(" type leave is  found with code" + code);
			return typeLeaveCodeDb.get();
		} else {
			throw new ResourceNotFoundException(" type leave not found with code:" + code);
		}
	}

}

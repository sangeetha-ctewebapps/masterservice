package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.MaritalStatusController;
import com.lic.epgs.mst.entity.MaritalStatus;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.MaritalStatusServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.MaritalStatusRepo;

@Service
@Transactional
public class MaritalStatusServiceImpl implements MaritalStatusService {

	@Autowired
	MaritalStatusRepo maritalRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MaritalStatusController.class);

	@Override
	public List<MaritalStatus> getAllMarital() throws ResourceNotFoundException, MaritalStatusServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return maritalRepository.findAll();
	}

	@Override
	public MaritalStatus getMaritalById(long maritalId) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<MaritalStatus> maritalDb = this.maritalRepository.findById(maritalId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for marital By Id" + maritalId);
		if (maritalDb.isPresent()) {
			logger.info("customer type is not found with id" + maritalId);
			return maritalDb.get();
		} else {
			throw new ResourceNotFoundException("marital not found with id:" + maritalId);
		}
	}

	@Override
	public MaritalStatus getMaritalByCode(String maritalCode) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<MaritalStatus> maritalDb = this.maritalRepository.findByMaritalCode(maritalCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for marital By Code" + maritalCode);
		if (maritalDb.isPresent()) {
			logger.info("marital type is not found with code" + maritalCode);
			return maritalDb.get();
		} else {
			throw new ResourceNotFoundException("marital not found with code:" + maritalCode);
		}
	}

}

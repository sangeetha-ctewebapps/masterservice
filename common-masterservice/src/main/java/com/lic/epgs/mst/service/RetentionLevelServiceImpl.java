package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.RetentionLevelController;
import com.lic.epgs.mst.entity.RetentionLevel;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RetentionLevelRepo;

@Service
@Transactional
public class RetentionLevelServiceImpl implements RetentionLevelService {

	@Autowired
	private RetentionLevelRepo retentionRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RetentionLevelController.class);

	@Override
	public List<RetentionLevel> getAllRetention() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return retentionRepository.findAll();
	}

	@Override
	public RetentionLevel getRetentionById(long retentionId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RetentionLevel> retentionDb = this.retentionRepository.findById(retentionId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Retention Level By Id" + retentionId);
		if (retentionDb.isPresent()) {
			logger.info("Retention Level is found with id" + retentionId);
			return retentionDb.get();
		} else {
			throw new ResourceNotFoundException("Retention Level is not found with id:" + retentionId);
		}
	}

	@Override
	public RetentionLevel getRetentionByCode(String retentionCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RetentionLevel> retentionDb = this.retentionRepository.findByRetentionCode(retentionCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Retention Level By Code" + retentionCode);
		if (retentionDb.isPresent()) {
			logger.info("Retention Level is found with code" +retentionCode);
			return retentionDb.get();
		} else {
			throw new ResourceNotFoundException("Retention Level is not found with code:" + retentionCode);
		}
	}
}

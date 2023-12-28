package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.IndustryTypeController;
import com.lic.epgs.mst.entity.IndustryType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.IndustryTypeRepo;

@Service
@Transactional
public class IndustryTypeServiceImpl implements IndustryTypeService {

	@Autowired
	IndustryTypeRepo industryRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(IndustryTypeController.class);

	@Override
	public List<IndustryType> getAllIndustryType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return industryRepository.findAll();
	}

	@Override
	public IndustryType getIndustryTypeById(long industryId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<IndustryType> industryDb = this.industryRepository.findById(industryId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for loan By Id" + industryId);
		if (industryDb.isPresent()) {
			logger.info("industry type is found with id" + industryId);
			return industryDb.get();
		} else {
			throw new ResourceNotFoundException("industry not found with id:" + industryId);
		}
	}

	@Override
	public IndustryType getIndustryTypeByCode(String industryCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<IndustryType> loanDb = this.industryRepository.findByIndustryCode(industryCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for industry By Id" + industryCode);
		if (loanDb.isPresent()) {
			logger.info("industry type is found with code" + industryCode);
			return loanDb.get();
		} else {
			throw new ResourceNotFoundException("industry not found with code:" + industryCode);
		}
	}
}

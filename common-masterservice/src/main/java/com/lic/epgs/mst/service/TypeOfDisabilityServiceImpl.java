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

import com.lic.epgs.mst.controller.TypeOfDisabilityController;
import com.lic.epgs.mst.entity.TypeOfDisability;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfDisabilityServiceException;
import com.lic.epgs.mst.repository.TypeOfDisabilityRepo;

@Service
@Transactional
public class TypeOfDisabilityServiceImpl implements TypeOfDisabilityService {

	@Autowired
	private TypeOfDisabilityRepo disabilityRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TypeOfDisabilityController.class);

	@Override
	public List<TypeOfDisability> getAllDisability()
			throws ResourceNotFoundException, TypeOfDisabilityServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return disabilityRepository.findAll();
	}

	@Override
	public TypeOfDisability getDisabilityById(long disabilityId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfDisability> disabilityDb = this.disabilityRepository.findById(disabilityId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Type Of Disability By Id" + disabilityId);
		if (disabilityDb.isPresent()) {
			logger.info("Type Of Disability is not found with id" + disabilityId);
			return disabilityDb.get();
		} else {
			throw new ResourceNotFoundException("Type Of Disability is not found with id:" + disabilityId);
		}
	}

	@Override
	public TypeOfDisability getDisabilityByCode(String disabilityCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfDisability> disabilityDb = this.disabilityRepository.findByDisabilityCode(disabilityCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Type Of Disability By Code" + disabilityCode);
		if (disabilityDb.isPresent()) {
			logger.info("Type Of Disability is found with code" + disabilityCode);
			return disabilityDb.get();
		} else {
			throw new ResourceNotFoundException("Type Of Disability is not found with code:" + disabilityCode);
		}
	}

}

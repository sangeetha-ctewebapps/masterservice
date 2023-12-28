package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.CessionTypeController;
import com.lic.epgs.mst.entity.CessionType;
import com.lic.epgs.mst.exceptionhandling.CessionTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CessionTypeRepo;

@Service
@Transactional
public class CessionTypeServiceImpl implements CessionTypeService {

	@Autowired
	private CessionTypeRepo cessionRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CessionTypeController.class);

	@Override
	public List<CessionType> getAllCession() throws ResourceNotFoundException, CessionTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return cessionRepository.findAll();
	}

	@Override
	public CessionType getCessionById(long cessionId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CessionType> cessionDb = this.cessionRepository.findById(cessionId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Cession Type By Id" + cessionId);
		if (cessionDb.isPresent()) {
			logger.info("Cession Type is found with id" + cessionId);
			return cessionDb.get();
		} else {
			throw new ResourceNotFoundException("Cession Type is not found with id:" + cessionId);
		}
	}

	@Override
	public CessionType getCessionByCode(String cessionCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CessionType> cessionDb = this.cessionRepository.findByCessionCode(cessionCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Cession Type By Code" + cessionCode);
		if (cessionDb.isPresent()) {
			logger.info("Cession Type is found with code" + cessionCode);
			return cessionDb.get();
		} else {
			throw new ResourceNotFoundException("Cession Type is not found with code:" + cessionCode);
		}
	}
}

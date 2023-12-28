package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.LifeCoverageTypeController;
import com.lic.epgs.mst.entity.LifeCoverageTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LifeCoverageTypeRepository;

@Service
@Transactional
public class LifeCoverageTypeServiceImpl implements LifeCoverageTypeService {

	@Autowired
	LifeCoverageTypeRepository lifecoveragetyperepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LifeCoverageTypeController.class);

	@Override
	public List<LifeCoverageTypeMst> getAllLifeCoverageType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return lifecoveragetyperepository.findAll();
	}

	

	@Override
	public LifeCoverageTypeMst getLifeCoverageTypeById(long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LifeCoverageTypeMst> LifeCoverageTypeDb = this.lifecoveragetyperepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for gender By Id" + id);
		if (LifeCoverageTypeDb.isPresent()) {
			logger.info("LifeCoverageType is  found with id" + id);
			return LifeCoverageTypeDb.get();
		} else {
			throw new ResourceNotFoundException("LifeCoverageType not found with id:" + id);
		}

	}

	@Override
	public LifeCoverageTypeMst getLifeCoverageTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LifeCoverageTypeMst> lifecoveragetypeDb = this.lifecoveragetyperepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Gender By code" + code);
		if (lifecoveragetypeDb.isPresent()) {
			logger.info("LifeCoverageType is  found with code" + code);
			return lifecoveragetypeDb.get();
		} else {
			throw new ResourceNotFoundException("LifeCoverageType not found with code:" + code);
		}
	}
}

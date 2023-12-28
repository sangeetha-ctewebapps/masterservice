package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.FclTypeController;
import com.lic.epgs.mst.entity.FclTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.FclTypeRepository;

@Service
@Transactional
public class FclTypeServiceImpl implements FclTypeService {

	@Autowired
	private FclTypeRepository fclTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(FclTypeController.class);

	@Override
	public List<FclTypeMst> getAllFclType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get FclType list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return fclTypeRepository.findAll();
	}

	@Override
	public FclTypeMst getFclTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<FclTypeMst> fcltypeDb = this.fclTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for FclType By Id" + id);
		if (fcltypeDb.isPresent()) {
			logger.info("FclType is  found with id" + id);
			return fcltypeDb.get();
		} else {
			throw new ResourceNotFoundException("FclType not found with id:" + id);
		}
	}

	@Override
	//@Cacheable(value = "masterCache", key = "#code")
	public FclTypeMst getFclTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<FclTypeMst> fcltypeDb = this.fclTypeRepository.findByFclTypeCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for FclType By code" + code);
		if (fcltypeDb.isPresent()) {
			logger.info("FclType is  found with code" + code);
			return fcltypeDb.get();
		} else {
			throw new ResourceNotFoundException("FclType not found with code:" + code);
		}
	}

}

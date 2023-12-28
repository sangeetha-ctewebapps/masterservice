package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.RateTypeController;
import com.lic.epgs.mst.entity.RateTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RateTypeRepository;

@Service
@Transactional
public class RateTypeServiceImpl implements RateTypeService {

	@Autowired
	private RateTypeRepository rateTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RateTypeController.class);

	@Override
	public List<RateTypeMst> getAllRateType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get RateType list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return rateTypeRepository.findAll();
	}

	@Override
	public RateTypeMst getRateTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RateTypeMst> ratetypeDb = this.rateTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for RateType By Id" + id);
		if (ratetypeDb.isPresent()) {
			logger.info("RateType is  found with id" + id);
			return ratetypeDb.get();
		} else {
			throw new ResourceNotFoundException("RateType not found with id:" + id);
		}
	}

	@Override
	public RateTypeMst getRateTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RateTypeMst> ratetypeDb = this.rateTypeRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for RateType By code" + code);
		if (ratetypeDb.isPresent()) {
			logger.info("RateType is  found with code" + code);
			return ratetypeDb.get();
		} else {
			throw new ResourceNotFoundException("RateType not found with code:" + code);
		}
	}

}

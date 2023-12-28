package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.CalculationValueController;
import com.lic.epgs.mst.entity.CalculationValueMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CalculationValueRepository;

@Service
@Transactional
public class CalculationValueServiceImpl implements CalculationValueService {

	@Autowired
	private CalculationValueRepository calculationValueRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CalculationValueController.class);

	@Override
	public List<CalculationValueMst> getAllCalculationValue() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get CalculationValue list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return calculationValueRepository.findAll();
	}

	@Override
	public CalculationValueMst getCalculationValueById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CalculationValueMst> calculationvalueDb = this.calculationValueRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for CalculationValue By Id" + id);
		if (calculationvalueDb.isPresent()) {
			logger.info("CalculationValue is  found with id" + id);
			return calculationvalueDb.get();
		} else {
			throw new ResourceNotFoundException("CalculationValue not found with id:" + id);
		}
	}

	@Override
	public CalculationValueMst getCalculationValueByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CalculationValueMst> calculationvalueDb = this.calculationValueRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for CalculationValue By code" + code);
		if (calculationvalueDb.isPresent()) {
			logger.info("CalculationValue is  found with code" + code);
			return calculationvalueDb.get();
		} else {
			throw new ResourceNotFoundException("CalculationValue not found with code:" + code);
		}
	}

}

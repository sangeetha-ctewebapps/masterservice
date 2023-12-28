package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ValuationTypeController;
import com.lic.epgs.mst.entity.ValuationTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ValuationTypeRepository;

@Service
@Transactional
public class ValuationTypeServiceImpl implements ValuationTypeService {

	@Autowired
	ValuationTypeRepository valuationtyperepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ValuationTypeController.class);

	@Override
	public List<ValuationTypeMst> getAllValuationType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return valuationtyperepository.findAll();
	}

	@Override
	public ValuationTypeMst getValuationTypeById(long valuationId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ValuationTypeMst> valuationDb = this.valuationtyperepository.findById(valuationId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for valuation By Id" + valuationId);
		if (valuationDb.isPresent()) {
			logger.info("Valuation type is  found with id" + valuationId);
			return valuationDb.get();
		} else {
			throw new ResourceNotFoundException("Valuation type not found with id:" + valuationId);
		}

	}

	@Override
	public ValuationTypeMst getValuationTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ValuationTypeMst> valuationDb = this.valuationtyperepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Valuation type By code" + code);
		if (valuationDb.isPresent()) {
			logger.info("valuation type is  found with code" + code);
			return valuationDb.get();
		} else {
			throw new ResourceNotFoundException("valuation type not found with code:" + code);
		}
	}
}

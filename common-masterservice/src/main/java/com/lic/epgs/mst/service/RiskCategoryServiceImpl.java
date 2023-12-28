package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.RiskCategoryController;
import com.lic.epgs.mst.entity.RiskCategoryMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RiskCategoryRepository;

@Service
@Transactional
public class RiskCategoryServiceImpl implements RiskCategoryService {

	@Autowired
	private RiskCategoryRepository riskCategoryRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RiskCategoryController.class);

	@Override
	public List<RiskCategoryMaster> getAllRiskCategory() {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("i am in all risk category method");
		logger.info("i get risk categor list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return riskCategoryRepository.findAll();

	}

	@Override
	public RiskCategoryMaster getRiskCategoryById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RiskCategoryMaster> riskCategoryDb = this.riskCategoryRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for layer type By Id" + id);
		if (riskCategoryDb.isPresent()) {
			logger.info("risk category is  found with id" + id);
			return riskCategoryDb.get();
		} else {
			throw new ResourceNotFoundException("riskCategory not found with id:" + id);
		}
	}

	@Override
	public RiskCategoryMaster getRiskCategoryByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RiskCategoryMaster> RiskCategoryCodeDb = this.riskCategoryRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for risk category By code" + code);
		if (RiskCategoryCodeDb.isPresent()) {
			logger.info(" risk category is  found with code" + code);
			return RiskCategoryCodeDb.get();
		} else {
			throw new ResourceNotFoundException("risk category not found with code:" + code);
		}

	}

}

package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.CategoryForGstNonApplicabilityController;
import com.lic.epgs.mst.entity.CategoryForGstNonApplicabilityMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CategoryForGstNonApplicabilityRepository;

@Service
@Transactional
public class CategoryForGstNonApplicabilityServiceImpl implements CategoryForGstNonApplicabilityService {

	@Autowired
	private CategoryForGstNonApplicabilityRepository categoryForGstNonApplicabilityRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CategoryForGstNonApplicabilityController.class);

	@Override
	public List<CategoryForGstNonApplicabilityMst> getAllCategoryForGstNonApplicability() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get CategoryforGstNonApplicability list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return categoryForGstNonApplicabilityRepository.findAll();
	}

	@Override
	public CategoryForGstNonApplicabilityMst getCategoryForGstNonApplicabilityById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CategoryForGstNonApplicabilityMst> categoryforgstnonapplicabilityDb = this.categoryForGstNonApplicabilityRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for CategoryforGstNonApplicability By Id:  " + id);
		if (categoryforgstnonapplicabilityDb.isPresent()) {
			logger.info("CategoryforGstNonApplicability is  found with id:  " + id);
			return categoryforgstnonapplicabilityDb.get();
		} else {
			throw new ResourceNotFoundException("CategoryforGstNonApplicability not found with id: " + id);
		}
	}

	@Override
	public CategoryForGstNonApplicabilityMst getCategoryForGstNonApplicabilityByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CategoryForGstNonApplicabilityMst> categoryforgstnonapplicabilityDb = this.categoryForGstNonApplicabilityRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for CategoryForGstNonApplicability By code: " + code);
		if (categoryforgstnonapplicabilityDb.isPresent()) {
			logger.info("CategoryforGstNonApplicability is  found with code: " + code);
			return categoryforgstnonapplicabilityDb.get();
		} else {
			throw new ResourceNotFoundException("CategoryforGstNonApplicability not found with code:  " + code);
		}
	}

}

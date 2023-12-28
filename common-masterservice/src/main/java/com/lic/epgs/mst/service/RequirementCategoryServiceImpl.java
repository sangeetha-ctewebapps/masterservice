package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.RequirementCategoryController;
import com.lic.epgs.mst.entity.RequirementCategoryMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RequirementCategoryRepository;

@Service
@Transactional
public class RequirementCategoryServiceImpl implements RequirementCategoryService {
	@Autowired
	RequirementCategoryRepository requirementcategoryrepository;

	String className=this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RequirementCategoryController.class);
	@Override
	public List<RequirementCategoryMst> getAllRequirementCategory() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		return requirementcategoryrepository.findAll();
	}

	@Override
	public RequirementCategoryMst getRequirementCategoryById(long requirementcategoryId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RequirementCategoryMst> requirementDb = this.requirementcategoryrepository.findById(requirementcategoryId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for address By Id" + requirementcategoryId);
		if (requirementDb.isPresent()) {
			logger.info("RequirementCategory type is not found with id"+requirementcategoryId);
			return requirementDb.get();
		} else {
			throw new ResourceNotFoundException("RequirementCategory not found with id:" + requirementcategoryId);
		}

	}


	@Override
	public RequirementCategoryMst getRequirementCategoryByCode(String requirementcategorycode) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RequirementCategoryMst> requirementcategoryDb = this.requirementcategoryrepository.findByCode(requirementcategorycode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for requirementcategory By code" + requirementcategorycode);
		if (requirementcategoryDb.isPresent()) {
			logger.info("requirementcategory is  found with code" + requirementcategorycode);
			return requirementcategoryDb.get();
		} else {
			throw new ResourceNotFoundException("requirementcategory not found with code:" + requirementcategorycode);
		}
	}
}



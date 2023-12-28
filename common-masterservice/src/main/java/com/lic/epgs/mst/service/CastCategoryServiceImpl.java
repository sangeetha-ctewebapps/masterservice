package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.CastCategoryController;
import com.lic.epgs.mst.entity.CastCategoryMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CastCategoryRepository;

@Service
@Transactional
public class CastCategoryServiceImpl implements CastCategoryService {

	@Autowired
	private CastCategoryRepository castCategoryRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CastCategoryController.class);

	@Override
	public List<CastCategoryMst> getAllCastCategory() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get Cast Category list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return castCategoryRepository.findAll();
	}

	@Override
	public CastCategoryMst getCastCategoryById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CastCategoryMst> castcategoryDb = this.castCategoryRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for castcategory By Id: " + id);
		if (castcategoryDb.isPresent()) {
			logger.info("castcategory is  found with id: " + id);
			return castcategoryDb.get();
		} else {
			throw new ResourceNotFoundException("castcategory not found with id: " + id);
		}
	}

	@Override
	public CastCategoryMst getCastCategoryByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CastCategoryMst> castcategoryDb = this.castCategoryRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for cast category By code: " + code);
		if (castcategoryDb.isPresent()) {
			logger.info("castcategory is  found with code: " + code);
			return castcategoryDb.get();
		} else {
			throw new ResourceNotFoundException("castcategory not found with code: " + code);
		}
	}

}

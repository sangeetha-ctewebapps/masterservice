package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.SubVariantController;
import com.lic.epgs.mst.entity.SubVariantMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.SubVariantRepository;

@Service
@Transactional
public class SubVariantServiceImpl implements SubVariantService {

	@Autowired
	private SubVariantRepository subVariantRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(SubVariantController.class);

	@Override
	public List<SubVariantMst> getAllSubVariant() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get SubVariant list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return subVariantRepository.findAll();
	}

	@Override
	public SubVariantMst getSubVariantById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<SubVariantMst> subvariantDb = this.subVariantRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for SubVariant By Id" + id);
		if (subvariantDb.isPresent()) {
			logger.info("SubVariant is  found with id" + id);
			return subvariantDb.get();
		} else {
			throw new ResourceNotFoundException("SubVariant not found with id:" + id);
		}
	}

	@Override
	public SubVariantMst getSubVariantByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<SubVariantMst> subvariantDb = this.subVariantRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for SubVariant By code" + code);
		if (subvariantDb.isPresent()) {
			logger.info("SubVariant is  found with code" + code);
			return subvariantDb.get();
		} else {
			throw new ResourceNotFoundException("SubVariant not found with code:" + code);
		}
	}

}

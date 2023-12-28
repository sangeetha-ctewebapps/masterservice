package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.AnnuityReqTypeController;
import com.lic.epgs.mst.entity.AnnuityReqType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.AnnuityReqTypeRepo;

@Service
@Transactional
public class AnnuityReqTypeServiceImpl implements AnnuityReqTypeService {

	@Autowired
	AnnuityReqTypeRepo annuityRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(AnnuityReqTypeController.class);

	@Override
	//@Cacheable()
	public List<AnnuityReqType> getAllAnnuity() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return annuityRepository.findAll();
	}

	@Override
	//@Cacheable(value = "masterCache", key = "#annuityId")
	public AnnuityReqType getAnnuityById(long annuityId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<AnnuityReqType> annuityDb = this.annuityRepository.findById(annuityId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for annuity By Id" + annuityId);
		if (annuityDb.isPresent()) {
			logger.info("annuity type is found with id" + annuityId);
			return annuityDb.get();
		} else {
			throw new ResourceNotFoundException("annuity not found with id:" + annuityId);
		}
	}

	@Override
	public AnnuityReqType getAnnuityByCode(String annuityCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<AnnuityReqType> annuityDb = this.annuityRepository.findByAnnuityCode(annuityCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for annuity By code" + annuityCode);
		if (annuityDb.isPresent()) {
			logger.info("annuity type is not found with code" + annuityCode);
			return annuityDb.get();
		} else {

			throw new ResourceNotFoundException("annuity not found with code:" + annuityCode);
		}
	}
}

package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.PremiumAdjustmentTypeController;
import com.lic.epgs.mst.entity.PremiumAdjustmentTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.PremiumAdjustmentTypeRepository;

@Service
@Transactional
public class PremiumAdjustmentTypeServiceImpl implements PremiumAdjustmentTypeService {

	@Autowired
	private PremiumAdjustmentTypeRepository premiumAdjustmentTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(PremiumAdjustmentTypeController.class);

	@Override
	public List<PremiumAdjustmentTypeMaster> getAllPremium() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get premium type list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return premiumAdjustmentTypeRepository.findAll();

	}

	@Override
	public PremiumAdjustmentTypeMaster getPremiumById(long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<PremiumAdjustmentTypeMaster> premiumTypeDb = this.premiumAdjustmentTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for premium type By Id" + id);
		if (premiumTypeDb.isPresent()) {
			logger.info("premium type is  found with id" + id);
			return premiumTypeDb.get();
		} else {
			throw new ResourceNotFoundException("premium type not found with id:" + id);
		}

	}

	@Override
	public PremiumAdjustmentTypeMaster getpremiumByCode(String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<PremiumAdjustmentTypeMaster> premiumTypeCodeDb = this.premiumAdjustmentTypeRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for premium type By code" + code);
		if (premiumTypeCodeDb.isPresent()) {
			logger.info("premium type is  found with code" + code);
			return premiumTypeCodeDb.get();
		} else {
			throw new ResourceNotFoundException("premium type not found with code:" + code);
		}

	}

}

package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.AnnuityPayoutMethodController;
import com.lic.epgs.mst.entity.AnnuityPayoutMethodMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.AnnuityPayoutMethodRepository;

@Service
@Transactional
public class AnnuityPayoutMethodServiceImpl implements AnnuityPayoutMethodService {

	@Autowired
	private AnnuityPayoutMethodRepository annuityPayoutMethodRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(AnnuityPayoutMethodController.class);

	@Override
	public List<AnnuityPayoutMethodMst> getAllAnnuityPayoutMethod() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get AnnuityPayoutMethod list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return annuityPayoutMethodRepository.findAll();
	}

	@Override
	public AnnuityPayoutMethodMst getAnnuityPayoutMethodById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<AnnuityPayoutMethodMst> annuitypayoutmethodDb = this.annuityPayoutMethodRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for AnnuityPayoutMethod By Id: " + id);
		if (annuitypayoutmethodDb.isPresent()) {
			logger.info("AnnuityPayoutMethod is  found with id: " + id);
			return annuitypayoutmethodDb.get();
		} else {
			throw new ResourceNotFoundException("AnnuityPayoutMethod not found with id: " + id);
		}
	}

	@Override
	public AnnuityPayoutMethodMst getAnnuityPayoutMethodByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<AnnuityPayoutMethodMst> annuitypayoutmethodDb = this.annuityPayoutMethodRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started ");
		LoggingUtil.logInfo(className, methodName, "Search for AnnuityPayoutMethod By code: " + code);
		if (annuitypayoutmethodDb.isPresent()) {
			logger.info("AnnuityPayoutMethod is  found with code: " + code);
			return annuitypayoutmethodDb.get();
		} else {
			throw new ResourceNotFoundException("AnnuityPayoutMethod not found with code: " + code);
		}
	}

}

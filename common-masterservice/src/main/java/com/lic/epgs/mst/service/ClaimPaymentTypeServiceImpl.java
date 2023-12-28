package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.ClaimPaymentTypeController;
import com.lic.epgs.mst.entity.ClaimPaymentTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ClaimPaymentTypeRepository;

@Service
@Transactional
public class ClaimPaymentTypeServiceImpl implements ClaimPaymentTypeService {

	@Autowired
	private ClaimPaymentTypeRepository claimPaymentTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ClaimPaymentTypeController.class);

	@Override
	public List<ClaimPaymentTypeMst> getAllClaimPaymentType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get Claim Payment Type list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return claimPaymentTypeRepository.findAll();
	}

	@Override
	public ClaimPaymentTypeMst getClaimPaymentTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimPaymentTypeMst> claimpaymenttypeDb = this.claimPaymentTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for ClaimPaymentType By Id" + id);
		if (claimpaymenttypeDb.isPresent()) {
			logger.info("ClaimPaymentType is  found with id" + id);
			return claimpaymenttypeDb.get();
		} else {
			throw new ResourceNotFoundException("Claim Payment Type not found with id: " + id);
		}
	}

	@Override
	public ClaimPaymentTypeMst getClaimPaymentTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimPaymentTypeMst> claimpaymenttypeDb = this.claimPaymentTypeRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Claim Payment Type By code: " + code);
		if (claimpaymenttypeDb.isPresent()) {
			logger.info("Claim Payment Type is  found with code: " + code);
			return claimpaymenttypeDb.get();
		} else {
			throw new ResourceNotFoundException("Claim Payment Type not found with code:" + code);
		}
	}

}

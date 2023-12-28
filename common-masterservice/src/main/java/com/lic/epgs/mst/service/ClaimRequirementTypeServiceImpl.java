package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ClaimRequirementTypeController;
import com.lic.epgs.mst.entity.ClaimRequirementType;
import com.lic.epgs.mst.exceptionhandling.ClaimRequirementTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ClaimRequirementTypeRepo;

@Service
@Transactional
public class ClaimRequirementTypeServiceImpl implements ClaimRequirementTypeService {

	@Autowired
	ClaimRequirementTypeRepo claimRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ClaimRequirementTypeController.class);

	@Override
	public List<ClaimRequirementType> getAllClaim()
			throws ResourceNotFoundException, ClaimRequirementTypeServiceException {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return claimRepository.findAll();
	}

	@Override
	public ClaimRequirementType getClaimById(long claimId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimRequirementType> claimDb = this.claimRepository.findById(claimId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for claim requirement type By Id" + claimId);
		if (claimDb.isPresent()) {
			logger.info("claim requirement type is found with id" + claimId);
			return claimDb.get();
		} else {
			throw new ResourceNotFoundException("claim requiremrnt type not found with id:" + claimId);
		}
	}

	@Override
	public ClaimRequirementType getClaimByCode(String claimCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimRequirementType> claimDb = this.claimRepository.findByClaimCode(claimCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for claim requirement type By Code" + claimCode);
		if (claimDb.isPresent()) {
			logger.info("customer type is not found with code" + claimCode);
			return claimDb.get();
		} else {
			throw new ResourceNotFoundException("claim requirement type not found with code:" + claimCode);
		}
	}

}

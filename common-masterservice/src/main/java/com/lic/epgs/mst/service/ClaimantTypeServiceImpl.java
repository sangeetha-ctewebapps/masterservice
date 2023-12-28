package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lic.epgs.mst.controller.ClaimantTypeController;
import com.lic.epgs.mst.entity.ClaimantType;
import com.lic.epgs.mst.exceptionhandling.ClaimantTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ClaimantTypeRepo;

@Service
@Transactional
public class ClaimantTypeServiceImpl implements ClaimantTypeService {

	@Autowired
	private ClaimantTypeRepo claimantRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ClaimantTypeController.class);

	@Override
	public List<ClaimantType> getAllClaimant() throws ResourceNotFoundException, ClaimantTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return claimantRepository.findAll();
	}

	@Override
	//@Cacheable(value = "masterCache", key = "#claimantId")
	public ClaimantType getClaimantById(long claimantId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimantType> claimantDb = this.claimantRepository.findById(claimantId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Claimant Type By Id" + claimantId);
		if (claimantDb.isPresent()) {
			logger.info("Claimant Type is not found with id" + claimantId);
			return claimantDb.get();
		} else {
			throw new ResourceNotFoundException("Claimant Type is not found with id:" + claimantId);
		}
	}

	@Override
	public ClaimantType getClaimantByCode(String claimantCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimantType> claimantDb = this.claimantRepository.findByClaimantCode(claimantCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Claimant Type By Code" + claimantCode);
		if (claimantDb.isPresent()) {
			logger.info("Claimant Type is not found with code" + claimantCode);
			return claimantDb.get();
		} else {
			throw new ResourceNotFoundException("Claimant Type is not found with code:" + claimantCode);
		}
	}
}


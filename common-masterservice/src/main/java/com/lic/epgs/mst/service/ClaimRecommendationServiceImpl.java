package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ClaimRecommendationController;
import com.lic.epgs.mst.entity.ClaimRecommendation;
import com.lic.epgs.mst.exceptionhandling.ClaimRecommendationServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ClaimRecommendationRepo;

@Service
@Transactional
public class ClaimRecommendationServiceImpl implements ClaimRecommendationService {

	@Autowired
	private ClaimRecommendationRepo claimRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ClaimRecommendationController.class);

	@Override
	public List<ClaimRecommendation> getAllClaim()
			throws ResourceNotFoundException, ClaimRecommendationServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return claimRepository.findAll();
	}

	@Override
	public ClaimRecommendation getClaimById(long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimRecommendation> claimRecommDb = this.claimRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Claim Recommendation By Id" + id);
		if (claimRecommDb.isPresent()) {
			logger.info("Claim Recommendation is not found with id" + id);
			return claimRecommDb.get();
		} else {
			throw new ResourceNotFoundException("Claim Recommendation not found with id:" + id);
		}
	}

	@Override
	public ClaimRecommendation getClaimByCode(String claimRecommCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimRecommendation> claimRecommDb = this.claimRepository.findByClaimCode(claimRecommCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for coverage By Code" + claimRecommCode);
		if (claimRecommDb.isPresent()) {
			logger.info("Claim Recommendation is found with code" + claimRecommCode);
			return claimRecommDb.get();
		} else {
			throw new ResourceNotFoundException("Claim Recommendation is not found with code:" + claimRecommCode);
		}
	}

}
package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ReasonForClaimController;
import com.lic.epgs.mst.entity.ReasonForClaim;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ReasonForClaimRepository;

@Service
@Transactional
public class ReasonForClaimServiceImpl implements ReasonForClaimService {
	@Autowired
	ReasonForClaimRepository reasonforclaimrepository;

	String className=this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ReasonForClaimController.class);
	@Override
	public List<ReasonForClaim> getAllReasonForClaim() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		return reasonforclaimrepository.findAll();
	}

	@Override
	public ReasonForClaim createReasonForClaim(ReasonForClaim reasonforclaim) {
		// TODO Auto-generated method stub
		return reasonforclaimrepository.save(reasonforclaim);
	}

	@Override
	public ReasonForClaim getById(long reasonforclaimId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ReasonForClaim> reasonforclaimDb = this.reasonforclaimrepository.findById(reasonforclaimId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for reasonforclaim By Id" + reasonforclaimId);
		if (reasonforclaimDb.isPresent()) {
			logger.info("ReasonForClaim is not found with id"+reasonforclaimId);
			return reasonforclaimDb.get();
		} else {
			throw new ResourceNotFoundException("ReasonForClaim not found with id:" + reasonforclaimId);
		}

	}
	
	@Override
	public ReasonForClaim getReasonForClaimByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ReasonForClaim> reasonforclaimDb = this.reasonforclaimrepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for reasonforclaim By code" + code);
		if (reasonforclaimDb.isPresent()) {
			logger.info("reasonforclaim is  found with code" + code);
			return reasonforclaimDb.get();
		} else {
			throw new ResourceNotFoundException("reasonforclaim not found with code:" + code);
		}
	}
}


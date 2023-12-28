package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ClaimCancellationReasonController;
import com.lic.epgs.mst.entity.ClaimTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ClaimCancellationReasonRepository;

@Service
@Transactional
public class ClaimCancellationReasonServiceImpl implements ClaimCancellationReasonService {
	@Autowired
	ClaimCancellationReasonRepository claimcancellationreasonrepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ClaimCancellationReasonController.class);

	@Override
	public List<ClaimTypeMst> getAllClaimCancellationReason() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return claimcancellationreasonrepository.findAll();
	}

	@Override
	public ClaimTypeMst getClaimCancellationReasonById(long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimTypeMst> claimDb = this.claimcancellationreasonrepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for claim By Id" + id);
		if (claimDb.isPresent()) {
			logger.info("claim type is  found with id" + id);
			return claimDb.get();
		} else {
			throw new ResourceNotFoundException("claim not found with id:" + id);
		}
	}

	@Override
	public ClaimTypeMst getClaimCancellationReasonByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ClaimTypeMst> claimDb = this.claimcancellationreasonrepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for claim By code" + code);
		if (claimDb.isPresent()) {
			logger.info("claim is  found with code" + code);
			return claimDb.get();
		} else {
			throw new ResourceNotFoundException("claim not found with code:" + code);
		}
	}

}

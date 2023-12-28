package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lic.epgs.mst.controller.RenewalProcessingTypeController;
import com.lic.epgs.mst.entity.RenewalProcessingType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.RenewalProcessingTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RenewalProcessingTypeRepo;

@Service
@Transactional
public class RenewalProcessingTypeServiceImpl implements RenewalProcessingTypeService {

	@Autowired
	private RenewalProcessingTypeRepo renewalRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RenewalProcessingTypeController.class);

	@Override
	public List<RenewalProcessingType> getAllRenewal()
			throws ResourceNotFoundException, RenewalProcessingTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return renewalRepository.findAll();
	}

	@Override
	public RenewalProcessingType getRenewalById(long renewalId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RenewalProcessingType> renewalDb = this.renewalRepository.findById(renewalId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "search for Renewal Processing Type By Id" + renewalId);
		if (renewalDb.isPresent()) {
			logger.info("Renewal Processing Type is not found with id" + renewalId);
			return renewalDb.get();
		} else {
			throw new ResourceNotFoundException("Renewal Processing Type not found with id:" + renewalId);
		}
	}

	@Override
	public RenewalProcessingType getRenewalByCode(String renewalCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RenewalProcessingType> renewalDb = this.renewalRepository.findByRenewalCode(renewalCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Renewal Processing Type By Code" + renewalCode);
		if (renewalDb.isPresent()) {
			logger.info("Renewal Processing Type is not found with code" + renewalCode);
			return renewalDb.get();
		} else {
			throw new ResourceNotFoundException("Renewal Processing Type not found with code:" + renewalCode);
		}
	}
}

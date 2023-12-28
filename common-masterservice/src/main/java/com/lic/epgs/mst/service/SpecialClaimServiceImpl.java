package com.lic.epgs.mst.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.AddressTypeController;
import com.lic.epgs.mst.entity.SpecailClaimReasonEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.SpecailClaimRepository;



@Service
@Transactional
public class SpecialClaimServiceImpl implements SpecialClaimService{
	
	@Autowired
	SpecailClaimRepository specailClaimRepository;
	
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(AddressTypeController.class);
	
	@Override
	public List<SpecailClaimReasonEntity> getAllDetails() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return specailClaimRepository.findAll();
	}
		

}

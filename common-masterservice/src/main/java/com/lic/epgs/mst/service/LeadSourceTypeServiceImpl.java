package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.LeadSourceTypeController;
import com.lic.epgs.mst.entity.LeadSourceTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LeadSourceTypeRepository;

@Service
@Transactional
public class LeadSourceTypeServiceImpl implements LeadSourceTypeService {

	@Autowired
	private LeadSourceTypeRepository leadSourceTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LeadSourceTypeController.class);

	@Override
	public List<LeadSourceTypeMaster> getAllLeadSourceType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get lead source type  list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return leadSourceTypeRepository.findAll();

	}

	@Override
	public LeadSourceTypeMaster getLeadSourceTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LeadSourceTypeMaster> leadSourceTypeDb = this.leadSourceTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for lead source type By Id" + id);
		if (leadSourceTypeDb.isPresent()) {
			logger.info("lead source type is  found with id" + id);
			return leadSourceTypeDb.get();
		} else {
			throw new ResourceNotFoundException("lead source type  not found with id:" + id);
		}
	}

	@Override
	public LeadSourceTypeMaster getLeadSourceTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LeadSourceTypeMaster> leadSourceCodeDb = this.leadSourceTypeRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for lead source type By code" + code);
		if (leadSourceCodeDb.isPresent()) {
			logger.info("lead source type is  found with code" + code);
			return leadSourceCodeDb.get();
		} else {
			throw new ResourceNotFoundException("lead source type not found with code:" + code);
		}
	}

}

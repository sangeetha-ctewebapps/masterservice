package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.ReinsurerType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ReinsurerRepository;

@Transactional
@Service
public class ReinsurerServiceImpl implements ReinsurerService {
	@Autowired
	ReinsurerRepository reinsurerRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ReinsurerServiceImpl.class);

	/** Get All Reinsurer type */
	public List<ReinsurerType> getAllReunsurer() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		List<ReinsurerType> reinsurerList = reinsurerRepository.findAll();

		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "All List of Reinsurer");

		if (reinsurerList != null) {
			logger.info("fetched Reisurer Lists");
			return reinsurerList;
		} else {
			logger.info("Reunsurer  not found");
			throw new ResourceNotFoundException("Reunsurer not found ");
			// ResponseEntity.unprocessableEntity().body("failed to fetch Reisurer Lists ");
		}
	}

	/** Add/Create New Reinsurer type */
	
	  public ReinsurerType addReinsurerType(ReinsurerType reinsurerType) { String
	  methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	  
	  ReinsurerType reinsurer = reinsurerRepository.save(reinsurerType);
	  
	  LoggingUtil.logInfo(className, methodName, "Started");
	  LoggingUtil.logInfo(className, methodName, "save for Reinsurer By name" +
	  reinsurerType);
	  
	  if (reinsurer != null) { logger.info("Reunsurer type created" +
	  reinsurerType); return reinsurer; } else { throw new
	  ResourceNotFoundException("Reunsurer not found with name:"); } }
	 

	/** fetch an Reisurer by ID */
	  
	@Override
	public ReinsurerType searchReinsurerTypeById(Long reinsurerId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		Optional<ReinsurerType> reinsurer = reinsurerRepository.findById(reinsurerId);

		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "save for Reinsurer By id" + reinsurerId);

		if (reinsurer.isPresent()) {
			return reinsurer.get();
		} else {
			logger.error("Reunsurer not found with Id" + reinsurerId);
			throw new ResourceNotFoundException("Reunsurer not found with id:" + reinsurerId);
		}

	}

	/** fetch an Reisurer by Code */
	public ReinsurerType searchReinsurerTypeByCode(String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		LoggingUtil.logInfo(className, methodName, "Started");

		if (reinsurerRepository.getReinsurerByCode(code) != null) {
			LoggingUtil.logInfo(className, methodName, "get Reinsurer Code" + code);
			logger.info("Reunsurer found with code" + code);

			return reinsurerRepository.getReinsurerByCode(code);
		} else {
			logger.error("Reunsurer not found with Code" + code);
			throw new ResourceNotFoundException("Reunsurer not found with code:" + code);
			// ResponseEntity.unprocessableEntity().body("failed to fetch Reisurer with
			// specified code");
		}
	}



}

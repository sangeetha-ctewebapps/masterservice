package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.EndorsementSubType;
import com.lic.epgs.mst.exceptionhandling.EndorsementSubTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.EndorsementSubTypeService;

@RestController
@CrossOrigin("*")
public class EndorsementSubTypeController {
	@Autowired
	private EndorsementSubTypeService endorsementSubTypeService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(EndorsementSubTypeController.class);

	 @GetMapping("/endorsementSubType")
	public List<EndorsementSubType> getAllEndorsementSubType() throws ResourceNotFoundException, EndorsementSubTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<EndorsementSubType> endorsements = endorsementSubTypeService.getAllEndorsementSubType();
			if (endorsements.isEmpty()) {
				logger.debug("Getting All Endorsement Sub Type");
				throw new ResourceNotFoundException("Endorsement Sub Type is not found");
			}
			return endorsements;
		} catch (Exception ex) {
			throw new EndorsementSubTypeServiceException("Endorsement Sub type is not found");
		}
	}

	@GetMapping("/endorsementSubTypeById/{id}")
	public ResponseEntity<EndorsementSubType> getEndorsementSubTypeById(@PathVariable Long id) throws EndorsementSubTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new Endorsement Sub Type By Id");
			return ResponseEntity.ok().body(endorsementSubTypeService.getEndorsementSubTypeById(id));
		} catch (Exception e) {
			logger.error("Endorsement Sub type is not found with ID : Invalid Data", e);
			throw new EndorsementSubTypeServiceException("Endorsement Sub type is not found with ID" + id);
		}
	}

	@GetMapping("/endorsementSubTypeByCode/{code}")
	public ResponseEntity<EndorsementSubType> getEndorsementSubTypeByCode(@PathVariable String code)
			throws EndorsementSubTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new Endorsement Sub Type By code");
			return ResponseEntity.ok().body(endorsementSubTypeService.findByEndorsementSTCode(code));
		} catch (Exception e) {
			logger.error("Endorsement Sub type is not found with code : Invalid Data", e);
			throw new EndorsementSubTypeServiceException("Endorsement Sub type is not found with code" + code);
		}
	}
}

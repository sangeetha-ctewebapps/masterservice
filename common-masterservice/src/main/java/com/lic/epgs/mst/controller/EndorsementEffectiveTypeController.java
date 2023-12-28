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

import com.lic.epgs.mst.entity.EndorsementEffectiveType;
import com.lic.epgs.mst.exceptionhandling.EndorsementEffectiveTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.EndorsementEffectiveTypeService;

@RestController
@CrossOrigin("*")
public class EndorsementEffectiveTypeController {
	@Autowired
	private EndorsementEffectiveTypeService endorsementEffectiveTypeService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(EndorsementEffectiveTypeController.class);

	 @GetMapping("/endorsementEffectiveType")
	public List<EndorsementEffectiveType> getAllEndorsementEffectiveType()
			throws ResourceNotFoundException, EndorsementEffectiveTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<EndorsementEffectiveType> endorsements = endorsementEffectiveTypeService
					.getAllEndorsementEffectiveType();
			if (endorsements.isEmpty()) {
				logger.debug("Getting All Endorsement Effective Type");
				throw new ResourceNotFoundException("Endorsement Effective Type is not found");
			}
			return endorsements;
		} catch (Exception ex) {
			throw new EndorsementEffectiveTypeServiceException("Endorsement Effective type is not found");
		}
	}

	@GetMapping("/endorsementEffectiveTypeById/{id}")
	public ResponseEntity<EndorsementEffectiveType> getEndorsementEffectiveTypeById(@PathVariable Long id)
			throws EndorsementEffectiveTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new customer Type By Id");
			return ResponseEntity.ok().body(endorsementEffectiveTypeService.getEndorsementEffectiveTypeById(id));
		} catch (Exception e) {
			logger.error("Endorsement Effective type is not found with ID : Invalid Data", e);
			throw new EndorsementEffectiveTypeServiceException("Endorsement Effective type is not found with ID" + id);
		}
	}

	@GetMapping("/endorsementEffectiveTypeByCode/{code}")
	public ResponseEntity<EndorsementEffectiveType> getEndorsementEffectiveTypeByCode(@PathVariable String code)
			throws EndorsementEffectiveTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new customer Type By code");
			return ResponseEntity.ok().body(endorsementEffectiveTypeService.findByEndorsementETCode(code));
		} catch (Exception e) {
			logger.error("Endorsement Effective type is not found with code : Invalid Data", e);
			throw new EndorsementEffectiveTypeServiceException("Endorsement Effective type is not found with code" + code);
		}
	}
}

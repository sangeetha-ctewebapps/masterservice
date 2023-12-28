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

import com.lic.epgs.mst.entity.TypeOfEndorsement;
import com.lic.epgs.mst.exceptionhandling.TypeOfEndorsementServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.TypeOfEndorsementService;

@RestController
@CrossOrigin("*")
public class TypeOfEndorsementController {
	@Autowired
	private TypeOfEndorsementService typeOfEndorsementService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(TypeOfEndorsementController.class);

	 @GetMapping("/typeOfEndorsement")
	public List<TypeOfEndorsement> getAllTypeOfEndorsement() throws ResourceNotFoundException, TypeOfEndorsementServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<TypeOfEndorsement> coveragees = typeOfEndorsementService.getAllTypeOfEndorsement();
			if (coveragees.isEmpty()) {
				logger.debug("Getting All Coverage Type");
				throw new ResourceNotFoundException("Coverage Type is not found");
			}
			return coveragees;
		} catch (Exception ex) {
			throw new TypeOfEndorsementServiceException("Type Of Endorsement is not found");
		}
	}

	@GetMapping("/typeOfEndorsementById/{id}")
	public ResponseEntity<TypeOfEndorsement> getTypeOfEndorsementById(@PathVariable Long id) throws TypeOfEndorsementServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new Type Of Endorsement By Id");
			return ResponseEntity.ok().body(typeOfEndorsementService.getTypeOfEndorsementById(id));
		} catch (Exception e) {
			logger.error("Type Of Endorsement is not found with ID : Invalid Data", e);
			throw new TypeOfEndorsementServiceException("Type Of Endorsement is not found with ID" + id);
		}
	}

	@GetMapping("/typeOfEndorsementByCode/{code}")
	public ResponseEntity<TypeOfEndorsement> getTypeOfEndorsementByCode(@PathVariable String code)
			throws TypeOfEndorsementServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new Type Of Endorsement By code");
			return ResponseEntity.ok().body(typeOfEndorsementService.findByTypeOfEndorsementCode(code));
		} catch (Exception e) {
			logger.error("Type Of Endorsement is not found with code : Invalid Data", e);
			throw new TypeOfEndorsementServiceException("Type Of Endorsement is not found with code" + code);
		}
	}
}

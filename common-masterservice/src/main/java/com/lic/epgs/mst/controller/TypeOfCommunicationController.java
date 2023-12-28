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

import com.lic.epgs.mst.entity.TypeOfCommunicationMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfCommunicationServiceException;
import com.lic.epgs.mst.service.TypeOfCommunicationService;

@RestController
@CrossOrigin("*")
public class TypeOfCommunicationController {

	private static final Logger logger = LoggerFactory.getLogger(TypeOfCommunicationController.class);

	@Autowired
	private TypeOfCommunicationService typeOfCommunicationService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/typeCommunication")
	public List<TypeOfCommunicationMaster> getAllTypeCommunication()
			throws ResourceNotFoundException, TypeOfCommunicationServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TypeOfCommunicationMaster> typeCommunication = typeOfCommunicationService.getAllTypeCommunication();

			if (typeCommunication.isEmpty()) {
				logger.debug("inside  type communication controller get All type communication() method");
				logger.info("type communication list is empty ");
				throw new ResourceNotFoundException("type communication not found");
			}
			return typeCommunication;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TypeOfCommunicationServiceException("internal server error");
		}
	}

	@GetMapping("/typeCommunication/{id}")
	public ResponseEntity<TypeOfCommunicationMaster> getTypeCommunicationById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(typeOfCommunicationService.getTypeCommunicationById(id));

	}

	@GetMapping("/typeCommunicationByCode/{code}")
	public ResponseEntity<TypeOfCommunicationMaster> getTypeCommunicationByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(typeOfCommunicationService.getTypeCommunicationByCode(code));

	}

}

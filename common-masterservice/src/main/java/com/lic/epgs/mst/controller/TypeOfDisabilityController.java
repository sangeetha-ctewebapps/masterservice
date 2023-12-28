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

import com.lic.epgs.mst.entity.TypeOfDisability;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfDisabilityServiceException;
import com.lic.epgs.mst.service.TypeOfDisabilityService;

@RestController
@CrossOrigin("*")
public class TypeOfDisabilityController {

	@Autowired
	private TypeOfDisabilityService disabilityService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TypeOfDisabilityController.class);

	 @GetMapping("/disabilities")
	public List<TypeOfDisability> getAllDisabilitiesType()
			throws ResourceNotFoundException, TypeOfDisabilityServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TypeOfDisability> disability = disabilityService.getAllDisability();

			if (disability.isEmpty()) {
				logger.debug("inside Type Of Disability Controller getAllDisabilities() method");
				logger.info("Type Of Disability list is empty ");
				throw new ResourceNotFoundException("Type Of Disability is not found");
			}
			return disability;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TypeOfDisabilityServiceException("internal server error");
		}
	}

	@GetMapping("/disabilities/{id}")
	public ResponseEntity<TypeOfDisability> getDisabilityById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Type Of Disability search by Id");
		return ResponseEntity.ok().body(disabilityService.getDisabilityById(id));
	}

	@GetMapping("/disabilities/code/{code}")
	public ResponseEntity<TypeOfDisability> getDisabilitiesByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Type Of Disability search by Code");
		return ResponseEntity.ok().body(disabilityService.getDisabilityByCode(code));
	}

}

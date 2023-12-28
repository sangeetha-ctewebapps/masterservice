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

import com.lic.epgs.mst.entity.ExtractionParameterMaster;
import com.lic.epgs.mst.exceptionhandling.ExtractionParameterServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ExtractionParameterService;

@RestController
@CrossOrigin("*")
public class ExtractionParameterController {

	private static final Logger logger = LoggerFactory.getLogger(ExtractionParameterController.class);

	@Autowired
	private ExtractionParameterService extractionParameterService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/extractionParameter")
	public List<ExtractionParameterMaster> getAllExtractionParameter()
			throws ResourceNotFoundException, ExtractionParameterServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ExtractionParameterMaster> extractionParameter = extractionParameterService
					.getAllExtractionParameter();

			if (extractionParameter.isEmpty()) {
				logger.debug("inside  exatraction parameter controller getAll extraction parameter() method");
				logger.info("extraction parameter list is empty ");
				throw new ResourceNotFoundException("extraction parameter not found");
			}
			return extractionParameter;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ExtractionParameterServiceException("internal server error");
		}
	}

	@GetMapping("/extractionParameter/{id}")
	public ResponseEntity<ExtractionParameterMaster> getExtractionParameterById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(extractionParameterService.getExtractionParameterById(id));

	}

	@GetMapping("/extractionParameterByCode/{code}")
	public ResponseEntity<ExtractionParameterMaster> getExtractionParameterByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(extractionParameterService.getExtractionParameterByCode(code));

	}

}

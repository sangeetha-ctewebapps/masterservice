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

import com.lic.epgs.mst.entity.TermType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TermTypeServiceException;
import com.lic.epgs.mst.service.TermTypeService;

@RestController
@CrossOrigin("*")
public class TermTypeController {

	@Autowired
	private TermTypeService termService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TermTypeController.class);

	 @GetMapping("/terms")
	public List<TermType> getAllTermType() throws ResourceNotFoundException, TermTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TermType> terms = termService.getAllTerm();

			if (terms.isEmpty()) {
				logger.debug("inside Term Type Controller getAllTermType() method");
				logger.info("Term Type list is empty ");
				throw new ResourceNotFoundException("Term Type is not found");
			}
			return terms;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TermTypeServiceException("internal server error");
		}
	}

	@GetMapping("/terms/{id}")
	public ResponseEntity<TermType> getTermById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Term Type search by Id");
		return ResponseEntity.ok().body(termService.getTermById(id));
	}

	@GetMapping("/terms/code/{code}")
	public ResponseEntity<TermType> getTermByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Term Type search by Code");
		return ResponseEntity.ok().body(termService.getTermByCode(code));
	}

}
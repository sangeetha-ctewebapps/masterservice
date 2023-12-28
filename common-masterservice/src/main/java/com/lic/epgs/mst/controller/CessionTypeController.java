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

import com.lic.epgs.mst.entity.CessionType;
import com.lic.epgs.mst.exceptionhandling.CessionTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CessionTypeService;

@RestController
@CrossOrigin("*")
public class CessionTypeController {

	@Autowired
	private CessionTypeService cessionService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CessionTypeController.class);

	 @GetMapping("/cessions")
	public List<CessionType> getAllCession() throws ResourceNotFoundException, CessionTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CessionType> cession = cessionService.getAllCession();

			if (cession.isEmpty()) {
				logger.debug("inside Cession Type Controller getAllCession() method");
				logger.info("cession type list is empty ");
				throw new ResourceNotFoundException("cession type list is not found");
			}
			return cession;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new CessionTypeServiceException("internal server error");
		}
	}

	@GetMapping("/cessions/{id}")
	public ResponseEntity<CessionType> getCessionById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Cession Type search by id");
		return ResponseEntity.ok().body(cessionService.getCessionById(id));

	}

	@GetMapping("/cessions/code/{code}")
	public ResponseEntity<CessionType> getCessionByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("CessionType search by code");
		return ResponseEntity.ok().body(cessionService.getCessionByCode(code));
	}

}



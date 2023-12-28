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

import com.lic.epgs.mst.entity.ValuationTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.ValuationTypeServiceException;
import com.lic.epgs.mst.service.ValuationTypeService;

@RestController
@CrossOrigin("*")
public class ValuationTypeController {

	@Autowired
	private ValuationTypeService valuationtypeservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ValuationTypeController.class);

	 @GetMapping("/valuation")
	public List<ValuationTypeMst> getAllValuationType()
			throws ResourceNotFoundException, ValuationTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ValuationTypeMst> valuation = valuationtypeservice.getAllValuationType();

			if (valuation.isEmpty()) {
				logger.debug("inside valuationtypecontroller getAllValuationType() method");
				logger.info("valuation type list is empty ");
				throw new ResourceNotFoundException("valuation not found");
			}
			return valuation;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ValuationTypeServiceException("internal server error");
		}
	}

	@GetMapping("/valuation/{id}")
	public ResponseEntity<ValuationTypeMst> getValuationTypeById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(valuationtypeservice.getValuationTypeById(id));
	}

	@GetMapping("/valuationByCode/{code}")
	public ResponseEntity<ValuationTypeMst> getValuationTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(valuationtypeservice.getValuationTypeByCode(code));

	}
}

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

import com.lic.epgs.mst.entity.RateTypeMst;
import com.lic.epgs.mst.exceptionhandling.RateTypeException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.RateTypeService;

@RestController
@CrossOrigin("*")
public class RateTypeController {

	private static final Logger logger = LoggerFactory.getLogger(RateTypeController.class);

	@Autowired
	private RateTypeService ratetypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/RateType")
	public List<RateTypeMst> getAllRateType() throws ResourceNotFoundException, RateTypeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RateTypeMst> ratetype = ratetypeService.getAllRateType();

			if (ratetype.isEmpty()) {
				logger.debug("inside RateType controller getAllRateType() method");
				logger.info("RateType list is empty ");
				throw new ResourceNotFoundException("RateType not found");
			}
			return ratetype;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RateTypeException("internal server error");
		}
	}

	@GetMapping("/RateType/{id}")
	public ResponseEntity<RateTypeMst> getRateTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(ratetypeService.getRateTypeById(id));

	}

	@GetMapping("/RateTypeByCode/{code}")
	public ResponseEntity<RateTypeMst> getRateTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(ratetypeService.getRateTypeByCode(code));

	}

}

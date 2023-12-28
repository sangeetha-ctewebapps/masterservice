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

import com.lic.epgs.mst.entity.CalculationValueMst;
import com.lic.epgs.mst.exceptionhandling.CalculationValueException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CalculationValueService;

@RestController
@CrossOrigin("*")
public class CalculationValueController {

	private static final Logger logger = LoggerFactory.getLogger(CalculationValueController.class);

	@Autowired
	private CalculationValueService calculationvalueService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/CalculationValue")
	public List<CalculationValueMst> getAllCalculationValue() throws ResourceNotFoundException, CalculationValueException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CalculationValueMst> calculationvalue = calculationvalueService.getAllCalculationValue();

			if (calculationvalue.isEmpty()) {
				logger.debug("inside Calculation Value controller getAllCalculationValue() method");
				logger.info("CalculationValue list is empty ");
				throw new ResourceNotFoundException("CalculationValue not found");
			}
			return calculationvalue;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new CalculationValueException("internal server error");
		}
	}

	@GetMapping("/CalculationValue/{id}")
	public ResponseEntity<CalculationValueMst> getCalculationValueById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(calculationvalueService.getCalculationValueById(id));

	}

	@GetMapping("/CalculationValueByCode/{code}")
	public ResponseEntity<CalculationValueMst> getCalculationValueByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(calculationvalueService.getCalculationValueByCode(code));

	}

}

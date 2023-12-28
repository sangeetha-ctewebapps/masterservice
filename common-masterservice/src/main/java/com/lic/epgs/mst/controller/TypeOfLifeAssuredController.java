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

import com.lic.epgs.mst.entity.TypeOfLifeAssured;
import com.lic.epgs.mst.entity.TypeOfLoanMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfLifeAssuredServiceException;
import com.lic.epgs.mst.exceptionhandling.TypeOfLoanException;
import com.lic.epgs.mst.service.TypeOfLifeAssuredService;

@RestController
@CrossOrigin("*")
public class TypeOfLifeAssuredController {

	@Autowired
	private TypeOfLifeAssuredService typeService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TypeOfLifeAssuredController.class);

	 @GetMapping("/typeoflifes")
	public List<TypeOfLifeAssured> getAllTypeOfLife() throws ResourceNotFoundException, TypeOfLifeAssuredServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TypeOfLifeAssured> terms = typeService.getAllTypeOfLife();

			if (terms.isEmpty()) {
				logger.debug("inside Type Of Life Assured Controller getAllType() method");
				logger.info("Type Of Life Assured list is empty ");
				throw new ResourceNotFoundException("Type Of Life Assured is not found");
			}
			return terms;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TypeOfLifeAssuredServiceException("internal server error");
		}
	}

	@GetMapping("/typeoflifes/{id}")
	public ResponseEntity<TypeOfLifeAssured> getTypeById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Type Of Life Assured search by Id");
		return ResponseEntity.ok().body(typeService.getTypeById(id));
	}

	@GetMapping("/typeoflifes/code/{code}")
	public ResponseEntity<TypeOfLifeAssured> getTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Type Of Life Assured search by Code");
		return ResponseEntity.ok().body(typeService.getTypeByCode(code));
	}

}

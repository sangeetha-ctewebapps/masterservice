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

import com.lic.epgs.mst.entity.TypeOfLoanMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfLoanException;
import com.lic.epgs.mst.service.TypeOfLoanService;

@RestController
@CrossOrigin("*")
public class TypeOfLoanController {

	@Autowired
	private TypeOfLoanService typeofloanservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(TypeOfLoanController.class);

	 @GetMapping("/TypeOfLoan")
	public List<TypeOfLoanMst> getAllTypeOfLoan() throws ResourceNotFoundException, TypeOfLoanException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TypeOfLoanMst> typeofloan = typeofloanservice.getAllTypeOfLoan();

			if (typeofloan.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllTypeOfLoan() method");
				logger.info("typeofloan list is empty ");
				throw new ResourceNotFoundException("typeofloan not found");
			}
			return typeofloan;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TypeOfLoanException("internal server error");
		}
	}

	@GetMapping("/TypeOfLoan/{id}")
	public ResponseEntity<TypeOfLoanMst> getTypeOfLoanById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(typeofloanservice.getTypeOfLoanById(id));
	}

	@GetMapping("/TypeOfLoanByCode/{code}")
	public ResponseEntity<TypeOfLoanMst> getTypeOfLoanByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(typeofloanservice.getTypeOfLoanByCode(code));

	}
}

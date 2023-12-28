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

import com.lic.epgs.mst.entity.LoanProvider;
import com.lic.epgs.mst.exceptionhandling.LoanProviderServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LoanProviderService;

@RestController
@CrossOrigin("*")
public class LoanProviderController {

	@Autowired
	private LoanProviderService loanService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LoanProviderController.class);

	  @GetMapping("/loan")
	public List<LoanProvider> getAllLoanProvider() throws ResourceNotFoundException, LoanProviderServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LoanProvider> loan = loanService.getAllLoanProvider();

			if (loan.isEmpty()) {
				logger.debug("inside loanprovider controller getAllLoanProvider() method");
				logger.info("loanprovider list is empty ");
				throw new ResourceNotFoundException("loanprovider not found");
			}
			return loan;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LoanProviderServiceException("internal server error");
		}
	}

	@GetMapping("/loan/{id}")
	public ResponseEntity<LoanProvider> getLoanProviderById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("LoanProvider search by id");
		return ResponseEntity.ok().body(loanService.getLoanProviderById(id));

	}

	@GetMapping("/loan/code/{code}")
	public ResponseEntity<LoanProvider> getLoanProviderByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("LoanProvider search by code");
		return ResponseEntity.ok().body(loanService.getLoanProviderByCode(code));

	}

}

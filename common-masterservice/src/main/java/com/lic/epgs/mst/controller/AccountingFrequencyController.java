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

import com.lic.epgs.mst.entity.AccountingFrequencyMaster;
import com.lic.epgs.mst.exceptionhandling.AccountingFrequencyServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.AccountingFrequencyService;

@RestController
@CrossOrigin("*")
public class AccountingFrequencyController {

	 private static final Logger logger = LoggerFactory.getLogger(AccountingFrequencyController.class);

	@Autowired
	private AccountingFrequencyService accountingFrequencyService;

	String className = this.getClass().getSimpleName();

	@GetMapping("/accountingfrequency")
	public List<AccountingFrequencyMaster> getAllAccountingFrequency()
			throws ResourceNotFoundException, AccountingFrequencyServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<AccountingFrequencyMaster> accountingFrequency = accountingFrequencyService
					.getAllAccountingFrequency();

			if (accountingFrequency.isEmpty()) {
				logger.debug("inside accounting Frequency controller getAllAccountFrequency() method");
				logger.info("accounting Frequency list is empty ");
				throw new ResourceNotFoundException("accountingFrequency not found");
			}
			return accountingFrequency;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new AccountingFrequencyServiceException("internal server error");
		}
	}

	@GetMapping("/accountingfrequency/{id}")
	public ResponseEntity<AccountingFrequencyMaster> getAccountingFrequencyById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(accountingFrequencyService.getAccountingFrequencyById(id));

	}

	@GetMapping("/accountingfrequency/accountingfrequencyCode/{code}")
	public ResponseEntity<AccountingFrequencyMaster> getAccountingFrequencyByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(accountingFrequencyService.getAccountingFrequencyByCode(code));

	}

}

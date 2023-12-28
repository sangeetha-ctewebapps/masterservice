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

import com.lic.epgs.mst.entity.FundNameMaster;
import com.lic.epgs.mst.exceptionhandling.FundNameServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.FundNameService;

@RestController
@CrossOrigin("*")
public class FundNameController {

	private static final Logger logger = LoggerFactory.getLogger(FundNameController.class);

	@Autowired
	private FundNameService fundNameService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/fundName")
	public List<FundNameMaster> getAllFundName() throws ResourceNotFoundException, FundNameServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<FundNameMaster> fundName = fundNameService.getAllFundName();

			if (fundName.isEmpty()) {
				logger.debug("inside fund name controller getAll fund name() method");
				logger.info("fund name list is empty ");
				throw new ResourceNotFoundException("fund name not found");
			}
			return fundName;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new FundNameServiceException("internal server error");
		}
	}

	@GetMapping("/fundName/{id}")
	public ResponseEntity<FundNameMaster> getFundNameById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(fundNameService.getFundNameById(id));

	}

	@GetMapping("/fundNameByCode/{code}")
	public ResponseEntity<FundNameMaster> getFundNameByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(fundNameService.getFundNameByCode(code));

	}

}

package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.BulkTypeMst;
import com.lic.epgs.mst.exceptionhandling.BulkWithdrawalTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.BulkWithdrawalTypeService;

@RestController
public class BulkWithdrawalTypeController {

	private static final Logger logger = LoggerFactory.getLogger(BulkWithdrawalTypeController.class);

	@Autowired
	private BulkWithdrawalTypeService bulkwithdrawaltypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/Bulk")
	public List<BulkTypeMst> getAllBulkWithdrawalType() throws ResourceNotFoundException, BulkWithdrawalTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<BulkTypeMst> bulkwithdrawaltype = bulkwithdrawaltypeService.getAllBulkWithdrawalType();

			if (bulkwithdrawaltype.isEmpty()) {
				logger.debug("inside Bulk Withdrawal Type controller getAllBulkWithdrawalType() method");
				logger.info("BulkWithdrawalType list is empty ");
				throw new ResourceNotFoundException("BulkWithdrawalType not found");
			}
			return bulkwithdrawaltype;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new BulkWithdrawalTypeServiceException("internal server error");
		}
	}

	@GetMapping("/Bulk/{id}")
	public ResponseEntity<BulkTypeMst> getBulkWithdrawalTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(bulkwithdrawaltypeService.getBulkWithdrawalTypeById(id));

	}

	@GetMapping("/BulkByCode/{code}")
	public ResponseEntity<BulkTypeMst> getBulkWithdrawalTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(bulkwithdrawaltypeService.getBulkWithdrawalTypeByCode(code));

	}

}

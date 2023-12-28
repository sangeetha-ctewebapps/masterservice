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

import com.lic.epgs.mst.entity.LockPeriodWhileInServiceMst;
import com.lic.epgs.mst.exceptionhandling.LockPeriodWhileInServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LockPeriodWhileInServiceService;

@RestController
@CrossOrigin("*")
public class LockPeriodWhileInServiceController {

	@Autowired
	private LockPeriodWhileInServiceService lockperiodwhileinserviceservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(LockPeriodWhileInServiceController.class);

	 @GetMapping("/LockPeriodWhileInService")
	public List<LockPeriodWhileInServiceMst> getAllLockPeriodWhileInService() throws ResourceNotFoundException, LockPeriodWhileInServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LockPeriodWhileInServiceMst> lockperiodwhileinservice = lockperiodwhileinserviceservice.getAllLockPeriodWhileInService();

			if (lockperiodwhileinservice.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllLockPeriodWhileInService() method");
				logger.info("LockPeriodWhileInService list is empty ");
				throw new ResourceNotFoundException("LockPeriodWhileInService not found");
			}
			return lockperiodwhileinservice;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LockPeriodWhileInServiceException("internal server error");
		}
	}

	@GetMapping("/LockPeriodWhileInService/{id}")
	public ResponseEntity<LockPeriodWhileInServiceMst> getLockPeriodWhileInServiceById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(lockperiodwhileinserviceservice.getLockPeriodWhileInServiceById(id));
	}

	@GetMapping("/LockPeriodWhileInServiceByCode/{code}")
	public ResponseEntity<LockPeriodWhileInServiceMst> getLockPeriodWhileInServiceByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(lockperiodwhileinserviceservice.getLockPeriodWhileInServiceByCode(code));

	}
}

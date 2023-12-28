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

import com.lic.epgs.mst.entity.IssuedByMst;
import com.lic.epgs.mst.exceptionhandling.IssuedByException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.IssuedByService;

@RestController
@CrossOrigin("*")
public class IssuedByController {

	@Autowired
	private IssuedByService issuedbyservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(IssuedByController.class);

	 @GetMapping("/IssuedBy")
	public List<IssuedByMst> getAllIssuedBy() throws ResourceNotFoundException, IssuedByException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<IssuedByMst> issuedby = issuedbyservice.getAllIssuedBy();

			if (issuedby.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllIssuedBy() method");
				logger.info("issuedby list is empty ");
				throw new ResourceNotFoundException("issuedby not found");
			}
			return issuedby;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new IssuedByException("internal server error");
		}
	}

	@GetMapping("/IssuedBy/{id}")
	public ResponseEntity<IssuedByMst> getIssuedByById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(issuedbyservice.getIssuedByById(id));
	}

	@GetMapping("/IssuedByByCode/{code}")
	public ResponseEntity<IssuedByMst> getIssuedByByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(issuedbyservice.getIssuedByByCode(code));

	}
}

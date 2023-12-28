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

import com.lic.epgs.mst.entity.TopUpValueMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TopUpValueException;
import com.lic.epgs.mst.service.TopUpValueService;

@RestController
@CrossOrigin("*")
public class TopUpValueController {

	@Autowired
	private TopUpValueService topupvalueservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(TopUpValueController.class);

	 @GetMapping("/TopUpValue")
	public List<TopUpValueMst> getAllTopUpValue() throws ResourceNotFoundException, TopUpValueException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TopUpValueMst> topupvalue = topupvalueservice.getAllTopUpValue();

			if (topupvalue.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllTopUpValue() method");
				logger.info("TopUpValue list is empty ");
				throw new ResourceNotFoundException("TopUpValue not found");
			}
			return topupvalue;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TopUpValueException("internal server error");
		}
	}

	@GetMapping("/TopUpValue/{id}")
	public ResponseEntity<TopUpValueMst> getTopUpValueById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(topupvalueservice.getTopUpValueById(id));
	}

	@GetMapping("/TopUpValueByCode/{code}")
	public ResponseEntity<TopUpValueMst> getTopUpValueByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(topupvalueservice.getTopUpValueByCode(code));

	}
}

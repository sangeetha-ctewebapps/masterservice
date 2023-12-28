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

import com.lic.epgs.mst.entity.UnderwritingDecisionMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.UnderwritingDecisionException;
import com.lic.epgs.mst.service.UnderwritingDecisionService;

@RestController
@CrossOrigin("*")
public class UnderwritingDecisionController {

	@Autowired
	private UnderwritingDecisionService underwritingdecisionservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(UnderwritingDecisionController.class);

	 @GetMapping("/UnderwritingDecision")
	public List<UnderwritingDecisionMst> getAllUnderwritingDecision()
			throws ResourceNotFoundException, UnderwritingDecisionException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<UnderwritingDecisionMst> underwritingdecision = underwritingdecisionservice.getAllUnderwritingDecision();
			if (underwritingdecision.isEmpty()) {
				logger.debug("inside UnderwritingDecisioncontroller getAllUnderwritingDecision() method");
				logger.info("Underwriting Decision  list is empty ");
				throw new ResourceNotFoundException("Underwriting Decision not found");
			} 
			return underwritingdecision;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new UnderwritingDecisionException("internal server error");
		}
	}

	@GetMapping("/UnderwritingDecision/{id}")
	public ResponseEntity<UnderwritingDecisionMst> getUnderwritingDecisionById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(underwritingdecisionservice.getUnderwritingDecisionById(id));

	}

	@GetMapping("/UnderwritingDecisionByCode/{code}")
	public ResponseEntity<UnderwritingDecisionMst> getUnderwritingDecisionByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(underwritingdecisionservice.getUnderwritingDecisionByCode(code));

	}
}

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

import com.lic.epgs.mst.entity.RecoveryInstruction;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.RecoveryInstructionServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.RecoveryInstructionService;

@RestController
@CrossOrigin("*")
public class RecoveryInstructionController {

	@Autowired
	private RecoveryInstructionService recoveryService;

	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(RecoveryInstructionController.class);

	 @GetMapping("/recoveries")
	public List<RecoveryInstruction> getAllRecoveryInstruction()
			throws ResourceNotFoundException, RecoveryInstructionServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RecoveryInstruction> recovery = recoveryService.getAllRecovery();

			if (recovery.isEmpty()) {
				logger.debug("inside Recovery Instruction Controller getAllRecovery() method");
				logger.info("Recovery Instruction list is empty ");
				throw new ResourceNotFoundException("Recovery Instruction is not found");
			}
			return recovery;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RecoveryInstructionServiceException("internal server error");
		}
	}

	@GetMapping("/recoveries/{id}")
	public ResponseEntity<RecoveryInstruction> getRecoveryById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Recovery Instruction search by id");
		return ResponseEntity.ok().body(recoveryService.getRecoveryById(id));
	}

	@GetMapping("/recoveries/code/{code}")
	public ResponseEntity<RecoveryInstruction> getRecoveryByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Recovery Instruction search by Code");
		return ResponseEntity.ok().body(recoveryService.getRecoveryByCode(code)); 
	}
}


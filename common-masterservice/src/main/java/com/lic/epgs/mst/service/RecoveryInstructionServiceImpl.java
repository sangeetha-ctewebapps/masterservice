package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.RecoveryInstructionController;
import com.lic.epgs.mst.entity.RecoveryInstruction;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RecoveryInstructionRepo;

@Service
@Transactional
public class RecoveryInstructionServiceImpl implements RecoveryInstructionService {

	@Autowired
	RecoveryInstructionRepo recoveryRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RecoveryInstructionController.class);

	@Override
	public List<RecoveryInstruction> getAllRecovery() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return recoveryRepository.findAll();
	}

	@Override
	public RecoveryInstruction getRecoveryById(long recoveryId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RecoveryInstruction> recoveryDb = this.recoveryRepository.findById(recoveryId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Recovery Instruction By Id" + recoveryId);
		if (recoveryDb.isPresent()) {
			logger.info("Recovery Instruction is found with id" + recoveryId);
			return recoveryDb.get();
		} else {
			throw new ResourceNotFoundException("Recovery Instruction is not found with id:" + recoveryId);
		}
	}

	@Override
	public RecoveryInstruction getRecoveryByCode(String recoveryCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RecoveryInstruction> recoveryDb = this.recoveryRepository.findByRecoveryCode(recoveryCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Recovery Instruction By Id" + recoveryCode);
		if (recoveryDb.isPresent()) {
			logger.info("Recovery Instruction is found with code" + recoveryCode);
			return recoveryDb.get();
		} else {
			throw new ResourceNotFoundException("Recovery Instruction is not found with code:" + recoveryCode);
		}
	}
}



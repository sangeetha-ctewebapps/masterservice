package com.lic.epgs.mst.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.RecoveryInstruction;
import com.lic.epgs.mst.exceptionhandling.RecoveryInstructionServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

@Service
public interface RecoveryInstructionService {
	List<RecoveryInstruction> getAllRecovery() throws ResourceNotFoundException, RecoveryInstructionServiceException;

	public RecoveryInstruction getRecoveryById(long id);

	public RecoveryInstruction getRecoveryByCode(String code);

}

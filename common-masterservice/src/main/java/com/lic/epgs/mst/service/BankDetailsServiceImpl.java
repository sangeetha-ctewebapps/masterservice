
package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.AddressTypeController;
import com.lic.epgs.mst.entity.BankDetails;
import com.lic.epgs.mst.exceptionhandling.BankDetailsServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.BankDetailsRepository;

@Service

@Transactional

public class BankDetailsServiceImpl implements BankDetailsService {

	@Autowired
	BankDetailsRepository bankDetailsRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(AddressTypeController.class);

	@Override
	public List<BankDetails> getAllBankDetails() throws ResourceNotFoundException, BankDetailsServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return bankDetailsRepository.findAll();
	}
	
	@Override
	public BankDetails getBankDetailsById(long bankId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<BankDetails> bankDb = this.bankDetailsRepository.findById(bankId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Bank Details By Id" + bankId);
		if (bankDb.isPresent()) {
			logger.info("Bank Details is not found with id" + bankId);
			return bankDb.get();
		} else {
			throw new ResourceNotFoundException("Bank Details found with id:" + bankId);
		}
	}

	
}

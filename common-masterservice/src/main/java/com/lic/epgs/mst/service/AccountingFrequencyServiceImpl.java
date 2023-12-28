package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.AccountingFrequencyController;
import com.lic.epgs.mst.entity.AccountingFrequencyMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.AccountingFrequencyRepository;

@Service
@Transactional
public class AccountingFrequencyServiceImpl implements AccountingFrequencyService {

	@Autowired
	private AccountingFrequencyRepository accountingFrequencyRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(AccountingFrequencyController.class);

	@Override
	public List<AccountingFrequencyMaster> getAllAccountingFrequency() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get accounting frequency list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return accountingFrequencyRepository.findAll();
	}

	@Override
	public AccountingFrequencyMaster getAccountingFrequencyById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<AccountingFrequencyMaster> accountingDb = this.accountingFrequencyRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for accounting frequency By Id" + id);
		if (accountingDb.isPresent()) {
			logger.info("accounting frequency is  found with id" + id);
			return accountingDb.get();
		} else {
			throw new ResourceNotFoundException("accounting frequency not found with id:" + id);
		}
	}

	@Override
	public AccountingFrequencyMaster getAccountingFrequencyByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<AccountingFrequencyMaster> accountingCodeDb = this.accountingFrequencyRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for accounting frequency By code" + code);
		if (accountingCodeDb.isPresent()) {
			logger.info("accounting frequency is  found with code" + code);
			return accountingCodeDb.get();
		} else {
			throw new ResourceNotFoundException("accounting frequency not found with code:" + code);
		}
	}

}

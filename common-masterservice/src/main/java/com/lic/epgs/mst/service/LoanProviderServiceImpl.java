package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.LoanProviderController;
import com.lic.epgs.mst.entity.LoanProvider;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LoanProviderRepo;

@Service
@Transactional
public class LoanProviderServiceImpl implements LoanProviderService {

	@Autowired
	LoanProviderRepo loanRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LoanProviderController.class);

	@Override
	public List<LoanProvider> getAllLoanProvider() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return loanRepository.findAll();
	}

	@Override
	public LoanProvider getLoanProviderById(long loanId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LoanProvider> loanDb = this.loanRepository.findById(loanId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for loan By Id" + loanId);
		if (loanDb.isPresent()) {
			logger.info("loan type is not found with id" + loanId);
			return loanDb.get();
		} else {
			throw new ResourceNotFoundException("loan not found with id:" + loanId);
		}
	}

	@Override
	public LoanProvider getLoanProviderByCode(String loanCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LoanProvider> loanDb = this.loanRepository.findByLoanCode(loanCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for loan By Id" + loanCode);
		if (loanDb.isPresent()) {
			logger.info("loan type is not found with code" + loanCode);
			return loanDb.get();
		} else {
			throw new ResourceNotFoundException("loan not found with code:" + loanCode);
		}
	}

}

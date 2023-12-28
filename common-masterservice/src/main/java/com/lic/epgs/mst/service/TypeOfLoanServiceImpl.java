package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.TypeOfLoanController;
import com.lic.epgs.mst.entity.TypeOfLoanMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TypeOfLoanRepository;

@Service
@Transactional
public class TypeOfLoanServiceImpl implements TypeOfLoanService {

	@Autowired
	TypeOfLoanRepository typeofloanrepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TypeOfLoanController.class);

	@Override
	public List<TypeOfLoanMst> getAllTypeOfLoan() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return typeofloanrepository.findAll();
	}

	@Override
	public TypeOfLoanMst getTypeOfLoanById(long typeofloanId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfLoanMst> TypeOfLoanDb = this.typeofloanrepository.findById(typeofloanId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for TypeOfLoan By Id" + typeofloanId);
		if (TypeOfLoanDb.isPresent()) {
			logger.info("TypeOfLoan is  found with id" + typeofloanId);
			return TypeOfLoanDb.get();
		} else {
			throw new ResourceNotFoundException("TypeOfLoan not found with id:" + typeofloanId);
		}

	}

	@Override
	public TypeOfLoanMst getTypeOfLoanByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfLoanMst> typeofloanDb = this.typeofloanrepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Gender By code" + code);
		if (typeofloanDb.isPresent()) {
			logger.info("TypeOfLoan is  found with code" + code);
			return typeofloanDb.get();
		} else {
			throw new ResourceNotFoundException("TypeOfLoan not found with code:" + code);
		}
	}
}

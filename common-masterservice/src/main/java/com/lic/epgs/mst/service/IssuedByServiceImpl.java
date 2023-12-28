package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.IssuedByController;
import com.lic.epgs.mst.entity.IssuedByMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.IssuedByRepository;

@Service
@Transactional
public class IssuedByServiceImpl implements IssuedByService {

	@Autowired
	IssuedByRepository issuedbyrepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(IssuedByController.class);

	@Override
	public List<IssuedByMst> getAllIssuedBy() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return issuedbyrepository.findAll();
	}

	@Override
	public IssuedByMst getIssuedByById(long issuedbyId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<IssuedByMst> IssuedByDb = this.issuedbyrepository.findById(issuedbyId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for issuedby By Id" + issuedbyId);
		if (IssuedByDb.isPresent()) {
			logger.info("IssuedBy is  found with id" + issuedbyId);
			return IssuedByDb.get();
		} else {
			throw new ResourceNotFoundException("IssuedBy not found with id:" + issuedbyId);
		}

	}

	@Override
	public IssuedByMst getIssuedByByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<IssuedByMst> issuedbyDb = this.issuedbyrepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for IssuedBy By code" + code);
		if (issuedbyDb.isPresent()) {
			logger.info("IssuedBy is  found with code" + code);
			return issuedbyDb.get();
		} else {
			throw new ResourceNotFoundException("IssuedBy not found with code:" + code);
		}
	}
}

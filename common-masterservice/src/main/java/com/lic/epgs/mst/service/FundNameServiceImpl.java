package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.FundNameController;
import com.lic.epgs.mst.entity.FundNameMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.FundNameRepository;

@Service
@Transactional
public class FundNameServiceImpl implements FundNameService {

	@Autowired
	private FundNameRepository fundNameRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(FundNameController.class);

	@Override
	public List<FundNameMaster> getAllFundName() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get fund name list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return fundNameRepository.findAll();
	}

	@Override
	public FundNameMaster getFundNameById(long id) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<FundNameMaster> fundNameDb = this.fundNameRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, " fund name By Id" + id);
		if (fundNameDb.isPresent()) {
			logger.info(" fund name is  found with id" + id);
			return fundNameDb.get();
		} else {
			throw new ResourceNotFoundException("fund name not found with id:" + id);
		}
	}

	@Override
	public FundNameMaster getFundNameByCode(String code) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<FundNameMaster> fundNmaeCodeDb = this.fundNameRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for fund name By code" + code);
		if (fundNmaeCodeDb.isPresent()) {
			logger.info("fund name is  found with code" + code);
			return fundNmaeCodeDb.get();
		} else {
			throw new ResourceNotFoundException("fund name not found with code:" + code);
		}

	}

}

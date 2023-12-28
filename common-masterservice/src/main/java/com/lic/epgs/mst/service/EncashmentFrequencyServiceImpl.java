package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.EncashmentFrequencyController;
import com.lic.epgs.mst.entity.EncashmentFrequencyMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.EncashmentFrequencyRepository;

@Service
@Transactional
public class EncashmentFrequencyServiceImpl implements EncashmentFrequencyService {
	@Autowired
	private EncashmentFrequencyRepository encashmentFrequencyRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(EncashmentFrequencyController.class);

	@Override
	public List<EncashmentFrequencyMaster> getAllEncashmentFrequency() {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get encashment frequency list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return encashmentFrequencyRepository.findAll();
	}

	@Override
	public EncashmentFrequencyMaster getEncashmentFrequencyById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<EncashmentFrequencyMaster> encashmentDb = this.encashmentFrequencyRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for encashment frequency By Id" + id);
		if (encashmentDb.isPresent()) {
			logger.info(" encashment frequency is  found with id" + id);
			return encashmentDb.get();
		} else {
			throw new ResourceNotFoundException(" encashment frequency not found with id:" + id);
		}
	}

	@Override
	public EncashmentFrequencyMaster getEncashmentFrequencyByCode(String code) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<EncashmentFrequencyMaster> encashmentCodeDb = this.encashmentFrequencyRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for encashment frequency By code" + code);
		if (encashmentCodeDb.isPresent()) {
			logger.info("encashment frequency is  found with code" + code);
			return encashmentCodeDb.get();
		} else {
			throw new ResourceNotFoundException(" encashment frequency not found with code:" + code);
		}
	}

}

package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.TopUpValueController;
import com.lic.epgs.mst.entity.TopUpValueMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TopUpValueRepository;

@Service
@Transactional
public class TopUpValueServiceImpl implements TopUpValueService {

	@Autowired
	TopUpValueRepository topupvaluerepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TopUpValueController.class);

	@Override
	public List<TopUpValueMst> getAllTopUpValue() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return topupvaluerepository.findAll();
	}

	@Override
	public TopUpValueMst getTopUpValueById(long topupvalueId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TopUpValueMst> TopUpValueDb = this.topupvaluerepository.findById(topupvalueId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for TopUpValue By Id" + topupvalueId);
		if (TopUpValueDb.isPresent()) {
			logger.info("TopUpValue is  found with id" + topupvalueId);
			return TopUpValueDb.get();
		} else {
			throw new ResourceNotFoundException("TopUpValue not found with id:" + topupvalueId);
		}

	}

	@Override
	public TopUpValueMst getTopUpValueByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TopUpValueMst> topupvalueDb = this.topupvaluerepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Gender By code" + code);
		if (topupvalueDb.isPresent()) {
			logger.info("TopUpValue is  found with code" + code);
			return topupvalueDb.get();
		} else {
			throw new ResourceNotFoundException("TopUpValue not found with code:" + code);
		}
	}
}

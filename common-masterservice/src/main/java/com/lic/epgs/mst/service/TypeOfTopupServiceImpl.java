package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.LineOfBusinessController;
import com.lic.epgs.mst.entity.TypeOfTopupMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TypeOfTopupRepository;

@Service
@Transactional
public class TypeOfTopupServiceImpl implements TypeOfTopupService {

	@Autowired
	private TypeOfTopupRepository typeOfTopupRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LineOfBusinessController.class);

	@Override
	public List<TypeOfTopupMaster> getAllTypeOfTopup() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get type of topup list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return typeOfTopupRepository.findAll();
	}

	@Override
	public TypeOfTopupMaster getTypeOfTopupById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfTopupMaster> typeOfTopupDb = this.typeOfTopupRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for type of topup By Id" + id);
		if (typeOfTopupDb.isPresent()) {
			logger.info(" type of topup is  found with id" + id);
			return typeOfTopupDb.get();
		} else {
			throw new ResourceNotFoundException("type of topup not found with id:" + id);
		}
	}

	@Override
	public TypeOfTopupMaster getTypeOfTopupByCode(String code) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfTopupMaster> typeOfTopupCodeDb = this.typeOfTopupRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for type of topup By code" + code);
		if (typeOfTopupCodeDb.isPresent()) {
			logger.info("type of topup is  found with code" + code);
			return typeOfTopupCodeDb.get();
		} else {
			throw new ResourceNotFoundException("type of topup not found with code:" + code);
		}
	}

}

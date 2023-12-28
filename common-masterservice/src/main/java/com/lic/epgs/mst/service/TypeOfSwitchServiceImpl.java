package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.TypeOfSwitchController;
import com.lic.epgs.mst.entity.TypeOfSwitchMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TypeOfSwitchRepository;

@Service
@Transactional
public class TypeOfSwitchServiceImpl implements TypeOfSwitchService{
	@Autowired
	private TypeOfSwitchRepository typeOfSwitchRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TypeOfSwitchController.class);

	@Override
	public List<TypeOfSwitchMaster> getAllTypeOfSwitchType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get type of switch list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return typeOfSwitchRepository.findAll();	}

	@Override
	public TypeOfSwitchMaster getTypeOfSwitchById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfSwitchMaster> typeSwitchDb = this.typeOfSwitchRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for type of switch By Id" + id);
		if (typeSwitchDb.isPresent()) {
			logger.info("  type of  switch is  found with id" + id);
			return typeSwitchDb.get();
		} else {
			throw new ResourceNotFoundException("  type  of switch not found with id:" + id);
		}
	}

	@Override
	public TypeOfSwitchMaster getTypeOfSwitchByCode(String code) {
		// TODO Auto-generated method stub
		
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfSwitchMaster> mergerTypeCodeDb = this.typeOfSwitchRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for type switch By code" + code);
		if (mergerTypeCodeDb.isPresent()) {
			logger.info("  type of switch is  found with code" + code);
			return mergerTypeCodeDb.get();
		} else {
			throw new ResourceNotFoundException(" type of switch not found with code:" + code);
		}
	}

}
	

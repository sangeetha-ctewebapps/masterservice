package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.TermTypeController;
import com.lic.epgs.mst.entity.TypeOfLifeAssured;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfLifeAssuredServiceException;
import com.lic.epgs.mst.repository.TypeOfLifeAssuredRepo;

@Service
@Transactional
public class TypeOfLifeAssuredServiceImpl implements TypeOfLifeAssuredService {

	@Autowired
	private TypeOfLifeAssuredRepo typeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TermTypeController.class);

	@Override
	public List<TypeOfLifeAssured> getAllTypeOfLife() throws ResourceNotFoundException, TypeOfLifeAssuredServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return typeRepository.findAll();
	}

	@Override
	public TypeOfLifeAssured getTypeById(long typeId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfLifeAssured> typeDb = this.typeRepository.findById(typeId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Type of Life Assured By Id" + typeId);
		if (typeDb.isPresent()) {
			logger.info("Type of Life Assured is not found with id" + typeId);
			return typeDb.get();
		} else {
			throw new ResourceNotFoundException("Type of Life Assured is not found with id:" + typeId);
		}
	}

	@Override
	public TypeOfLifeAssured getTypeByCode(String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfLifeAssured> typeDb = this.typeRepository.findByTypeCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Type of Life Assured By Code" + code);
		if (typeDb.isPresent()) {
			logger.info("Type of Life Assured is found with code" + code);
			return typeDb.get();
		} else {
			throw new ResourceNotFoundException("Type of Life Assured is not found with code:" + code);
		}
	}

}

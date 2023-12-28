package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.TypeOfService;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TypeOfServiceRepository;

@Service
@Transactional
public class TypeOfServiceServiceImpl implements TypeOfServiceService {
	@Autowired
	TypeOfServiceRepository typeOfServiceRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<TypeOfService> getAllTypeOfService() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return typeOfServiceRepository.findAll();
	}

	@Override
	public TypeOfService getTypeOfServiceById(long TypeOfServiceId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfService> TypeOfServiceDb = this.typeOfServiceRepository.findById(TypeOfServiceId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Type Of Service By Id" + TypeOfServiceId);
		if (TypeOfServiceDb.isPresent()) {
			return TypeOfServiceDb.get();
		} else {
			throw new ResourceNotFoundException("Type Of Service not found with id:" + TypeOfServiceId);
		}
	}

	@Override
	public TypeOfService findByTypeOfServiceCode(String TypeOfSCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object customerDb = this.typeOfServiceRepository.findByTypeOfServiceCode(TypeOfSCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search TypeOfService details By customerCode:" + TypeOfSCode);
		if (customerDb != null) {
			return (TypeOfService) customerDb;
		} else {
			throw new ResourceNotFoundException("Type Of Service code not found:" + TypeOfSCode);
		}
	}

}

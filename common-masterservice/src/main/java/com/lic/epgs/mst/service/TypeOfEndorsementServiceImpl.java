package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.TypeOfEndorsement;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TypeOfEndorsementRepository;

@Service
@Transactional
public class TypeOfEndorsementServiceImpl implements TypeOfEndorsementService {
	@Autowired
	TypeOfEndorsementRepository typeOfEndorsementRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<TypeOfEndorsement> getAllTypeOfEndorsement() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return typeOfEndorsementRepository.findAll();
	}

	@Override
	public TypeOfEndorsement getTypeOfEndorsementById(long TypeoeId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfEndorsement> TypeOfEndorsementDb = this.typeOfEndorsementRepository.findById(TypeoeId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Type Of Endorsement By Id" + TypeoeId);
		if (TypeOfEndorsementDb.isPresent()) {
			return TypeOfEndorsementDb.get();
		} else {
			throw new ResourceNotFoundException("Type Of Endorsement not found with id:" + TypeoeId);
		}
	}

	@Override
	public TypeOfEndorsement findByTypeOfEndorsementCode(String TypeOfECode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object TypeOfEDb = this.typeOfEndorsementRepository.findByTypeOfEndorsementCode(TypeOfECode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search TypeOfEId details By customerCode:" + TypeOfECode);

		if (TypeOfEDb != null) {

			return (TypeOfEndorsement) TypeOfEDb;
		} else {
			throw new ResourceNotFoundException("Type Of Endorsement code not found:" + TypeOfECode);
		}
	}

}

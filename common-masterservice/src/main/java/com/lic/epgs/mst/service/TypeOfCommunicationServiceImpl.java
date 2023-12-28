package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.TypeOfCommunicationController;
import com.lic.epgs.mst.entity.TypeOfCommunicationMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TypeOfCommunicationRepository;

@Service
@Transactional
public class TypeOfCommunicationServiceImpl implements TypeOfCommunicationService {

	private static final Logger logger = LoggerFactory.getLogger(TypeOfCommunicationController.class);
	@Autowired
	private TypeOfCommunicationRepository typeOfCommunicationRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<TypeOfCommunicationMaster> getAllTypeCommunication() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return typeOfCommunicationRepository.findAll();
	}

	@Override
	public TypeOfCommunicationMaster getTypeCommunicationById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfCommunicationMaster> typeCommunicationDb = this.typeOfCommunicationRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for type of communication By Id" + id);
		if (typeCommunicationDb.isPresent()) {
			logger.info(" type of communication  is  found with id" + id);
			return typeCommunicationDb.get();
		} else {
			throw new ResourceNotFoundException(" type of communication not found with id:" + id);
		}
	}

	@Override
	public TypeOfCommunicationMaster getTypeCommunicationByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfCommunicationMaster> typeCommunicationDb = this.typeOfCommunicationRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for type of communication  By code" + code);
		if (typeCommunicationDb.isPresent()) {
			logger.info(" type of communication is  found with code" + code);
			return typeCommunicationDb.get();
		} else {
			throw new ResourceNotFoundException("type of communication not found with code:" + code);
		}
	}

}

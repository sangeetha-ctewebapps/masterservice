package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.EndorsementSubType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.EndorsementSubTypeRepository;

@Service
@Transactional
public class EndorsementSubTypeServiceImpl implements EndorsementSubTypeService {
	@Autowired
	EndorsementSubTypeRepository endorsementSubTypeRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<EndorsementSubType> getAllEndorsementSubType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return endorsementSubTypeRepository.findAll();
	}

	@Override
	public EndorsementSubType getEndorsementSubTypeById(long EndorsementSTId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<EndorsementSubType> EndorsementSubTypeDb = this.endorsementSubTypeRepository.findById(EndorsementSTId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Endorsement Sub Type By Id" + EndorsementSTId);
		if (EndorsementSubTypeDb.isPresent()) {
			return EndorsementSubTypeDb.get();
		} else {
			throw new ResourceNotFoundException("Endorsement Sub Type not found with id:" + EndorsementSTId);
		}
	}

	@Override
	public EndorsementSubType findByEndorsementSTCode(String EndorsementSTCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object customerDb = this.endorsementSubTypeRepository.findByEndorsementSTCode(EndorsementSTCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Customer details By endorsementSTCode:" + EndorsementSTCode);

		if (customerDb != null) {

			return (EndorsementSubType) customerDb;
		} else {
			throw new ResourceNotFoundException("Endorsement Sub Type code not found:" + EndorsementSTCode);
		}
	}

}

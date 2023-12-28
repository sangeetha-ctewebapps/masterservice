package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.TypeOfClaim;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TypeOfClaimRepository;

@Service
@Transactional
public class TypeOfClaimServiceImpl implements TypeOfClaimService {
	@Autowired
	TypeOfClaimRepository typeOfClaimRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<TypeOfClaim> getAllTypeOfClaim() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return typeOfClaimRepository.findAll();
	}

	@Override
	public TypeOfClaim getTypeOfClaimById(long ClaimId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TypeOfClaim> TypeOfClaimDb = this.typeOfClaimRepository.findById(ClaimId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Type Of Claim By Id" + ClaimId);
		if (TypeOfClaimDb.isPresent()) {
			return TypeOfClaimDb.get();
		} else {
			throw new ResourceNotFoundException("Type Of Claim not found with id:" + ClaimId);
		}
	}

	@Override
	public TypeOfClaim findByTypeOfClaimCode(String ClaimCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object coDb = this.typeOfClaimRepository.findByTypeOfClaimCode(ClaimCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Co details By coCode:" + ClaimCode);

		if (coDb != null) {

			return (TypeOfClaim) coDb;
		} else {
			throw new ResourceNotFoundException("Type Of Claim code not found:" + ClaimCode);
		}
	}

}

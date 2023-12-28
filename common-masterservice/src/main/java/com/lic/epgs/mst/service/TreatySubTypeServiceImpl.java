package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.TreatySubTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TreatySubTypeRepository;

@Service
@Transactional
public class TreatySubTypeServiceImpl implements TreatySubTypeService {

	@Autowired
	TreatySubTypeRepository treatysubtyperepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<TreatySubTypeMst> getAllTreatySubType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return treatysubtyperepository.findAll();
	}

	@Override
	public TreatySubTypeMst getTreatySubTypeById(long treatysubtypeId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TreatySubTypeMst> TreatySubTypeDb = this.treatysubtyperepository.findById(treatysubtypeId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for treatysubtype By Id" + treatysubtypeId);
		if (TreatySubTypeDb.isPresent()) {
			return TreatySubTypeDb.get();
		} else {
			throw new ResourceNotFoundException("treatysubtype not found with id:" + treatysubtypeId);
		}
	}

	@Override
	public TreatySubTypeMst getTreatySubTypeByCode(String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TreatySubTypeMst> treatysubtypeDb = this.treatysubtyperepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Treaty Sub Type code not found:" + code);
		if (treatysubtypeDb.isPresent()) {
			return treatysubtypeDb.get();
		} else {
			throw new ResourceNotFoundException("Treaty Sub Type code not found:" + code);
		}
	}

}

package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.TreatyTypeController;
import com.lic.epgs.mst.entity.TreatyTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.TreatyTypeRepository;

@Service
@Transactional
public class TreatyTypeServiceImpl implements TreatyTypeService {

	@Autowired
	TreatyTypeRepository treatytyperepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TreatyTypeController.class);

	@Override
	public List<TreatyTypeMst> getAllTreatyType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return treatytyperepository.findAll();
	}

	@Override
	public TreatyTypeMst createTreatyType(TreatyTypeMst treatytype) {
		// TODO Auto-generated method stub
		return treatytyperepository.save(treatytype);
	}

	@Override
	public TreatyTypeMst getTreatyTypeById(long treatytypeId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TreatyTypeMst> TreatyTypeDb = this.treatytyperepository.findById(treatytypeId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for TreatyType By Id" + treatytypeId);
		if (TreatyTypeDb.isPresent()) {
			logger.info("TreatyType is  found with id" + treatytypeId);
			return TreatyTypeDb.get();
		} else {
			throw new ResourceNotFoundException("TreatyType not found with id:" + treatytypeId);
		}

	}

	@Override
	public TreatyTypeMst getTreatyTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TreatyTypeMst> treatytypeDb = this.treatytyperepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for TreatyType By code" + code);
		if (treatytypeDb.isPresent()) {
			logger.info("TreatyType is  found with code" + code);
			return treatytypeDb.get();
		} else {
			throw new ResourceNotFoundException("TreatyType not found with code:" + code);
		}
	}
}

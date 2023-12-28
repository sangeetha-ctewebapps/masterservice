package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.CoverageType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CoverageTypeRepository;

@Service
@Transactional
public class CoverageTypeServiceImpl implements CoverageTypeService {
	@Autowired
	CoverageTypeRepository coveragetyperepository;
	String className = this.getClass().getSimpleName();

	@Override
	//@Cacheable()
	public List<CoverageType> getAllCoverageType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return coveragetyperepository.findAll();
	}

	@Override
	//@Cacheable(value = "masterCache", key = "#CoverageId")
	public CoverageType getCoverageTypeById(long CoverageId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CoverageType> CoverageTypeDb = this.coveragetyperepository.findById(CoverageId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Coverage type By Id" + CoverageId);
		if (CoverageTypeDb.isPresent()) {
			return CoverageTypeDb.get();
		} else {
			throw new ResourceNotFoundException("Coverage type not found with id:" + CoverageId);
		}
	}

	@Override
	public CoverageType findByCoverageCode(String CoverageCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object coverageDb = this.coveragetyperepository.findByCoverageCode(CoverageCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Coverage details By coverageCode:" + CoverageCode);

		if (coverageDb != null) {

			return (CoverageType) coverageDb;
		} else {
			throw new ResourceNotFoundException("Coverage type code not found:" + CoverageCode);
		}
	}

}

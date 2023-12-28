package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.MergerTypeController;
import com.lic.epgs.mst.entity.MergerTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.MergerTypeRepository;

@Service
@Transactional
public class MergerTypeServiceImpl implements MergerTypeService {

	@Autowired
	private MergerTypeRepository mergerTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MergerTypeController.class);

	@Override
	public List<MergerTypeMaster> getAllMergerType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get merger type list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return mergerTypeRepository.findAll();
	}

	@Override
	public MergerTypeMaster getMergerTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<MergerTypeMaster> mergerTypeDb = this.mergerTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for merger type By Id" + id);
		if (mergerTypeDb.isPresent()) {
			logger.info(" merger type is  found with id" + id);
			return mergerTypeDb.get();
		} else {
			throw new ResourceNotFoundException(" merger type not found with id:" + id);
		}
	}

	@Override
	public MergerTypeMaster getMergerTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<MergerTypeMaster> mergerTypeCodeDb = this.mergerTypeRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for merger type By code" + code);
		if (mergerTypeCodeDb.isPresent()) {
			logger.info(" merger type is  found with code" + code);
			return mergerTypeCodeDb.get();
		} else {
			throw new ResourceNotFoundException(" merger type not found with code:" + code);
		}
	}

}

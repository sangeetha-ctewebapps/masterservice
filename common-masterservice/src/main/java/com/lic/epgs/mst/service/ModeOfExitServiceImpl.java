package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ModeOfExitController;
import com.lic.epgs.mst.entity.ModeTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ModeOfExitRepository;

@Service
@Transactional
public class ModeOfExitServiceImpl implements ModeOfExitService {
	@Autowired
	ModeOfExitRepository modeofexitrepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ModeOfExitController.class);

	@Override
	public List<ModeTypeMst> getAllModeOfExit() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return modeofexitrepository.findAll();
	}

	@Override
	public ModeTypeMst getModeOfExitById(long modeId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ModeTypeMst> modeDb = this.modeofexitrepository.findById(modeId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for ModeOfExit By Id" + modeId);
		if (modeDb.isPresent()) {
			logger.info("ModeOfExit type is not found with id" + modeId);
			return modeDb.get();
		} else {
			throw new ResourceNotFoundException("ModeOfExit not found with id:" + modeId);
		}

	}

	@Override
	public ModeTypeMst getModeOfExitByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ModeTypeMst> modeDb = this.modeofexitrepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for ModeOfExit  By code" + code);
		if (modeDb.isPresent()) {
			logger.info("ModeOfExit is  found with code" + code);
			return modeDb.get();
		} else {
			throw new ResourceNotFoundException("ModeOfExit not found with code:" + code);
		}
	}

}

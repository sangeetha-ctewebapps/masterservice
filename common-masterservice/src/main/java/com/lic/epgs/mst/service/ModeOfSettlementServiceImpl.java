package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.ModeOfSettlementController;
import com.lic.epgs.mst.entity.ModeOfSettlementMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ModeOfSettlementRepository;

@Service
@Transactional
public class ModeOfSettlementServiceImpl implements ModeOfSettlementService {

	@Autowired
	private ModeOfSettlementRepository modeOfSettlementRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ModeOfSettlementController.class);

	@Override
	public List<ModeOfSettlementMst> getAllModeOfSettlement() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get ModeOfSettlement list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return modeOfSettlementRepository.findAll();
	}

	@Override
	public ModeOfSettlementMst getModeOfSettlementById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ModeOfSettlementMst> modeofsettlementDb = this.modeOfSettlementRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for ModeOfSettlement By Id" + id);
		if (modeofsettlementDb.isPresent()) {
			logger.info("ModeOfSettlement is  found with id" + id);
			return modeofsettlementDb.get();
		} else {
			throw new ResourceNotFoundException("ModeOfSettlement not found with id:" + id);
		}
	}

	@Override
	public ModeOfSettlementMst getModeOfSettlementByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ModeOfSettlementMst> modeofsettlementDb = this.modeOfSettlementRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for ModeOfSettlement By code" + code);
		if (modeofsettlementDb.isPresent()) {
			logger.info("ModeOfSettlement is  found with code" + code);
			return modeofsettlementDb.get();
		} else {
			throw new ResourceNotFoundException("ModeOfSettlement not found with code:" + code);
		}
	}

}

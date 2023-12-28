package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.UnderwritingLoadingController;
import com.lic.epgs.mst.entity.UnderwritingLoadingMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.UnderwritingLoadingRepository;

@Service
@Transactional
public class UnderwritingLoadingServiceImpl implements UnderwritingLoadingService {

	@Autowired
	private UnderwritingLoadingRepository underWritingLoadingRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(UnderwritingLoadingController.class);

	@Override
	public List<UnderwritingLoadingMst> getAllUnderwritingLoading() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get UnderwritingLoading list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return underWritingLoadingRepository.findAll();
	}

	@Override
	public UnderwritingLoadingMst getUnderwritingLoadingById(long underwritingId) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<UnderwritingLoadingMst> underwritingloadingDb = this.underWritingLoadingRepository.findById(underwritingId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for UnderwritingLoading By Id" + underwritingId);
		if (underwritingloadingDb.isPresent()) {
			logger.info("UnderwritingLoading is  found with id" + underwritingId);
			return underwritingloadingDb.get();
		} else {
			throw new ResourceNotFoundException("UnderwritingLoading not found with id:" + underwritingId);
		}
	}

	@Override
	public UnderwritingLoadingMst getUnderwritingLoadingByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<UnderwritingLoadingMst> underwritingloadingDb = this.underWritingLoadingRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for UnderwritingLoading By code" + code);
		if (underwritingloadingDb.isPresent()) {
			logger.info("UnderwritingLoading is  found with code" + code);
			return underwritingloadingDb.get();
		} else {
			throw new ResourceNotFoundException("UnderwritingLoading not found with code:" + code);
		}
	}

}

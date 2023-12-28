package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ExtractionParameterController;
import com.lic.epgs.mst.entity.ExtractionParameterMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ExtractionParameterRepository;

@Service
@Transactional
public class ExtractionParameterServiceImpl implements ExtractionParameterService {
	private static final Logger logger = LoggerFactory.getLogger(ExtractionParameterController.class);
	@Autowired
	private ExtractionParameterRepository extractionParameterRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<ExtractionParameterMaster> getAllExtractionParameter() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return extractionParameterRepository.findAll();
	}

	@Override
	public ExtractionParameterMaster getExtractionParameterById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ExtractionParameterMaster> extractionDb = this.extractionParameterRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for extraction parameter By Id" + id);
		if (extractionDb.isPresent()) {
			logger.info("extraction parameters is  found with id" + id);
			return extractionDb.get();
		} else {
			throw new ResourceNotFoundException("extraction parameter not found with id:" + id);
		}
	}

	@Override
	public ExtractionParameterMaster getExtractionParameterByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ExtractionParameterMaster> extractionDb = this.extractionParameterRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for extraction parameters By code" + code);
		if (extractionDb.isPresent()) {
			logger.info("extraction parameter is  found with code" + code);
			return extractionDb.get();
		} else {
			throw new ResourceNotFoundException("extraction parameter not found with code:" + code);
		}
	}

}

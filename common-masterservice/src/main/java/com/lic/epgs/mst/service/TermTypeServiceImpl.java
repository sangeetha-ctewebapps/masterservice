package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.TermTypeController;
import com.lic.epgs.mst.entity.TermType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TermTypeServiceException;
import com.lic.epgs.mst.repository.TermTypeRepo;

@Service
@Transactional
public class TermTypeServiceImpl implements TermTypeService {

	@Autowired
	private TermTypeRepo termRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(TermTypeController.class);

	@Override
	public List<TermType> getAllTerm() throws ResourceNotFoundException, TermTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return termRepository.findAll();
	}

	@Override
	public TermType getTermById(long termId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TermType> termDb = this.termRepository.findById(termId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for coverage By Id" + termId);
		if (termDb.isPresent()) {
			logger.info("Term Type is not found with id" + termId);
			return termDb.get();
		} else {
			throw new ResourceNotFoundException("Term Type is not found with id:" + termId);
		}
	}

	@Override
	public TermType getTermByCode(String termCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<TermType> termDb = this.termRepository.findByTermCode(termCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Term Type By Code" + termCode);
		if (termDb.isPresent()) {
			logger.info("Term Type is not found with code" + termCode);
			return termDb.get();
		} else {
			throw new ResourceNotFoundException("Term Type is not found with code:" + termCode);
		}
	}

}

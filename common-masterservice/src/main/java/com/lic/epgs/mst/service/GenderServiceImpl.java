package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.GenderController;
import com.lic.epgs.mst.entity.GenderMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.GenderRepository;

@Service
@Transactional
public class GenderServiceImpl implements GenderService {

	@Autowired
	GenderRepository genderrepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(GenderController.class);

	@Override
	public List<GenderMst> getAllGender() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return genderrepository.findAll();
	}

	@Override
	public GenderMst getGenderById(long genderId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GenderMst> GenderDb = this.genderrepository.findById(genderId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for gender By Id" + genderId);
		if (GenderDb.isPresent()) {
			logger.info("Gender is  found with id" + genderId);
			return GenderDb.get();
		} else {
			throw new ResourceNotFoundException("Gender not found with id:" + genderId);
		}

	}

	@Override
	public GenderMst getGenderByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GenderMst> genderDb = this.genderrepository.findByGender(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Gender By code" + code);
		if (genderDb.isPresent()) {
			logger.info("gender is  found with code" + code);
			return genderDb.get();
		} else {
			throw new ResourceNotFoundException("gender not found with code:" + code);
		}
	}
}

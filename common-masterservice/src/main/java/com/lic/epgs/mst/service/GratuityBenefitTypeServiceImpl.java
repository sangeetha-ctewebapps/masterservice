package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.GratuityBenefitTypeController;
import com.lic.epgs.mst.entity.GratuityTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.GratuityBenefitTypeRepository;

@Service
@Transactional
public class GratuityBenefitTypeServiceImpl implements GratuityBenefitTypeService {

	@Autowired
	GratuityBenefitTypeRepository gratuitybenefittyperepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(GratuityBenefitTypeController.class);

	@Override
	public List<GratuityTypeMst> getAllGratuityBenefitType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return gratuitybenefittyperepository.findAll();
	}

	@Override
	public GratuityTypeMst getGratuityBenefitTypeById(long gratuityId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GratuityTypeMst> gratuityDb = this.gratuitybenefittyperepository.findById(gratuityId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for gratuity By Id" + gratuityId);
		if (gratuityDb.isPresent()) {
			logger.info("gratuity type is  found with id" + gratuityId);
			return gratuityDb.get();
		} else {
			throw new ResourceNotFoundException("gratuity not found with id:" + gratuityId);
		}

	}

	@Override
	public GratuityTypeMst getGratuityBenefitTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GratuityTypeMst> gratuityDb = this.gratuitybenefittyperepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for gratuity By code" + code);
		if (gratuityDb.isPresent()) {
			logger.info("gratuity is  found with code" + code);
			return gratuityDb.get();
		} else {
			throw new ResourceNotFoundException("gratuity not found with code:" + code);
		}
	}
}

package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.PortfolioTypeController;
import com.lic.epgs.mst.entity.PortfolioTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.PortfolioTypeRepository;

@Transactional
@Service
public class PortfolioTypeServiceImpl implements PortfolioTypeService {

	@Autowired
	private PortfolioTypeRepository portfolioTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(PortfolioTypeController.class);

	@Override
	public List<PortfolioTypeMaster> getAllPortfolioType() {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get layer type list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return portfolioTypeRepository.findAll();
	}

	@Override
	public PortfolioTypeMaster getPortfolioTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<PortfolioTypeMaster> portfolioTypeDb = this.portfolioTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for portfolio type By Id" + id);
		if (portfolioTypeDb.isPresent()) {
			logger.info("portfolio type is  found with id" + id);
			return portfolioTypeDb.get();
		} else {
			throw new ResourceNotFoundException(" portfolio type not found with id:" + id);
		}
	}

	@Override
	public PortfolioTypeMaster getPortfolioTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<PortfolioTypeMaster> portfolioTypeCodeDb = this.portfolioTypeRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for portfolio type By code" + code);
		if (portfolioTypeCodeDb.isPresent()) {
			logger.info("portfolio type is  found with code" + code);
			return portfolioTypeCodeDb.get();
		} else {
			throw new ResourceNotFoundException("portfolio type not found with code:" + code);
		}
	}

}

package com.lic.epgs.mst.usermgmt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.usermgmt.controller.MasterCadreController;
import com.lic.epgs.mst.usermgmt.entity.MasterCadre;
import com.lic.epgs.mst.usermgmt.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MasterCadreServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.usermgmt.repository.MasterCadreRepo;

@Service
@Transactional
public class MasterCadreServiceImpl implements MasterCadreService  {
	
	@Autowired
	MasterCadreRepo mastercadreRepo;
	
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MasterCadreController.class);

	/*
	 * Description: This function is used for getting all the data from Cadres Module
	 * Table Name- MASTER_CADRE
	 * Author- Nandini R
	 */
	
	 @Override
	public List<MasterCadre> getAllCadres()
			throws ResourceNotFoundException, MasterCadreServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for All Cadres");
		logger.info("master cadre search by AllCadres");
		return mastercadreRepo.findAll();
	}

}

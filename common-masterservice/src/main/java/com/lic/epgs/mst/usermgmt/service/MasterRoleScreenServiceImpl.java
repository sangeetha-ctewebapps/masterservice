package com.lic.epgs.mst.usermgmt.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.controller.MasterRoleController;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleScreenEntity;
import com.lic.epgs.mst.usermgmt.repository.MasterRoleScreenRepository;

@Service
@Transactional

public class MasterRoleScreenServiceImpl implements MasterRoleScreenService {
	
	@Autowired
	private MasterRoleScreenRepository masterRoleScreenRepository;
	
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MasterRoleController.class);
	
	/*
	 * Description: This function is used for getting all the data from MASTER ROLE SCREEN Module
	 * Table Name- MASTER_ROLE_SCREEN
	 * Author- Nandini R
	 */
	
	@Override
	 public List<MasterRoleScreenEntity> getAllScreens() {
		
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get Screen list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return masterRoleScreenRepository.getAllScreens();
	}

	/*
	 * Description: This function is used for getting data from MASTER ROLE SCREEN Module by using the role id
	 * Table Name- MASTER_ROLE_SCREEN
	 * Author- Nandini R
	 */
	
	@Override
	public MasterRoleScreenEntity getAllScreensByRoleId(Long roleId) throws Exception {
    
		logger.info("Start getUserRoleByMasterUserId");
		
		logger.info("roleId--"+roleId);
		
		MasterRoleScreenEntity objMasterRoleScreenEntity = masterRoleScreenRepository.findByRoleId(roleId);
	
		logger.info("End getAllScreensByRoleId");
		
		return objMasterRoleScreenEntity;
	}
	
	

}

package com.lic.epgs.mst.usermgmt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.controller.UserController;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	/*
	 * Description: This function is used for getting all the data from User Role Mapping  Module
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Sandeep D
	 */
	@Override
	 public List<MasterUsersEntity> getAllUsers() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get User list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return userRepository.getAllUser();
	}

	
}

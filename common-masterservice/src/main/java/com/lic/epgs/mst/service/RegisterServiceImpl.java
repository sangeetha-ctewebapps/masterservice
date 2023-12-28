package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.RegisterController;
import com.lic.epgs.mst.entity.RegisterTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RegisterRepository;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	RegisterRepository registerrepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Override
	public List<RegisterTypeMst> getAllRegister() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return registerrepository.findAll();
	}

	@Override
	public RegisterTypeMst getRegisterById(long registerId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RegisterTypeMst> registerDb = this.registerrepository.findById(registerId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for register By Id" + registerId);
		if (registerDb.isPresent()) {
			logger.info("register type is  found with id" + registerId);
			return registerDb.get();
		} else {
			throw new ResourceNotFoundException("register not found with id:" + registerId);
		}

	}

	@Override
	public RegisterTypeMst getRegisterByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RegisterTypeMst> registerDb = this.registerrepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for register By code" + code);
		if (registerDb.isPresent()) {
			logger.info("register is  found with code" + code);
			return registerDb.get();
		} else {
			throw new ResourceNotFoundException("register not found with code:" + code);
		}
	}

}

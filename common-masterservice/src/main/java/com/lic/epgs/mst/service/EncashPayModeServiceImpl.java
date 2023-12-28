package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.EncashPayModeController;
import com.lic.epgs.mst.entity.EncashPayModeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.EncashPayModeRepository;

@Service
@Transactional
public class EncashPayModeServiceImpl implements EncashPayModeService {
	@Autowired
	EncashPayModeRepository encashPayModeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(EncashPayModeController.class);

	@Override
	public List<EncashPayModeMst> getAllEncashPayMode() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		logger.info("i get EncashPayMode list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return encashPayModeRepository.findAll();
	}

	@Override
	public EncashPayModeMst getEncashPayModeById(long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<EncashPayModeMst> encashpaymodeDb = this.encashPayModeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for EncashPayMode By Id" + id);
		if (encashpaymodeDb.isPresent()) {
			logger.info("EncashPayMode is  found with id" + id);
			return encashpaymodeDb.get();
		} else {
			throw new ResourceNotFoundException("EncashPayMode not found with id:" + id);
		}
	}

	@Override
	public EncashPayModeMst getEncashPayModeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<EncashPayModeMst> encashpaymodeDb = this.encashPayModeRepository.findByEncashPayCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for EncashPayMode By code" + code);
		if (encashpaymodeDb.isPresent()) {
			logger.info("EncashPayMode is  found with code" + code);
			return encashpaymodeDb.get();
		} else {
			throw new ResourceNotFoundException("EncashPayMode not found with code:" + code);
		}
	}

}

package com.lic.epgs.mst.usermgmt.service;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.entity.MasterCategoryDesignationsEntity;
import com.lic.epgs.mst.usermgmt.repository.MasterCategoryDesignationsRepository;

@Service
@Transactional

public class CategoryDesignationsServiceImpl implements CategoryDesignationsService {

	@Autowired
	 private MasterCategoryDesignationsRepository masterCategoryDesignationsRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CategoryDesignationsServiceImpl.class);

	/*
	 * Description: This function is used for getting all the data from Category Designation Module
	 * Table Name- MASTER_CATEGORY_DESIGNATION
	 * Author- Nandini R
	 */
	
	@Override
	public List<MasterCategoryDesignationsEntity> getALLCategoryDesignations() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get Category designation list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return masterCategoryDesignationsRepository.getALLCategoryDesignations();

	}

}

package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.GroupCategoryController;
import com.lic.epgs.mst.entity.GroupCategoryMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.GroupCategoryRepository;

@Service
@Transactional
public class GroupCategoryServiceImpl implements GroupCategoryService {

	@Autowired
	private GroupCategoryRepository groupCategoryRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(GroupCategoryController.class);

	@Override
	public List<GroupCategoryMst> getAllGroupCategory() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get GroupCategory list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return groupCategoryRepository.findAll();
	}

	@Override
	public GroupCategoryMst getGroupCategoryById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GroupCategoryMst> groupcategoryDb = this.groupCategoryRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for GroupCategory By Id" + id);
		if (groupcategoryDb.isPresent()) {
			logger.info("GroupCategory is  found with id" + id);
			return groupcategoryDb.get();
		} else {
			throw new ResourceNotFoundException("GroupCategory not found with id:" + id);
		}
	}

	@Override
	public GroupCategoryMst getGroupCategoryByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GroupCategoryMst> groupcategoryDb = this.groupCategoryRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for GroupCategory By code" + code);
		if (groupcategoryDb.isPresent()) {
			logger.info("GroupCategory is  found with code" + code);
			return groupcategoryDb.get();
		} else {
			throw new ResourceNotFoundException("GroupCategory not found with code:" + code);
		}
	}

}

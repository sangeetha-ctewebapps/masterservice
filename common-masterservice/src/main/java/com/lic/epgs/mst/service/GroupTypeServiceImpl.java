package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.GroupType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.GroupTypeRepository;

@Service
@Transactional
public class GroupTypeServiceImpl implements GroupTypeService {
	@Autowired
	GroupTypeRepository grouptyperepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<GroupType> getAllGroupType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return grouptyperepository.findAll();
	}

	@Override
	public GroupType getGroupTypeById(long GroupId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GroupType> GroupTypeDb = this.grouptyperepository.findById(GroupId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Group type By Id" + GroupId);
		if (GroupTypeDb.isPresent()) {
			return GroupTypeDb.get();
		} else {
			throw new ResourceNotFoundException("Group type not found with id:" + GroupId);
		}
	}

	@Override
	public GroupType findByGroupCode(String GroupCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object groupDb = this.grouptyperepository.findByGroupCode(GroupCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Group details By groupCode:" + GroupCode);

		if (groupDb != null) {

			return (GroupType) groupDb;
		} else {
			throw new ResourceNotFoundException("Group type code not found:" + GroupCode);
		}
	}

}

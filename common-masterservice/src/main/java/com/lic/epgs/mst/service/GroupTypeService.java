package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.GroupType;

public interface GroupTypeService {

	List<GroupType> getAllGroupType();

	public GroupType getGroupTypeById(long groupId);

	public GroupType findByGroupCode(String groupcode);

}

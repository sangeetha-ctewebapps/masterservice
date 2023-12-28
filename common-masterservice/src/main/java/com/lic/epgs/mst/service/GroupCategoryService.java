package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.GroupCategoryMst;

public interface GroupCategoryService {

	List<GroupCategoryMst> getAllGroupCategory();

	public GroupCategoryMst getGroupCategoryById(long id);

	public GroupCategoryMst getGroupCategoryByCode(String code);
}

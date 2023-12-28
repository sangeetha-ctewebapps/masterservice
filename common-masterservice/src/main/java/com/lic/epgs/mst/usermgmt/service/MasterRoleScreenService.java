package com.lic.epgs.mst.usermgmt.service;

import java.util.List;

import com.lic.epgs.mst.usermgmt.entity.MasterRoleScreenEntity;

public interface MasterRoleScreenService {
	
	
	public List<MasterRoleScreenEntity> getAllScreens();
	
	public MasterRoleScreenEntity getAllScreensByRoleId(Long roleId) throws Exception;


}

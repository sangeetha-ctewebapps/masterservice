package com.lic.epgs.mst.usermgmt.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.RolesAssignmentHistory;
import com.lic.epgs.mst.usermgmt.exceptionhandling.RolesAssignmentException;
import com.lic.epgs.rhssomodel.RolesAssignmnetHistoryModel;
import com.lic.epgs.rhssomodel.UserCreationModel;

public interface RolesAssignmentHistoryService {

	public ResponseEntity<Object> rolesAssigmentHistory(RolesAssignmentHistory rolesAssignmentHistory) throws  RolesAssignmentException;
	
	public ResponseEntity<Object> getrolesAssigmentHistory(RolesAssignmentHistory rolesAssignmentHistory) throws  RolesAssignmentException;

	public ResponseEntity<Object> getrolesAndUsesAssigmentHistory(RolesAssignmnetHistoryModel rolesAssignmentHistoryModel,UserCreationModel userCreationModel) throws  RolesAssignmentException;
	
}

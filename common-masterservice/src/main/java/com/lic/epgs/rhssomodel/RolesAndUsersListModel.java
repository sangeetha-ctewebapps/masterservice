package com.lic.epgs.rhssomodel;

import java.util.List;

import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.RolesAssignmentHistory;

public class RolesAndUsersListModel  {

	private List<RolesAssignmentHistory> rolesAssignmnetHistoryModel;
	
	private List<PortalMasterEntity>  userCreationModel;

	public List<RolesAssignmentHistory> getRolesAssignmnetHistoryModel() {
		return rolesAssignmnetHistoryModel;
	}

	public void setRolesAssignmnetHistoryModel(List<RolesAssignmentHistory> rolesAssignmnetHistoryModel) {
		this.rolesAssignmnetHistoryModel = rolesAssignmnetHistoryModel;
	}

	public List<PortalMasterEntity> getUserCreationModel() {
		return userCreationModel;
	}

	public void setUserCreationModel(List<PortalMasterEntity> userCreationModel) {
		this.userCreationModel = userCreationModel;
	}

	public RolesAndUsersListModel(List<RolesAssignmentHistory> rolesAssignmnetHistoryModel,
			List<PortalMasterEntity> userCreationModel) {
		super();
		this.rolesAssignmnetHistoryModel = rolesAssignmnetHistoryModel;
		this.userCreationModel = userCreationModel;
	}

	public RolesAndUsersListModel() {
		super();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	

	
	
	
	
}

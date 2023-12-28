package com.lic.epgs.mst.usermgmt.service; 

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lic.epgs.mst.usermgmt.entity.MasterRolesBulkEntity;
import com.lic.epgs.mst.usermgmt.entity.MphNameEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.RolesAssignmentHistory;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MphUserServiceException;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;
import com.lic.epgs.rhssomodel.AccessTokenResponse;
import com.lic.epgs.rhssomodel.ResponseModel;

public interface MphUserService {
	
	 
	
	public ResponseEntity<Object> addMphUser(PortalMasterEntity portalMasterEntity) throws MphUserServiceException;
	
	 public ResponseEntity<Object> deleteMphUser(PortalMasterEntity portalMasterEntity) throws MphUserServiceException;
	
	 public ResponseEntity<Object> deleteRolesForSuperAdmin(RolesAssignmentHistory rolesAssignmentEntity) throws MphUserServiceException;
	 
	 public ResponseEntity<Object> approveRejectUser(org.json.JSONArray userArray) throws MphUserServiceException;
	 
	 public ResponseEntity<Object> getAllAdminOrdinaryUsers(String is_MphAdmin, String is_Active, String loggedInUserUnitCode) throws Exception;
	 
	 public ResponseEntity<Object> getAllUsersPendingForApproval(String action, String fromDate, String toDate, String loggedInUserUnitCode) throws Exception;
	 
	 public ResponseEntity<Object> getAllUsersBasedOnStatus(String status, String loggedInUserUnitCode) throws Exception;
	 
	 public ResponseEntity<Object> getValidateUserName(String username) throws MphUserServiceException;
	 
	 public ResponseEntity<Object> getValidateEmail(String email) throws MphUserServiceException;
	 
	 public ResponseEntity<Object> getValidateMobile(String mobile) throws MphUserServiceException;
	 
	 public AccessTokenResponse generateToken();
	 
	 public ResponseEntity<Object> updateLoggedInUserFlagDetails(String userName, String loginFlag) throws Exception;
	 
	 public ResponseEntity<Object> getUserDetailsInSuperAdmin(String username) throws MphUserServiceException;
	
	 public ResponseEntity<Object> checkPolicyAvailability(String policyNumber, String loggedInUserUnitCode) throws Exception;
	 
}

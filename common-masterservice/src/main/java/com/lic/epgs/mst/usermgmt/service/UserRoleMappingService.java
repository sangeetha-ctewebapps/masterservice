package com.lic.epgs.mst.usermgmt.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.ModuleDescriptionRoleTypeEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;
import com.lic.epgs.mst.usermgmt.modal.LocationModel;
import com.lic.epgs.mst.usermgmt.modal.MandhanLocationModel;
import com.lic.epgs.mst.usermgmt.modal.MasterRoleModal;
import com.lic.epgs.mst.usermgmt.modal.UoZoAdminModel;

public interface UserRoleMappingService {
	
	List<UserRoleMappingEntity> getAllUserRole();

	public UserRoleMappingEntity getUserRoleByMasterUserId(long masterUserId) throws UserRoleMappingException;
	
	public ModuleDescriptionRoleTypeEntity getDescriptionByRoleTypeName(String roleTypeName)throws UserRoleMappingException;
	
	public UserRoleMappingEntity getUserRoleBySrNumber(long srNumber)throws UserRoleMappingException;
	
	List<UoZoAdminModel> zoSearch(String location, String srNumber, String userName) throws SQLException;;

	List<UoZoAdminModel> uoSearch(String locationType,String location, String srNumber, String userName) throws SQLException;
	
	public List<MasterRoleModal> checkAdminRoleBySrNo( String srNumber);

	public List<MasterRoleModal> checkAdminRoleByEmail(String email);

	 public List<LocationModel> checkAdminRoleBySrNoForSwalamban(String srNumber);
	 
	 public MasterUsersEntity  checkUserNameActiveStatus(String srNumber);
	 
		
	 public List<MandhanLocationModel> checkAdminRoleBySrNoForMandhan(String srNumber);
	 
	 public PortalMasterEntity  getMasterUserIdByUserName(String username) throws UserRoleMappingException;
	 
	 public ResponseEntity<Map<String, Object>> updateUserDetailAfterLogin(PortalMasterEntity portalMasterEntity) throws UserRoleMappingException;
	 
	 public ResponseEntity<Map<String, Object>> updateUserDetailAfterLogout(PortalMasterEntity portalMasterEntity) throws UserRoleMappingException;

	}

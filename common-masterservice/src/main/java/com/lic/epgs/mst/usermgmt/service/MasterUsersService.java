package com.lic.epgs.mst.usermgmt.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import com.lic.epgs.mst.modal.COModel;
import com.lic.epgs.mst.modal.CoOfficeModel;
import com.lic.epgs.mst.modal.UOModel;
import com.lic.epgs.mst.modal.UnitModel;
import com.lic.epgs.mst.modal.UsersModal;
import com.lic.epgs.mst.modal.ZOModel;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.modal.AssignRolesModel;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;
import com.lic.epgs.mst.usermgmt.modal.MasterUnitForGC;
import com.lic.epgs.mst.usermgmt.modal.SrnumberUsernameModel;
import com.lic.epgs.mst.usermgmt.modal.UserRoleModel;
import com.lic.epgs.rhssomodel.UserDetailsResponse;

public interface MasterUsersService {
	
	public MasterUsersEntity getUserRoleByLocation(Long masterUserId) throws Exception;
	
	public ResponseEntity<Map<String, Object>> masterUserSearch(String location, String locationType,String srNumber, String userName,String typeOfUser) throws SQLException;
	
	public List<MasterUsersEntity> getMasterUserSearchBySrNumber(String srNumber) throws SQLException;
	
	public ResponseEntity<Map<String, Object>> addMasterUser(MasterUsersEntity masterUsersEntity) throws Exception;

	public ResponseEntity<Map<String, Object>> editMasterUser(MasterUsersEntity masterUsersEntity) throws Exception;
	
	public ResponseEntity<Map<String, Object>> deleteUser(Long masterUserId) throws Exception;

	List<UnitModel> getUnitDetails(String userName) throws Exception;
	
	List<MasterUnitForGC> getStateDetailsForGC(String srNumber) throws Exception;
	
	public ResponseEntity<Map<String, Object>> getUserDetailsByEmployeeCode(String srNumber) throws Exception;

	List<COModel> getAllAdminOfficeDetails(String userName) throws Exception;
	
	List<MasterUsersEntity> getAllUsersByLocationType(String emailId) throws Exception;

	public List<UOModel> getUoDetails(String userName) throws Exception;

	public List<CoOfficeModel> getCoDetails(String userName) throws Exception;
	
	public List<UserRoleModel> getUserRoleDetails(String srNumber,String loggedInUsername) throws Exception;
	
	public List<SrnumberUsernameModel> getAllUsersBySrNumber(String locationCode);

	public List<ZOModel> getZoDetails(String userName) throws Exception;
	
	List<MasterUsersEntity> getSearchByUserName(String srNumber, String userName,String loggedInUsername) throws SQLException;

	 public MasterUsersEntity getUserByUserName(String userName) throws Exception;
	
   /* public int  findDisabledByUsername(String username) throws Exception;*/
	
	List<UsersModal> getUnitDetailsByUserName(String userName) throws Exception;
	
	public List<MasterUsersEntity> getUserDetailsByUnitCode(String locationCode) throws Exception;
	
	 public ResponseEntity<Map<String, Object>> getValidateSrNumber(String srNumber,String isActiveFlag) throws Exception;
	 
	 List<MasterUsersEntity> getAllUsersByParticularLocationType(String locationType) throws Exception;
	 
	 public ResponseEntity<Map<String, Object>> checkActiveSrNumber(String srNumber) throws Exception;

	public ResponseEntity<Map<String, Object>> findDisabledByUsername(MasterUsersEntity masterUserEntity) throws Exception;
	
	public ResponseEntity<Map<String, Object>> findEnabledByUsername(MasterUsersEntity masterUserEntity) throws Exception;
	 
	public ResponseEntity<Map<String, Object>> updateLoggedInUserFlag(LoggedInUserModel loggedInUserModel) throws Exception;

    public ResponseEntity<Object> checkUsersExistsinePGSBySRnumber(String srNumber) throws Exception;

	public List<MasterUsersEntity> getAllInActiveUsersByLocationType(String locationType) throws SQLException;
	
	public ResponseEntity<Map<String, Object>> getEPGSAndMPHUserDetails(List<String> userNames) throws Exception;
	
	public String getZonalCodeForUnitCode(String unitCode) throws Exception;
	
	public int checkIfUnitCodeIsValid(String unitCode) throws Exception;
	
	public int checkIfZonalCodeIsValid(String unitCode) throws Exception;
	
	public List<ZOModel> getZoUnitDetails(String unitCode) throws Exception;
	
	public ResponseEntity<Map<String, Object>> getUserDetailsBasedOnUnit(String userName, String unitCode) throws Exception;
	
	public ResponseEntity<Map<String, Object>> getLoginFlagStatus(String srNumber) throws Exception;
	
	public List<UserDetailsResponse> getUserDetailsForConcurreciaAPIBySrNumber(List<String> srNumbers) throws Exception;
	
	public List<UserDetailsResponse> getUserDetailsForConcurreciaAPIByEmail(List<String> srNumbers) throws Exception;
	
	 public  ResponseEntity<Map<String, Object>> assignRolesToUser(ArrayList<AssignRolesModel> assignRolesModel,String remark) throws Exception;
	 
	 public ResponseEntity<Map<String, Object>> syncUserDetailsWithConcurrecia(String srNumber) throws Exception;
	 
	 public ResponseEntity<Map<String, Object>> checkTdsDetailsForUser(String username) throws Exception;
	 
	 public ResponseEntity<Map<String, Object>> checkBrsAndTokenDetailsForUser(String username) throws Exception;
}

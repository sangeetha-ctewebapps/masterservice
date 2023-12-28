package com.lic.epgs.mst.usermgmt.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.modal.UnitMasterModel;
import com.lic.epgs.mst.modal.UserHistoryInputModel;
import com.lic.epgs.mst.usermgmt.entity.AppModuleRoleTypeEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterAuditDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRolesDisplayRolesMappingEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersHistoryDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersLoginDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.modal.AppRoleTypeModel;
import com.lic.epgs.mst.usermgmt.modal.HistoryDetailsModel;
import com.lic.epgs.mst.usermgmt.modal.LocationModel;
import com.lic.epgs.mst.usermgmt.modal.MasterRoleModal;
import com.lic.epgs.mst.usermgmt.modal.RoleTypeModel;
import com.lic.epgs.mst.usermgmt.modal.UpdateLoginDetailsModel;


public interface UserRoleTypeMappingService {
	
	public ResponseEntity<Map<String, Object>> addUserType(UserRoleTypeMappingEntity userRoleTypeEntity)
			throws Exception;
	
	public ResponseEntity<Map<String, Object>> saveLoginDetails(MasterUsersLoginDetailsEntity masterUsersLoginDetailsEntity)
			throws Exception;
	
	public ResponseEntity<Map<String, Object>> saveActionDetailsForAudit(MasterAuditDetailsEntity masterAuditDetailsEntity)
			throws Exception;
	
	public ResponseEntity<Map<String, Object>> saveHistoryDetails(MasterUsersHistoryDetailsEntity masterUsersHistoryDetailsEntity)
			throws Exception;
	
	public List<HistoryDetailsModel> getHistoryDetails(UserHistoryInputModel userHistoryInputModel);
	
	public ResponseEntity<Map<String, Object>> getLoginDetails(String username)
			throws Exception;
	
	public ResponseEntity<Map<String, Object>> updateLoginDetails(UpdateLoginDetailsModel updateLoginDetailsModel) throws Exception;

	public ResponseEntity<Map<String, Object>> addUserBasedOnUserName(String appmoduleId,String roleTypeId,String masterUserId,String locationType)
			throws Exception;
	public List<MasterRolesDisplayRolesMappingEntity> getUserRoleTypeByAppModuleId(Long appModuleId,String locationType) throws Exception;

	List<AppRoleTypeModel> userRoleTypeSearch(Long masterUserId) throws SQLException;

	List<RoleTypeModel> masterRoleTypeSearch(Long appModuleId, String displayRoleType,String locationType) throws SQLException;

	public ResponseEntity<Map<String, Object>> deleteuserRoleTypeMapping(Long userRoleTypeMappingId);

	public List<MasterRoleModal> checkAdminRoleBySrNo(String email) throws Exception;
	
	public List<MasterRoleModal> checkAdminRoleBySrNoForAccounting(String email) throws Exception;

	public List<LocationModel> checkAdminRoleByEmailForSwalamban(String email);

	List<MasterRoleModal> getRoleTypeByEmail(String Email) throws Exception;
	
	 List<UnitMasterModel> getAllUoForZoLocation(String locationType,String locationCode) throws SQLException;

}

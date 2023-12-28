package com.lic.epgs.mst.usermgmt.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.entity.UnitEntity;
import com.lic.epgs.mst.modal.UnitMasterModel;
import com.lic.epgs.mst.modal.UserHistoryInputModel;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.repository.UnitEntityRepository;
import com.lic.epgs.mst.usermgmt.entity.AppModuleRoleTypeEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterAuditDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRolesDisplayRolesMappingEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersHistoryDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersLoginDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.modal.AppRoleTypeModel;
import com.lic.epgs.mst.usermgmt.modal.HistoryDetailsModel;
import com.lic.epgs.mst.usermgmt.modal.LocationModel;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;
import com.lic.epgs.mst.usermgmt.modal.MasterRoleModal;
import com.lic.epgs.mst.usermgmt.modal.RoleTypeModel;
import com.lic.epgs.mst.usermgmt.modal.UpdateLoginDetailsModel;
import com.lic.epgs.mst.usermgmt.repository.AppModuleRoleTypeRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterAuditDetailsRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterRolesDisplayRolesMappingRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersHistoryDetailsRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersLoginDetailsRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.UserRoleTypeRepository;


@Service
@Transactional
public class UserRoleTypeMappingServiceImpl implements UserRoleTypeMappingService{
	
	private static final Logger logger = LogManager.getLogger(UserRoleTypeMappingServiceImpl.class);
	private static final String className = null;
	@Autowired
	private UserRoleTypeRepository userRoleTypeRepository;
	@Autowired
	private AppModuleRoleTypeRepository appModuleRoleTypeRepository;
	
	
	@Autowired
	private MasterUsersHistoryDetailsRepository masterUsersHistoryDetailsRepository;
	
	//@Autowired
	//private MasterRoleRepository masterRoleRepository;
	
	@Autowired
	MasterRolesDisplayRolesMappingRepository masterRolesDisplayRolesMappingRepository;
	
	@Autowired
	private MasterUsersRepository masterUsersRepository;
	
	@Autowired
	private UnitEntityRepository unitEntityRepository;
	
	
	@Autowired
	MasterUsersLoginDetailsRepository  masterUsersLoginDetailsRepository;
	
	@Autowired
	MasterAuditDetailsRepository  masterAuditDetailsRepository;
	
	@Autowired
	JDBCConnection jdbcConnection;


	/*
	 * Description: This function is used for getting adding the data into User_Role_Type_Mapping Module
	 * Table Name- User_Role_Type_Mapping
	 * Author- Sandeep D
	 */
	@Override
	 public ResponseEntity<Map<String, Object>> addUserType(UserRoleTypeMappingEntity userRoleTypeEntity) throws Exception {
	
		logger.info("Enter UserRoleTypeMapping : add userRoleType ");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		try {
			Date date = new Date();
			if (userRoleTypeEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
			else {
				if(!isValid(userRoleTypeEntity, "ADD")) {
					response.put(Constant.STATUS, 12);
					response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
				}
				if(!isUnique(userRoleTypeEntity, "ADD")) {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
				}
				else  {
					userRoleTypeEntity.setCreatedOn(date);
					userRoleTypeEntity.setModifiedOn(date);
					UserRoleTypeMappingEntity userRoleTypeMapping = userRoleTypeRepository.save(userRoleTypeEntity);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("UserRoleTypeMapId", userRoleTypeMapping.getUserRoleTypeMappingId());
				}
				
			}

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Could not save  userRoleType : " + exception.getMessage());
		}
		return null;
	}
	
	
	

	
	
	private boolean isValid(UserRoleTypeMappingEntity userRoleTypeMappingEntity, String operation) {
	
	if(!(userRoleTypeRepository.findById(userRoleTypeMappingEntity.getMasterUserId()).isPresent())) {
		return false;
	}
	if(operation.equals("EDIT") && (userRoleTypeMappingEntity.getUserRoleTypeMappingId() == 0.00 || userRoleTypeMappingEntity.getMasterUserId() == 0.00)) {
		return false;
	}
	return true;
}
	
	private boolean isUnique(UserRoleTypeMappingEntity userRoleTypeMappingEntity, String operation) throws Exception {
		List<UserRoleTypeMappingEntity> result = userRoleTypeRepository.findDuplicate(userRoleTypeMappingEntity.getMasterUserId(), userRoleTypeMappingEntity.getRoleTypeId(),userRoleTypeMappingEntity.getAppModuleId());
		if(operation.equals("ADD") && !(result.size() > 0)) {
			return true;
		}
		if(operation.equals("EDIT") && !(result.size() > 1)) {
			return true;
		}
		return false;
	}
	
	/*
	 * Description: This function is used for getting data from User_role_type_mapping Module by using appModuleId for identifying the User Role Type presence
	 * Table Name- User_Role_Type_Mapping
	 * Author- Sandeep D
	 */
	public List<MasterRolesDisplayRolesMappingEntity> getUserRoleTypeByAppModuleId(Long appModuleId,String locationType) throws Exception { 
		logger.info("Start getUserRoleTypeByAppModuleId");

		//AppModuleRoleTypeEntity appRoleTypeMapping =new AppModuleRoleTypeEntity() ;

		logger.info("appModuleId--"+appModuleId);

		List<MasterRolesDisplayRolesMappingEntity> objUserRoleTypeMapping = masterRolesDisplayRolesMappingRepository.getUserRoleTypeByappModuleId(appModuleId, locationType);

		

		logger.info("End getUserRoleBySrNumber");

		return objUserRoleTypeMapping;
	}


	

	/*
	 * Description: This function is used for searching the data in User_Role_Type_Mapping Module using the filters like MasterUserId
	 * Table Name- User_Role_Type_Mapping
	 * Author- Sandeep D
	 */

	@Override
	public List<AppRoleTypeModel> userRoleTypeSearch(Long masterUserId) throws SQLException {
		logger.info("Start Service  userRoleTypeSearch");
		List<AppRoleTypeModel> masterUsersList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();
		try {
			
			sqlmu = "SELECT URTM.*,MAM.modulename,amr.roletype_name FROM USER_ROLETYPE_MAPPING URTM ,MASTER_APPLICAITON_MODULE MAM ,MASTER_ROLETYPE AMR WHERE MAM.MODULEID = URTM.appmoduleid AND amr.roletypeid = urtm.roletypeid AND URTM.MASTER_USER_ID = ? order by URTM.CREATEDON desc";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setLong(1, masterUserId);
	
				try(ResultSet rs = preparestatements.executeQuery();)
				{
	
					masterUsersList = new ArrayList<>();
					while (rs.next()) {
						AppRoleTypeModel masterSearch = new AppRoleTypeModel();
						masterSearch.setUserRoleTypeMappingId(rs.getLong(1));
						masterSearch.setAppModuleId(rs.getLong(2));
						masterSearch.setRoleTypeId(rs.getLong(3));
						masterSearch.setMasterUserId(rs.getLong(4));
						masterSearch.setIsActive(rs.getString(5));
						masterSearch.setIsDeleted(rs.getString(6));
						masterSearch.setModifiedBy(rs.getString(7));
						masterSearch.setModifiedOn(rs.getDate(8));
						masterSearch.setCreatedBy(rs.getString(9));
						masterSearch.setCreatedOn(rs.getDate(10));
						masterSearch.setAppModule(rs.getString(11));
						masterSearch.setRoleTypeName(rs.getString(12));
						
						
						
						masterUsersList.add(masterSearch);
					}
		
					logger.info("userRoleTypeSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.info("userRoleTypeSearch exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.info("userRoleTypeSearch exception occured." + e.getMessage());
			}
			return masterUsersList;
		} catch (Exception e) {
			logger.info("userRoleTypeSearch exception occured." + e.getMessage());
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
		return masterUsersList;

	}
	
	/*
	 * Description: This function is used for soft deleting the data in User_Role_Type_Mapping by using primary key
	 * Table Name- User_Role_Type_Mapping
	 * Author- Sandeep D
	 */
	
	@Override
	public ResponseEntity<Map<String, Object>> deleteuserRoleTypeMapping(Long userRoleTypeMappingId) {
	// TODO Auto-generated method stub
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	LoggingUtil.logInfo(className, methodName, "Started");

	try {

	if (userRoleTypeMappingId == null) {
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	} else {
	if (!isValidDeletion(userRoleTypeMappingId)) {
	response.put(Constant.STATUS, 10);
	response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	} else {
	userRoleTypeRepository.findAndDeleteById(userRoleTypeMappingId);
	response.put(Constant.STATUS, 1);
	response.put(Constant.MESSAGE, Constant.SUCCESS);
	response.put("Data", "Deleted User Role TypeMapId : " +userRoleTypeMappingId);

	}
	}
	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	} catch (Exception ex) {
	logger.info("Could not delete userRoleTypeMapId due to : " + ex.getMessage());
	}
	return null;

	}

	private boolean isValidDeletion(Long id) {
	java.util.Optional<UserRoleTypeMappingEntity> result = userRoleTypeRepository.findById(id);
	if (!result.isPresent()) {
	return false;
	}
	if (result.get().getIsDeleted() != null && result.get().getIsDeleted().equals("Y")) {
	return false;
	}
	if (result.get().getIsActive() != null && result.get().getIsActive().equals("N")) {
	return false;
	}

	else {
	return true;
	}
	}



	/*
	 * Description: This function is used for searching the data in Master_role Module using the filters like EMAIL
	 * Table Name- Master_role
	 * Author- Sandeep D
	 */

	@Cacheable(value = "myCache", key = "#srnumber")
	@Override
	public List<MasterRoleModal> checkAdminRoleBySrNo(String srnumber) throws Exception {
		logger.info("Start Service  checkAdminRoleBySrNo");
		List<MasterRoleModal> masterUsersList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
			sqlmu = "select mr.rolename,amr.roletype_name from USER_ROLETYPE_MAPPING urtm, master_users mu,master_role mr,master_ROLETYPE_ROLES_MAPPING mar,MASTER_ROLETYPE amr, MASTER_APPLICAITON_MODULE mam\n"
					+ "where mu.MASTER_USER_ID= urtm.MASTER_USER_ID and urtm.roletypeid = amr.roletypeid and amr.roletypeid = mar.roletypeid  and mar.roleid = mr.roleid and urtm.APPMODULEID = mam.MODULEID and urtm.APPMODULEID = amr.APPMODULEID and amr.APPMODULEID = mar.APP_MODULE_ID \n"
					+ "AND lower(MU.EMAIL)  = lower(?) and mu.IS_ACTIVE= 'Y' and mu.IS_DELETED = 'N'  and urtm.IS_ACTIVE= 'Y' and urtm.IS_DELETED = 'N' ";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setString(1, srnumber);
	
				try(ResultSet rs = preparestatements.executeQuery();)
				{
	
					masterUsersList = new ArrayList<>();
					while (rs.next()) {
						MasterRoleModal masterSearch = new MasterRoleModal();
						masterSearch.setRoleName(rs.getString(1));
						masterSearch.setRoleType(rs.getString(2));
						masterUsersList.add(masterSearch);
					}
		
					logger.info("checkAdminRoleBySrNo executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("checkAdminRoleBySrNo exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("checkAdminRoleBySrNo exception occured." + e.getMessage());
			}
			return masterUsersList;
		} catch (Exception e) {
			logger.error("checkAdminRoleBySrNo exception occured." + e.getMessage());
			return masterUsersList;
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
		
	}
	
	@Cacheable(value = "myCache", key = "#srnumber")
	@Override
	public List<MasterRoleModal> checkAdminRoleBySrNoForAccounting(String srnumber) throws Exception {
		logger.info("Start Service  checkAdminRoleBySrNoForAccounting");
		List<MasterRoleModal> masterUsersList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
			sqlmu = "select mr.rolename,amr.roletype_name from USER_ROLETYPE_MAPPING urtm, master_users mu,master_role mr,master_ROLETYPE_ROLES_MAPPING mar,MASTER_ROLETYPE amr, MASTER_APPLICAITON_MODULE mam\n"
					+ "where mu.MASTER_USER_ID= urtm.MASTER_USER_ID and urtm.roletypeid = amr.roletypeid and amr.roletypeid = mar.roletypeid  and mar.roleid = mr.roleid and urtm.APPMODULEID = mam.MODULEID and urtm.APPMODULEID = amr.APPMODULEID and amr.APPMODULEID = mar.APP_MODULE_ID \n"
					+ "AND lower(MU.EMAIL)  = lower(?) and mu.IS_ACTIVE= 'Y' and mu.IS_DELETED = 'N'  and urtm.IS_ACTIVE= 'Y' and urtm.IS_DELETED = 'N' ";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setString(1, srnumber);
	
				try(ResultSet rs = preparestatements.executeQuery();)
				{
					Map<String,String> roleMap = new HashMap<String,String>();
					while (rs.next()) {						
						roleMap.put(rs.getString(1), rs.getString(2));
					}
	
					masterUsersList = new ArrayList<>();
					
					boolean hasAccountingAccess = false;
					boolean hasTBAccess = false;
					boolean hasbothAccess = false;
					for(Map.Entry<String,String> mapEntry : roleMap.entrySet()) {
						if(mapEntry.getValue().equalsIgnoreCase("TB_Admin")) {
							hasTBAccess = true;
						}
						if(mapEntry.getValue().equalsIgnoreCase("Maker-Acc-Collection") || mapEntry.getValue().equalsIgnoreCase("Checker-Acc-Collection") || mapEntry.getValue().equalsIgnoreCase("Approver-Acc-Collection") || mapEntry.getValue().equalsIgnoreCase("Admin-Acc-Collection")
								|| mapEntry.getValue().equalsIgnoreCase("Maker-Acc") || mapEntry.getValue().equalsIgnoreCase("Checker-Acc") || mapEntry.getValue().equalsIgnoreCase("Approver-Acc") || mapEntry.getValue().equalsIgnoreCase("Cpc-Type-Acc") || mapEntry.getValue().equalsIgnoreCase("Admin-Acc") ) {
							hasAccountingAccess = true;
						}
						if(hasTBAccess && hasAccountingAccess) {
							hasbothAccess = true;
						}
						
					}					
						if(hasbothAccess) {
							String roleTye = null;
							for(Map.Entry<String,String> mapEntry : roleMap.entrySet()) {
								if(!mapEntry.getValue().equalsIgnoreCase("TB_Admin")) {
									MasterRoleModal masterSearch = new MasterRoleModal();
									masterSearch.setRoleName(mapEntry.getKey());
									masterSearch.setRoleType(mapEntry.getValue());
									if(mapEntry.getValue().equalsIgnoreCase("Maker-Acc-Collection") || mapEntry.getValue().equalsIgnoreCase("Checker-Acc-Collection") || mapEntry.getValue().equalsIgnoreCase("Approver-Acc-Collection") || mapEntry.getValue().equalsIgnoreCase("Admin-Acc-Collection")
								|| mapEntry.getValue().equalsIgnoreCase("Maker-Acc") || mapEntry.getValue().equalsIgnoreCase("Checker-Acc") || mapEntry.getValue().equalsIgnoreCase("Approver-Acc") || mapEntry.getValue().equalsIgnoreCase("Cpc-Type-Acc") || mapEntry.getValue().equalsIgnoreCase("Admin-Acc")) {
										roleTye = mapEntry.getValue(); 
									}
									masterUsersList.add(masterSearch);
								}
							}
							MasterRoleModal masterSearch = new MasterRoleModal();
							masterSearch.setRoleName("TB_Admin_View");
							masterSearch.setRoleType(roleTye);
							masterUsersList.add(masterSearch);
						}else {
							for(Map.Entry<String,String> mapEntry : roleMap.entrySet()) {
								MasterRoleModal masterSearch = new MasterRoleModal();
								masterSearch.setRoleName(mapEntry.getKey());
								masterSearch.setRoleType(mapEntry.getValue());
								masterUsersList.add(masterSearch);
							}
						}
					logger.info("checkAdminRoleBySrNoForAccounting executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("checkAdminRoleBySrNoForAccounting exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("checkAdminRoleBySrNoForAccounting exception occured." + e.getMessage());
			}
			return masterUsersList;
		} catch (Exception e) {
			logger.error("checkAdminRoleBySrNoForAccounting exception occured." + e.getMessage());
			return masterUsersList;
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
		
	}
	
	
	@Override
	public List<MasterRoleModal> getRoleTypeByEmail(String Email) throws Exception {
		logger.info("Start Service  getRoleTypeByEmail");
		List<MasterRoleModal> masterUsersList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
			sqlmu = "  select mr.rolename,amr.roletype_name from USER_ROLETYPE_MAPPING urtm, master_users mu,master_role mr,master_ROLETYPE_ROLES_MAPPING mar,MASTER_ROLETYPE amr, MASTER_APPLICAITON_MODULE mam\r\n" + 
					"  where mu.MASTER_USER_ID= urtm.MASTER_USER_ID and urtm.roletypeid = amr.roletypeid and amr.roletypeid = mar.roletypeid  and mar.roleid = mr.roleid and urtm.APPMODULEID = mam.MODULEID and urtm.APPMODULEID = amr.APPMODULEID and amr.APPMODULEID = mar.APP_MODULE_ID  \r\n" + 
					"  AND lower(MU.EMAIL)  = lower(?) and mu.IS_ACTIVE= 'Y' and mu.IS_DELETED = 'N'  and urtm.IS_ACTIVE= 'Y' and urtm.IS_DELETED = 'N' ";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setString(1, Email);
	
				try(ResultSet rs = preparestatements.executeQuery();)
				{
	
					masterUsersList = new ArrayList<>();
					while (rs.next()) {
						MasterRoleModal masterSearch = new MasterRoleModal();
						masterSearch.setRoleName(rs.getString(1));
						masterSearch.setRoleType(rs.getString(2));
						masterUsersList.add(masterSearch);
					}
		
					logger.info("getRoleTypeByEmail executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getRoleTypeByEmail exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getRoleTypeByEmail exception occured." + e.getMessage());
			}
			return masterUsersList;
		} catch (Exception e) {
			logger.error("getRoleTypeByEmail exception occured." + e.getMessage());
			return masterUsersList;
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
		
	}

	

	/*
	 * Description: This function is used for searching the data in Master_role and App_Module_RoleType Module using the filters like appModuleId,roleTypeId
	 * Table Name- Master_role,App_Module_RoleType
	 * Author- Sandeep D
	 */


	@Override
	public List<RoleTypeModel> masterRoleTypeSearch(Long appModuleId, String displayRoleType,String locationType) throws SQLException {
		logger.info("Start Service  masterRoleTypeSearch");
		logger.info("appModuleId:"+appModuleId+"roleTypeId::"+displayRoleType);
		List<RoleTypeModel> masterUsersList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();
		try {
			
			
			 if ((appModuleId!=null || !(appModuleId+"").equalsIgnoreCase("")) && (locationType!=null || !(locationType+"").equalsIgnoreCase("")))
			{
			sqlmu = " SELECT MR.*,amr.APP_MODULE_ID,amr.APP_MODULE_NAME,amr.ROLE_TYPE_ID,amr.role_type_name FROM MASTER_ROLE mr ,master_roles_displayroles_mapping AMR,MASTER_ROLETYPE_ROLES_MAPPING MAR WHERE \n" + 
					"             amr.APP_MODULE_ID = mar.APP_MODULE_ID and mar.ROLEID = mr.ROLEID\n" + 
					"			 and amr.ROLE_TYPE_ID = mar.ROLETYPEID and amr.APP_MODULE_ID = ? and(User_type is null or User_type = ?)";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setLong(1, appModuleId);
				preparestatements.setString(2, locationType);
				try(ResultSet rs = preparestatements.executeQuery();)
				{
					masterUsersList = new ArrayList<>();
						while (rs.next()) {
							RoleTypeModel masterSearch = new RoleTypeModel();
							masterSearch.setRoleId(rs.getLong("ROLEID"));
							masterSearch.setAppModule(rs.getString("APP_MODULE"));
							masterSearch.setRoleName(rs.getString("ROLENAME"));
							masterSearch.setRoleType(rs.getString("ROLE_TYPE"));
							masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
							masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
							masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
							masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
							masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
							masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
							masterSearch.setAppModuleId(rs.getLong("APP_MODULE_ID"));
							masterSearch.setModuleName(rs.getString("APP_MODULE_NAME"));
							masterSearch.setRoleTypeId(rs.getLong("ROLE_TYPE_ID"));
			                masterSearch.setRoleTypeName(rs.getString("ROLE_TYPE_NAME"));
							masterUsersList.add(masterSearch);
						}
						logger.info("masterRoleTypeSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("masterRoleTypeSearch exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("masterRoleTypeSearch exception occured." + e.getMessage());
			}
				return masterUsersList;
			}
			else if ((appModuleId!=null || !(appModuleId+"").equalsIgnoreCase("")) && (locationType!=null || !(locationType+"").equalsIgnoreCase("")) && (displayRoleType!=null || !(displayRoleType+"").equalsIgnoreCase("")))
			{
			sqlmu = " SELECT MR.*,amr.APP_MODULE_ID,amr.APP_MODULE_NAME,amr.ROLE_TYPE_ID,amr.role_type_name FROM MASTER_ROLE mr ,master_roles_displayroles_mapping AMR,MASTER_ROLETYPE_ROLES_MAPPING MAR WHERE \n" + 
					"             amr.APP_MODULE_ID = mar.APP_MODULE_ID and mar.ROLEID = mr.ROLEID\n" + 
					"			 and amr.ROLE_TYPE_ID = mar.ROLETYPEID and amr.APP_MODULE_ID = ? and(User_type is null or User_type = ?) and amr.display_role_type_name = ?";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setLong(1, appModuleId);
				preparestatements.setString(2, locationType);
				preparestatements.setString(3, displayRoleType);
				try(ResultSet rs = preparestatements.executeQuery();)
				{
					masterUsersList = new ArrayList<>();
						while (rs.next()) {
							RoleTypeModel masterSearch = new RoleTypeModel();
							masterSearch.setRoleId(rs.getLong("ROLEID"));
							masterSearch.setAppModule(rs.getString("APP_MODULE"));
							masterSearch.setRoleName(rs.getString("ROLENAME"));
							masterSearch.setRoleType(rs.getString("ROLE_TYPE"));
							masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
							masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
							masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
							masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
							masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
							masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
							masterSearch.setAppModuleId(rs.getLong("APP_MODULE_ID"));
							masterSearch.setModuleName(rs.getString("APP_MODULE_NAME"));
							masterSearch.setRoleTypeId(rs.getLong("ROLE_TYPE_ID"));
			                masterSearch.setRoleTypeName(rs.getString("ROLE_TYPE_NAME"));
							masterUsersList.add(masterSearch);
						}
						logger.info("masterRoleTypeSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("masterRoleTypeSearch exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("masterRoleTypeSearch exception occured." + e.getMessage());
			}
				return masterUsersList;
			}
			if( appModuleId!=null)
			{
			sqlmu = " SELECT MR.*,amr.APP_MODULE_ID,amr.APP_MODULE_NAME,amr.ROLE_TYPE_ID,amr.role_type_name FROM MASTER_ROLE mr ,master_roles_displayroles_mapping AMR,MASTER_ROLETYPE_ROLES_MAPPING MAR WHERE \n" + 
					"             amr.APP_MODULE_ID = mar.APP_MODULE_ID and mar.ROLEID = mr.ROLEID\n" + 
					"			 and amr.ROLE_TYPE_ID = mar.ROLETYPEID and amr.APP_MODULE_ID = ? ";
			
			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setLong(1, appModuleId);
				//preparestatements.setString(2, locationType);
				try(ResultSet rs = preparestatements.executeQuery();)
				{
					masterUsersList = new ArrayList<>();
						while (rs.next()) {
							RoleTypeModel masterSearch = new RoleTypeModel();
							masterSearch.setRoleId(rs.getLong("ROLEID"));
							masterSearch.setAppModule(rs.getString("APP_MODULE"));
							masterSearch.setRoleName(rs.getString("ROLENAME"));
							masterSearch.setRoleType(rs.getString("ROLE_TYPE"));
							masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
							masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
							masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
							masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
							masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
							masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
							masterSearch.setAppModuleId(rs.getLong("APP_MODULE_ID"));
							masterSearch.setModuleName(rs.getString("APP_MODULE_NAME"));
							masterSearch.setRoleTypeId(rs.getLong("ROLE_TYPE_ID"));
			                masterSearch.setRoleTypeName(rs.getString("ROLE_TYPE_NAME"));
							masterUsersList.add(masterSearch);
						}
						logger.info("masterRoleTypeSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("masterRoleTypeSearch exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("masterRoleTypeSearch exception occured." + e.getMessage());
			}
				return masterUsersList;
			} 
			
			
			else {
				logger.info("masterRoleTypeSearch does not executed successfully.");
				return masterUsersList;
			}
			
			
		
			
		} catch (Exception e) {
			logger.error("masterRoleTypeSearch exception occured." + e.getMessage());
			return masterUsersList;
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
		
	}

	/*
	 * Description: This function is used for getting data from MASTER Role Module by using email for identifying the admin  role presence
	 * Table Name- MASTER_Role
	 * Author- Sandeep D
	 */


	@Override
	public List<LocationModel> checkAdminRoleByEmailForSwalamban(String email) {
	
	List<MasterUsersEntity> masterUserEntity = masterUsersRepository.checkAdminroleByEmail(email);
	List<LocationModel> modalList = new ArrayList();

	logger.info("End Service checkAdminRoleBySrNo");
	String sawlabanValue="";
	for(int i=0 ; i <masterUserEntity.size();i ++) {
		LocationModel  roleModal= new LocationModel();
		roleModal.setLocationType(masterUserEntity.get(i).getLocationType());
		roleModal.setLocation(masterUserEntity.get(i).getLocation());
	if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("UO"))
			sawlabanValue="Unit";
		if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("ZO"))
			sawlabanValue="Zonal";
	if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("CO"))
			sawlabanValue="COO";
		 roleModal.setSwalambanMenu(sawlabanValue);
		modalList.add(roleModal);
	}


	return modalList;
}



	





@Override public List<UnitMasterModel> getAllUoForZoLocation(String locationType,String locationCode) {

logger.info("Start Service  getAllUoForZoLocation");
List<UnitEntity> masterUnitEntity = unitEntityRepository.getAllUoForZoLocation(locationCode);
List<UnitMasterModel> modalList = new ArrayList();

for(int i=0 ; i <masterUnitEntity.size();i ++) 
{ 
	  UnitMasterModel unitModal= new UnitMasterModel();  
	  unitModal.setUnitId(masterUnitEntity.get(i).getUnitId());
	  unitModal.setUnitCode(masterUnitEntity.get(i).getUnitCode());
	  unitModal.setCityName(masterUnitEntity.get(i).getDescription());
	  unitModal.setIsActive(masterUnitEntity.get(i).getIsActive());
	  unitModal.setIsDeleted(masterUnitEntity.get(i).getIsDeleted());
	  unitModal.setZonalId(masterUnitEntity.get(i).getZonalId());
	  modalList.add(unitModal); 
}
logger.info("End Service getAllUoForZoLocation");
return modalList; 
}



@Override
public ResponseEntity<Map<String, Object>> addUserBasedOnUserName(String appmoduleId,String roleTypeId,String masterUserId,String locationType ) throws Exception {
	logger.info("Enter UserRoleTypeMapping : add userRoleType ");
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	 List<UserRoleTypeMappingEntity> objClaimReserveDetailEntityList = new ArrayList<>();
	 UserRoleTypeMappingEntity userRoleMapping = new UserRoleTypeMappingEntity();
	try {
		
		/*if (appmoduleId !=null & roleTypeId!= null & masterUserId!=null & locationType!=null) {
			
			List<MasterUsersEntity> addUserList = masterUsersRepository.getAllActiveLocationType(locationType);
			 for(int i=0;i<addUserList.size();i++) {
				 
				 int userRoleEntity = userRoleTypeRepository.duplicateUserRoleCheck(addUserList.get(i).getMasterUserId(),Long.parseLong(appmoduleId),Long.parseLong(roleTypeId));
			 }
			 
			
		}*/
		 
		
	List<MasterUsersEntity> addUserList = masterUsersRepository.getAllActiveLocationType(locationType);
		
	  for(int i=0;i<addUserList.size();i++) {
		  logger.info(" addUserBasedOnUserName executed successfully.");
		  UserRoleTypeMappingEntity userRoleMappingEntity = new UserRoleTypeMappingEntity();
		  Date today = new Date();
		  userRoleMappingEntity.setUserRoleTypeMappingId(userRoleMapping.getUserRoleTypeMappingId());
		  userRoleMappingEntity.setAppModuleId(Long.parseLong(appmoduleId));
		  userRoleMappingEntity.setRoleTypeId(Long.parseLong(roleTypeId));
		  userRoleMappingEntity.setMasterUserId(addUserList.get(i).getMasterUserId());
		  userRoleMappingEntity.setIsActive("Y");
		  userRoleMappingEntity.setIsDeleted("N");
		  userRoleMappingEntity.setCreatedBy("sysAdmin");
		  userRoleMappingEntity.setCreatedOn(today);
		  userRoleMappingEntity.setModifiedBy("sysAdmin");
		  userRoleMappingEntity.setModifiedOn(today);
		  objClaimReserveDetailEntityList.add(userRoleMappingEntity); 
		  
	  }
	  
	  userRoleTypeRepository.saveAll(objClaimReserveDetailEntityList);
	  logger.info("Roles Assigned  Successfully");
	  response.put(Constant.STATUS, 200);
		response.put(Constant.MESSAGE,"Roles Assigned to "+objClaimReserveDetailEntityList.size()+ " Users Sucessfully");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	
	}catch (Exception exception) {
		logger.info("Could not save  userRoleType : " + exception.getMessage());
	}
	return null;
}






@Override
public ResponseEntity<Map<String, Object>> saveLoginDetails(MasterUsersLoginDetailsEntity masterUsersLoginDetailsEntity) throws Exception {
	logger.info("Enter UserRoleTypeMapping : add userRoleType ");
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

	try {
		Date date = new Date();
		if (masterUsersLoginDetailsEntity == null) {
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		} 
			else  {
				masterUsersLoginDetailsEntity.setCreatedOn(date);
				masterUsersLoginDetailsEntity.setModifiedOn(date);
				masterUsersLoginDetailsEntity.setLoggedINTime(date);
				//masterUsersLoginDetailsEntity.setLoggedOutTime(date);
				MasterUsersLoginDetailsEntity masterUsersLoginDetails = masterUsersLoginDetailsRepository.save(masterUsersLoginDetailsEntity);
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("LOGINID", masterUsersLoginDetailsEntity.getLoginId());
			}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	} catch (Exception exception) {
		logger.info("Could not save  userRoleType : " + exception.getMessage());
	}
	return null;
}






@Override
public ResponseEntity<Map<String, Object>> updateLoginDetails(UpdateLoginDetailsModel updateLoginDetailsModel) throws Exception {
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	LoggingUtil.logInfo(className, methodName, "Started");

	try {

		if (updateLoginDetailsModel == null) {
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		} else {
		
				Date date = new Date();
				MasterUsersLoginDetailsEntity existingMasterUser = masterUsersLoginDetailsRepository.getLoginDetails(updateLoginDetailsModel.getLoginId());
				existingMasterUser.setLoginId(updateLoginDetailsModel.getLoginId());
				existingMasterUser.setUserName(updateLoginDetailsModel.getUserName());
				existingMasterUser.setLoggedOutTime(date);
				
				masterUsersLoginDetailsRepository.save(existingMasterUser);

				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", "Updated Login Details  for: " + updateLoginDetailsModel.getLoginId());
			}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	} catch (Exception ex) {
		logger.info("Could not update Master User  due to : " + ex.getMessage());
	}
	return null;
}






@Override
public ResponseEntity<Map<String, Object>> getLoginDetails(String username) throws Exception {
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	logger.info("Enter UserManagementService :  " + methodName);

	try {

		List<MasterUsersLoginDetailsEntity> masterUsersDetailList = masterUsersLoginDetailsRepository.getLoginUserDetails(username);

		if (masterUsersDetailList.isEmpty()) {
			response.put(Constant.STATUS, 10);
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		} else {
			response.put(Constant.STATUS, 1);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", masterUsersDetailList);

		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	} catch (Exception exception) {
		logger.info("Could not get Master User data : " + exception.getMessage());
	}
	return null;
}






@Override
public ResponseEntity<Map<String, Object>> saveActionDetailsForAudit(MasterAuditDetailsEntity masterAuditDetailsEntity)
		throws Exception {
	logger.info("Enter UserRoleTypeMapping : add userRoleType ");
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

	try {
		Date date = new Date();
		if (masterAuditDetailsEntity == null) {
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		} 
			else  {
				masterAuditDetailsEntity.setCreatedOn(date);
				masterAuditDetailsEntity.setModifiedOn(date);
				MasterAuditDetailsEntity masterAuditDetails = masterAuditDetailsRepository.save(masterAuditDetailsEntity);
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("AUDITID", masterAuditDetailsEntity.getAuditId());
			}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	} catch (Exception exception) {
		logger.info("Could not save  userRoleType : " + exception.getMessage());
	}
	return null;
}






@Override
public ResponseEntity<Map<String, Object>> saveHistoryDetails(
		MasterUsersHistoryDetailsEntity masterUsersHistoryDetailsEntity) throws Exception {
	logger.info("Enter UserRoleTypeMapping : add userRoleType ");
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

	try {
		Date date = new Date();
		if (masterUsersHistoryDetailsEntity == null) {
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		} 
			else  {
				masterUsersHistoryDetailsEntity.setCreatedOn(date);
				MasterUsersHistoryDetailsEntity masterHistoryDetails = masterUsersHistoryDetailsRepository.save(masterUsersHistoryDetailsEntity);
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("HISTORYID",masterUsersHistoryDetailsEntity.getHistoryId());
			}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	} catch (Exception exception) {
		logger.info("Could not save  userRoleType : " + exception.getMessage());
	}
	return null;
}






@Override
public List<HistoryDetailsModel> getHistoryDetails(UserHistoryInputModel userHistoryInputModel) {
	logger.info("Start Service  getHistoryDetails");
	List<HistoryDetailsModel> modalList = new ArrayList();
	try {
		List<String>locationCriteria = new ArrayList();;
		List<MasterUsersHistoryDetailsEntity> masterRoleEntity = new ArrayList();
		
		MasterUsersEntity objMasterUsersEntity = masterUsersRepository.getUserByUserName(userHistoryInputModel.getLoggedInUser());
		
		if(userHistoryInputModel.getLocationType() == null) {
		if(objMasterUsersEntity.getLocationType().equalsIgnoreCase("CO")) {
			locationCriteria.add("CO");
			locationCriteria.add("ZO");
			locationCriteria.add("UO");
		}else if(objMasterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
			locationCriteria.add("ZO");
			locationCriteria.add("UO");
		}else if(objMasterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
			locationCriteria.add("UO");
		}
      }else {
    	  locationCriteria.add(userHistoryInputModel.getLocationType());
      }
		
		if(userHistoryInputModel.getFromDate() == null && userHistoryInputModel.getToDate() != null) {
			
			List<MasterUsersHistoryDetailsEntity> MasterUsersHistoryDetailsEntities = masterUsersHistoryDetailsRepository.getAllHistoryDetails();
			MasterUsersHistoryDetailsEntity masterUsersHistoryDetailsEntity = MasterUsersHistoryDetailsEntities.get(MasterUsersHistoryDetailsEntities.size()-1);
			/*LocalDate dateObj = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String date = dateObj.format(formatter);*/
			Date dmyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(String.valueOf(masterUsersHistoryDetailsEntity.getCreatedOn()));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			String date = dateFormat.format(dmyFormat);
			userHistoryInputModel.setFromDate(date);
		}else if(userHistoryInputModel.getFromDate() != null && userHistoryInputModel.getToDate() == null) {
			LocalDate dateObj = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String date = dateObj.format(formatter);
			userHistoryInputModel.setToDate(date);
		}
		if(userHistoryInputModel.getAdminSrNumber() != null && userHistoryInputModel.getFromDate() == null && userHistoryInputModel.getToDate() == null) {
			 masterRoleEntity = masterUsersHistoryDetailsRepository.getAllHistoryDetailsBasedOnLoggedInUserLocationTypeAndAdminSrNo(locationCriteria, userHistoryInputModel.getAdminSrNumber());
		}else if((userHistoryInputModel.getFromDate() != null && userHistoryInputModel.getToDate() != null) || (userHistoryInputModel.getFromDate() != null && userHistoryInputModel.getToDate() == null) ||   (userHistoryInputModel.getFromDate() == null && userHistoryInputModel.getToDate() != null)) {
			 masterRoleEntity = masterUsersHistoryDetailsRepository.getAllHistoryDetailsBasedOnLoggedInUserLocationTypeAndAdminSrNoAndDate(locationCriteria,userHistoryInputModel.getAdminSrNumber(),userHistoryInputModel.getFromDate(), userHistoryInputModel.getToDate());
		}else if((userHistoryInputModel.getLocationCode() !=null || userHistoryInputModel.getLocationType() !=null) ) {
			 masterRoleEntity = masterUsersHistoryDetailsRepository.getAllHistoryDetailsBasedOnLocationCode(locationCriteria,userHistoryInputModel.getLocationCode());
		}else{
			 masterRoleEntity = masterUsersHistoryDetailsRepository.getAllHistoryDetailsBasedOnLoggedInUserLocationType(locationCriteria);
		}
			
		
	
		for(int i=0 ; i <masterRoleEntity.size();i ++) {
			
			HistoryDetailsModel  historyModal= new HistoryDetailsModel();
			historyModal.setAdminUserSrnumber(masterRoleEntity.get(i).getAdminUserSrnumber());
			historyModal.setAdminUserLocationCode(masterRoleEntity.get(i).getAdminUserLocationCode());
			historyModal.setUserSrnumber(masterRoleEntity.get(i).getUserSrnumber());	
			historyModal.setUserLocationCode(masterRoleEntity.get(i).getUserLocationCode());
			historyModal.setAdminUserActivityPerformed(masterRoleEntity.get(i).getAdminUserActivityPerformed());
			historyModal.setUserModule(masterRoleEntity.get(i).getUserModule());
			historyModal.setIsUserAdmin(masterRoleEntity.get(i).getIsUserAdmin());
			historyModal.setIsUserMarketingNonMarketing(masterRoleEntity.get(i).getIsUserMarketingNonMarketing());
			historyModal.setUserOldRoles(masterRoleEntity.get(i).getUserOldRoles());
			historyModal.setUserNewRoles(masterRoleEntity.get(i).getUserNewRoles());
			Date dmyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(String.valueOf(masterRoleEntity.get(i).getCreatedOn()));
			SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f2.format(dmyFormat));  
			historyModal.setCreatedOn(date1);
			modalList.add(historyModal);
		}
		logger.info("End Service getHistoryDetails");
	}catch(Exception e) {
		e.printStackTrace();
	}

	return modalList;
	
}

}




	
	
	


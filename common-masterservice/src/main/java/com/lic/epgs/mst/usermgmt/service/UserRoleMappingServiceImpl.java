package com.lic.epgs.mst.usermgmt.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.usermgmt.controller.UserRoleMappingController;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.ModuleDescriptionRoleTypeEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;
import com.lic.epgs.mst.usermgmt.modal.LocationModel;
import com.lic.epgs.mst.usermgmt.modal.MandhanLocationModel;
import com.lic.epgs.mst.usermgmt.modal.MasterRoleModal;
import com.lic.epgs.mst.usermgmt.modal.UoZoAdminModel;
import com.lic.epgs.mst.usermgmt.repository.MasterRoleRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.ModuleDescriptionRoleTypeRepository;
import com.lic.epgs.mst.usermgmt.repository.PortalMasterRepository;
import com.lic.epgs.mst.usermgmt.repository.UserRoleMappingRepository;

@Service
@Transactional
public class UserRoleMappingServiceImpl implements UserRoleMappingService {

	@Autowired
	private UserRoleMappingRepository userRoleMappingRepository;

	@Autowired
	private MasterUsersRepository masterUsersRepository;

	@Autowired
	private JDBCConnection jdbcConnection;

	@Autowired
	private MasterRoleRepository masterRoleRepository;
	
	@Autowired
	private PortalMasterRepository portalMasterRepository;
	
	
	@Autowired
	ModuleDescriptionRoleTypeRepository  moduleDescriptionRoleTypeRepository;
	


	 String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(UserRoleMappingController.class);

	
	/*
	 * Description: This function is used for getting data from User_Role_Mapping Module for identifying the user role presence
	 * Table Name- User_Role_Mapping
	 * Author- Sandeep D
	 */
	@Override
	 public List<UserRoleMappingEntity> getAllUserRole() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get UserRole list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return userRoleMappingRepository.getAllUserRole();
	}
	
	
	/*
	 * Description: This function is used for getting data from User_Role_Mapping Module by using masterUserId for identifying the admin role presence
	 * Table Name- User_Role_Mapping
	 * Author- Sandeep D
	 */

	public UserRoleMappingEntity getUserRoleByMasterUserId(long masterUserId) throws UserRoleMappingException {
		logger.info("Start getUserRoleByMasterUserId");

		UserRoleMappingEntity userRoleMapping =new UserRoleMappingEntity() ;

		logger.info("masterUserId--"+masterUserId);

		UserRoleMappingEntity objUserRoleMapping = userRoleMappingRepository.getUserRoleByMasterUserId(masterUserId);

		if(objUserRoleMapping == null)
			userRoleMapping.getUserRoleMappingId();
		else {
			logger.info("objUserRoleMapping::"+userRoleMapping.getUserRoleMappingId());
			userRoleMapping.getUserRoleMappingId();
		}

		logger.info("End getUserRoleByMasterUserId");

		return userRoleMapping;
	}


	
	/*
	 * Description: This function is used for getting data from User_Role_Mapping Module by using srnumber for identifying the admin role presence
	 * Table Name- User_Role_Mapping
	 * Author- Sandeep D
	 */
	public UserRoleMappingEntity getUserRoleBySrNumber(long srNumber) throws UserRoleMappingException { 
		logger.info("Start getUserRoleBySrNumber");

		UserRoleMappingEntity userRoleMapping =new UserRoleMappingEntity() ;

		logger.info("srNumber--"+srNumber);

		UserRoleMappingEntity objUserRoleMapping = userRoleMappingRepository.findUserRoleBySrNumber(srNumber);

		if(objUserRoleMapping == null)
			userRoleMapping.getUserRoleMappingId();
		else {
			logger.info("objUserRoleMapping::"+userRoleMapping.getUserRoleMappingId());
			userRoleMapping.getUserRoleMappingId();
		}

		logger.info("End getUserRoleBySrNumber");

		return userRoleMapping;
	}

	/*
	 * Description: This function is used for searching the data in Zo Admin Module using the filters like LOCATION, SRNUMBER, USERNAME
	 * Table Name- ZO_ADMIN
	 * Author- Sandeep D
	 */
	//ZO Search
	@Override
	public List<UoZoAdminModel> zoSearch(String location, String srNumber, String userName) throws SQLException {
		logger.info("Start Service  zoSearch");
		List<UoZoAdminModel> adminModelList2 = null;
		String sqlzo = null;
		Connection connection1 = jdbcConnection.getConnection();
		try {
			
			sqlzo = "SELECT ZO.LOCATION_CODE, ZO.LOCATION, MU.SRNUMBER,MU.SRNUMBER_MAIN, MU.DESIGNATION, MU.USERNAME,\r\n" + 
					"	ZO.MODIFIED_BY, ZO.MODIFIED_ON, ZO.ZO_ADMIN_ID, MU.MASTER_USER_ID , ZO.IS_DELETED ,ZO.IS_ACTIVE FROM MASTER_USERS MU INNER JOIN ZO_ADMIN ZO  \r\n" + 
					"   ON MU.MASTER_USER_ID = ZO.MASTER_USER_ID \r\n" + 
					"	 WHERE ((UPPER(ZO.LOCATION) LIKE UPPER('%'||?||'%')) OR (? =''))\r\n" + 
					"    AND ((UPPER(MU.SRNUMBER_MAIN) LIKE UPPER('%'||?||'%') ) OR (? =''))\r\n" + 
					"	AND ((UPPER(MU.USERNAME) LIKE UPPER('%'||?||'%') ) OR (? =NULL)) \r\n" + 
					"    order by zo.modified_on desc";

			try(PreparedStatement preparestatements2 = connection1.prepareStatement(sqlzo);)
			{
				preparestatements2.setString(1, location);
				preparestatements2.setString(2, location);
				preparestatements2.setString(3, srNumber);
				preparestatements2.setString(4, srNumber);
				preparestatements2.setString(5, userName);
				preparestatements2.setString(6, userName);
	
				try(ResultSet rs2 = preparestatements2.executeQuery();)
				{
	
					adminModelList2 = new ArrayList<>();
					while (rs2.next()) {
						//Date today = new Date();
						UoZoAdminModel adminModel2 = new UoZoAdminModel();
						adminModel2.setLocationCode(rs2.getString(1));
						adminModel2.setLocation(rs2.getString(2));
						adminModel2.setSrNumber(rs2.getString(3));
						adminModel2.setSrNumber2(rs2.getString(4));
						adminModel2.setCadre(rs2.getString(5));
						adminModel2.setUserName(rs2.getString(6));  
						adminModel2.setModifiedBy(rs2.getString(7));
						adminModel2.setModifiedOn(rs2.getDate(8));
						adminModel2.setAdminId(rs2.getLong(9));
						adminModel2.setMasterUserId(rs2.getLong(10));
						adminModel2.setIsDeleted(rs2.getString(11));
						adminModel2.setIsActive(rs2.getString(12));
						adminModelList2.add(adminModel2);
					}
		
					logger.info("zoSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("get Zo Search Exception occured : " + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("get Zo Search Exception occured : " + e.getMessage());
			}
			return adminModelList2;
		} catch (Exception e) {
			logger.error("get Zo Search Exception occured : " + e.getMessage());
			return adminModelList2;
		} finally {
			if(!connection1.isClosed()) {
				connection1.close();
			}
		}
	}
	
	/*
	 * Description: This function is used for searching the data in Uo Admin Module using the filters like LOCATION, SRNUMBER, USERNAME
	 * Table Name- UO_ADMIN
	 * Author- Sandeep D
	 */

	//UO Search
	@Override
	public List<UoZoAdminModel> uoSearch(String locationType,String location, String srNumber, String userName) throws SQLException {
		logger.info("Start Service  uoSearch");
		List<UoZoAdminModel> adminModelList = null;
		String sql = null;
		String sql1 = null;

		Connection connection = jdbcConnection.getConnection();
		try {
			
			sql = "  SELECT UO.LOCATION_TYPE, UO.LOCATION_CODE, UO.LOCATION, MU.SRNUMBER,MU.SRNUMBER_MAIN, MU.DESIGNATION, MU.USERNAME,\r\n" + 
					"					 UO.MODIFIED_BY, UO.MODIFIED_ON, UO.UO_ADMIN_ID, MU.MASTER_USER_ID , UO.IS_DELETED,UO.IS_ACTIVE FROM\r\n" + 
					"					 MASTER_USERS MU INNER JOIN UO_ADMIN UO \r\n" + 
					"					 ON MU.MASTER_USER_ID = UO.MASTER_USER_ID\r\n" + 
					"					 WHERE ((UPPER(UO.LOCATION) LIKE UPPER('%'||?||'%')) OR (? =''))\r\n" + 
					"					 AND ((UPPER(UO.LOCATION_TYPE) LIKE UPPER('%'||?||'%')) OR (? =''))\r\n" + 
					"					AND ((UPPER(MU.SRNUMBER_MAIN) LIKE UPPER('%'||?||'%') ) OR (? =''))\r\n" + 
					"					 AND ((UPPER(MU.USERNAME) LIKE UPPER('%'||?||'%') ) OR (? =NULL))\r\n" + 
					"					order by uo.modified_on  desc";

			try(PreparedStatement preparestatements = connection.prepareStatement(sql);)
			{
				preparestatements.setString(1, location);
				preparestatements.setString(2, location);
				preparestatements.setString(3, locationType);
	            preparestatements.setString(4, locationType);
			    preparestatements.setString(5, srNumber);
				preparestatements.setString(6, srNumber);
				preparestatements.setString(7, userName);
				preparestatements.setString(8, userName);
	
				try(ResultSet rs = preparestatements.executeQuery();)
				{
	
					adminModelList = new ArrayList<>();
					while (rs.next()) {
						UoZoAdminModel adminModel = new UoZoAdminModel();
						adminModel.setLocationType(rs.getString(1));
						adminModel.setLocationCode(rs.getString(2));
						adminModel.setLocation(rs.getString(3));
						adminModel.setSrNumber(rs.getString(4));
						adminModel.setSrNumber2(rs.getString(5));
						adminModel.setCadre(rs.getString(6));
						adminModel.setUserName(rs.getString(7));  
						adminModel.setModifiedBy(rs.getString(8));
						adminModel.setModifiedOn(rs.getDate(9));
						adminModel.setAdminId(rs.getLong(10));
						adminModel.setMasterUserId(rs.getLong(11));
						adminModel.setIsDeleted(rs.getString(12));
						adminModel.setIsActive(rs.getString(13));
						adminModelList.add(adminModel);
					}
		
					logger.info("uoSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("get Uo Search exception occuredException : " + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("get Uo Search exception occuredException : " + e.getMessage());
			}
			return adminModelList;
		} catch (Exception e) {
			logger.error("get Uo Search exception occuredException : " + e.getMessage());
			return adminModelList;
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
	}
	
	/*
	 * Description: This function is used for getting data from MASTER Role Module by using srnumber for identifying the admin role presence
	 * Table Name- MASTER_ROLE
	 * Author- Sandeep D
	 */

	@Override
	public List<MasterRoleModal> checkAdminRoleBySrNo( String srNumber) {

		logger.info("Start Service  checkAdminRoleBySrNo");

		List<MasterRoleEntity> masterRoleEntity = masterRoleRepository.checkAdminroleBySrNo(srNumber);
		List<MasterRoleModal> modalList = new ArrayList();

		logger.info("End Service checkAdminRoleBySrNo");

		for(int i=0 ; i <masterRoleEntity.size();i ++) {
			MasterRoleModal  roleModal= new MasterRoleModal();
			roleModal.setRoleName(masterRoleEntity.get(i).getRoleName());
			roleModal.setRoleType(masterRoleEntity.get(i).getRoleType());
			modalList.add(roleModal);
		}


		return modalList;
	}
	
	/*
	 * Description: This function is used for getting data from MASTER Role Module by using srnumber for identifying the admin role presence
	 * Table Name- MASTER_ROLE
	 * Author- Sandeep D
	 */

	@Override
	public List<MasterRoleModal> checkAdminRoleByEmail(String email) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();	
		LoggingUtil.logInfo(className, methodName, "Start");

		List<MasterRoleEntity> masterRoleEntity = masterRoleRepository.checkAdminRoleByEmail(email);
		List<MasterRoleModal> modalList = new ArrayList();


		for(int i=0 ; i <masterRoleEntity.size();i ++) {
			MasterRoleModal  roleModal= new MasterRoleModal();
			roleModal.setRoleName(masterRoleEntity.get(i).getRoleName());
			roleModal.setRoleType(masterRoleEntity.get(i).getRoleType());
			modalList.add(roleModal);
		}


		return modalList;
	}
	
	/*
	 * Description: This function is used for getting data from MASTER Role Module by using srnumber for identifying the admin presence
	 * Table Name- MASTER_ROLE
	 * Author- Sandeep D
	 */

	@Override
	public List<LocationModel> checkAdminRoleBySrNoForSwalamban(String srNumber) {
		List<MasterUsersEntity> masterUserEntity = masterUsersRepository.checkAdminroleBysrNumber(srNumber);
		List<LocationModel> modalList = new ArrayList();

		logger.info("End Service checkAdminRoleBySrNo");
		String sawlabanValue="";
		for(int i=0 ; i <masterUserEntity.size();i ++) {
			LocationModel  roleModal= new LocationModel();
		//	roleModal.setLocationType(masterUserEntity.get(i).getLocationType());
			roleModal.setLocation(masterUserEntity.get(i).getLocation());
		//	if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("UO"))
				sawlabanValue="Unit";
			//if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("ZO"))
				sawlabanValue="Zonal";
		//	if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("CO"))
				sawlabanValue="COO";
			roleModal.setSwalambanMenu(sawlabanValue);
			modalList.add(roleModal);
		}


		return modalList;
	}
	
	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using srnumber for identifying the active status
	 * Table Name- MASTER_USERS
	 * Author- Sandeep D
	 */

	@Override
	public MasterUsersEntity checkUserNameActiveStatus(String srNumber) {
		// TODO Auto-generated method stub
		MasterUsersEntity objMasterUserEntity = new MasterUsersEntity();
		 objMasterUserEntity = masterUsersRepository.checkActiveStatusBySrNumber(srNumber);
		    return objMasterUserEntity;
	}
	
	


@Override
	public List<MandhanLocationModel> checkAdminRoleBySrNoForMandhan(String srNumber) {
		List<MasterUsersEntity> masterUserEntity = masterUsersRepository.checkAdminroleBysrNumber(srNumber);
		List<MandhanLocationModel> modalList = new ArrayList();

		logger.info("End Service checkAdminRoleBySrNo");
		String mandhanValue="";
		for(int i=0 ; i <masterUserEntity.size();i ++) {
			MandhanLocationModel  roleModal= new MandhanLocationModel();
		//	roleModal.setLocationType(masterUserEntity.get(i).getLocationType());
			roleModal.setLocation(masterUserEntity.get(i).getLocation());
		//	if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("UO"))
			mandhanValue="Unit";
			//if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("ZO"))
			mandhanValue="Zonal";
		//	if (masterUserEntity.get(i).getLocationType().equalsIgnoreCase("CO"))
			mandhanValue="COO";
			roleModal.setMandhanMenu(mandhanValue);
			modalList.add(roleModal);
		}


	return modalList;
	
}

@Override
public ResponseEntity<Map<String, Object>> updateUserDetailAfterLogout(PortalMasterEntity portalMasterEntity) throws UserRoleMappingException {
logger.info("Enter UserManagementService : logoutUser");
try { Map<String, Object> response = new HashMap<String, Object>();
response.put(Constant.STATUS, 0);
response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG); if (portalMasterEntity == null) {
response.put(Constant.STATUS, 0);
response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
} else {
PortalMasterEntity portalAdmin = new PortalMasterEntity();
portalMasterRepository.updateUserDetailAfterLogout(portalMasterEntity.getUsername());
response.put(Constant.STATUS, 1);
response.put(Constant.MESSAGE, Constant.SUCCESS);
response.put("Data", "update portal user after logout : " );
}
return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); } catch (Exception ex) {
logger.info("Could not logout user due to : " + ex.getMessage());
return null;
}
}


@Override
public PortalMasterEntity getMasterUserIdByUserName(String username) {
	// TODO Auto-generated method stub
	PortalMasterEntity objportalEntity = new PortalMasterEntity();
	objportalEntity = portalMasterRepository.getMasterUserIdByUserName(username);
	    return objportalEntity;
}

@Override
public ResponseEntity<Map<String, Object>> updateUserDetailAfterLogin(PortalMasterEntity portalMasterEntity) throws UserRoleMappingException {
	logger.info("Enter UserManagementService : update user detail after login");
	try {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		if (portalMasterEntity == null) {
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		} else {
			PortalMasterEntity portalAdmin = new PortalMasterEntity();
		//	portalAdmin = portalMasterRepository.getPortalMasterDetails(portalMasterEntity.getPortalUserId());
			
		//	portalAdmin.setPortalUserId(portalMasterEntity.getPortalUserId());
		//	portalAdmin.setRefreshToken(portalMasterEntity.getRefreshToken());
		//	portalAdmin.setMphName(portalMasterEntity.getMphName());
		//	portalAdmin.setUsername(portalMasterEntity.getUsername());
		//	portalMasterRepository.save(portalAdmin);
			portalMasterRepository.updateportalusersbyuserName(portalMasterEntity.getUsername(),portalMasterEntity.getRefreshToken(),portalMasterEntity.getMphName());
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", "updated portal user after Login : " );
			}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	} catch (Exception ex) {
		logger.info("Could not update portal user after login : " + ex.getMessage());
		return null;
	}
}


@Override
public ModuleDescriptionRoleTypeEntity getDescriptionByRoleTypeName(String roleTypeName)
		throws UserRoleMappingException {
	// TODO Auto-generated method stub
	
	ModuleDescriptionRoleTypeEntity objportalEntity =new ModuleDescriptionRoleTypeEntity() ;

	objportalEntity = moduleDescriptionRoleTypeRepository.getDescriptionByRoleTypeName(roleTypeName);
	    return objportalEntity;
}

}



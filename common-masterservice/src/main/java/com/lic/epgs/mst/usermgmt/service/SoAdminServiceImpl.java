package com.lic.epgs.mst.usermgmt.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.SOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.modal.SoAdminModel;
import com.lic.epgs.mst.usermgmt.modal.UoZoAdminModel;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.SoAdminRepository;
import com.lic.epgs.mst.usermgmt.repository.UserRoleTypeRepository;

@Service
@Transactional
public class SoAdminServiceImpl implements SoAdminService {
	
	private static final Logger logger = LogManager.getLogger(SoAdminServiceImpl.class);
	
	@Autowired
	SoAdminRepository soAdminRepository;

	@Autowired
	MasterUsersRepository masterUsersRepository;
	
	@Autowired
	private  UserRoleTypeRepository userRoleTypeRepository;
	
	@Autowired
	private JDBCConnection jdbcConnection;

	String className = this.getClass().getSimpleName();
	
	@Override
	 public ResponseEntity<Map<String, Object>> addSoAdmin(SOAdminEntity soAdminEntity) throws Exception {
			logger.info("Enter UserManagementService : add soAdmin ");
			Map<String, Object> response = new HashMap<String, Object>();
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

			try {
				Date date = new Date();
				if (soAdminEntity == null) {
					response.put(Constant.STATUS, 0);
					response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
				} 
				 else {
					if(!isValid(soAdminEntity, "ADD")) {
						response.put(Constant.STATUS, 12);
						response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
					}
					if(!isUnique(soAdminEntity, "ADD")) {
						response.put(Constant.STATUS, 11);
						response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
					}
					 else  {
						soAdminEntity.setCreatedOn(date);
						soAdminEntity.setModifiedOn(date);
						SOAdminEntity soAdminMapping = soAdminRepository.save(soAdminEntity);
						MasterUsersEntity masterUserEntity = new MasterUsersEntity();
						masterUsersRepository.updateisAdminFlag(soAdminEntity.getMasterUserId());
						UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
						userRoleTypeMappingEntity.setAppModuleId(1L);;
						userRoleTypeMappingEntity.setRoleTypeId(11L);
						userRoleTypeMappingEntity.setMasterUserId(soAdminEntity.getMasterUserId());
						userRoleTypeMappingEntity.setIsActive("Y");
						userRoleTypeMappingEntity.setIsDeleted("N");
					//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
						userRoleTypeMappingEntity.setModifiedBy("sysadmin");
						userRoleTypeMappingEntity.setModifiedOn(date);
						userRoleTypeMappingEntity.setCreatedBy("sysAdmin");
						userRoleTypeMappingEntity.setCreatedOn(date);
						userRoleTypeRepository.save(userRoleTypeMappingEntity);
						response.put(Constant.STATUS, 1);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", soAdminMapping.getSoAdminId());
					}
					
				}

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

			} catch (Exception exception) {
				logger.info("Could not save SO Admin due to : " + exception.getMessage());
			}
			return null;
		}
	 
	 private boolean isValid(SOAdminEntity entity, String operation) {

			if(!(masterUsersRepository.findById(entity.getMasterUserId()).isPresent())) {
				return false;
			}
			if(operation.equals("EDIT") && (entity.getSoAdminId() == 0.00 || entity.getMasterUserId() == 0.00)) {
				return false;
			}
			return true;
		}

		private boolean isUnique(SOAdminEntity soAdminEntity, String operation) throws Exception {
			List<SOAdminEntity> result = soAdminRepository.findDuplicate(soAdminEntity.getMasterUserId());
			if(operation.equals("ADD") && !(result.size() > 0)) {
				return true;
			}
			if(operation.equals("EDIT") && !(result.size() > 1)) {
				return true;
			}
			return false;
		}

		@Override
		public List<SOAdminEntity> getAllType() throws Exception {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LoggingUtil.logInfo(className, methodName, "Started");
			LoggingUtil.logInfo(className, methodName, "get SOAdminEntity");
			logger.info("get SOAdminEntity");
			return soAdminRepository.findAll();
		}

		@Override
		public ResponseEntity<Map<String, Object>> updateSoAdmin(SOAdminEntity soAdminEntity) throws Exception {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LoggingUtil.logInfo(className, methodName, "Started");

			Map<String, Object> response = new HashMap<String, Object>();
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

			try {
				if (soAdminEntity == null) {
					response.put(Constant.STATUS, 0);
					response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
				} else {
					
				//	Date today = new Date();
					SOAdminEntity soAdmin = new SOAdminEntity();

					soAdmin = soAdminRepository.getSoAdminDetail(soAdminEntity.getSoAdminId());

					soAdmin.setSoAdminId(soAdminEntity.getSoAdminId());
					soAdmin.setLocation(soAdminEntity.getLocation());
					soAdmin.setLocationCode(soAdminEntity.getLocationCode());
					soAdmin.setLocationType(soAdminEntity.getLocationType());
					soAdmin.setMasterUserId(soAdminEntity.getMasterUserId());
					soAdmin.setModifiedOn(soAdminEntity.getModifiedOn());
					soAdmin.setModifiedBy(soAdminEntity.getModifiedBy());

					if(!isValid(soAdminEntity, "EDIT")) {
						response.put(Constant.STATUS, 12);
						response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
					}
					if(!isUnique(soAdminEntity, "EDIT")) {
						response.put(Constant.STATUS, 11);
						response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
					}
					else  {
						
						
						soAdminRepository.save(soAdmin);
						masterUsersRepository.updateMstUserUO(soAdminEntity.getMasterUserId(), soAdminEntity.getLocation(), soAdminEntity.getLocationCode(), soAdminEntity.getLocationType());
						response.put(Constant.STATUS, 1);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "Updated SO admin for - " + soAdminEntity.getMasterUserId());

					}
				}
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

			} catch (Exception exception) {
				logger.info("Could not update updateSoAdmin due to : " + exception.getMessage());
			}
			return null;
		}
		
		
		@Override
		public ResponseEntity<Map<String, Object>> deleteSoAdmin(Long soAdminId) throws Exception {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LoggingUtil.logInfo(className, methodName, "Started");

			try {

				if (soAdminId == null) {
					response.put(Constant.STATUS, 0);
					response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
				} 
				
					else {
						SOAdminEntity uoAdmin =soAdminRepository.getSoAdminDetail(soAdminId);
	                	Long masterUserId = uoAdmin.getMasterUserId();
	                	MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
	                	if (masteruserEntity.getIsActive().equalsIgnoreCase("Y") & masteruserEntity.getIsDeleted().equalsIgnoreCase("N") &  masteruserEntity.getIsAdmin().equalsIgnoreCase("N")) {
	                	userRoleTypeRepository.findAndDeleteAdminBymasterId(masterUserId);
	                	} else {
	                		userRoleTypeRepository.findAndDeleteBymasterId(masterUserId);
	                	}
						soAdminRepository.findAndDeleteById(soAdminId);
						response.put(Constant.STATUS, 1);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "Deleted UO Admin Id : " + soAdminId );

					}
				
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

			} catch (Exception ex) {
				logger.info("Could not delete UO Admin due to : " + ex.getMessage());
			}
			return null;

		}

		private boolean isValidDeletion(Long id) {
			Optional<SOAdminEntity> result = soAdminRepository.findById(id);
			if(!result.isPresent()) {
				return false;
			}
			if(result.get().getIsDeleted()!= null && result.get().getIsDeleted().equals("Y")) {
				return false;
			}
			if(result.get().getIsActive()!= null && result.get().getIsActive().equals("N")) {
				return false;
			}

			else { return true;  }
		}

		@Override
		public List<SoAdminModel> soSearch(String locationType, String location, String srNumber, String userName)
				throws SQLException {
			logger.info("Start Service  uoSearch");
			List<SoAdminModel> adminModelList = null;
			String sql = null;
			String sql1 = null;

			Connection connection = jdbcConnection.getConnection();
			try {
				
				sql = "  SELECT SO.LOCATION_TYPE, SO.LOCATION_CODE, SO.LOCATION, MU.SRNUMBER,MU.SRNUMBER_MAIN, MU.DESIGNATION, MU.USERNAME,\r\n" + 
						"					 SO.MODIFIED_BY, SO.MODIFIED_ON, SO.SO_ADMIN_ID, MU.MASTER_USER_ID , SO.IS_DELETED,SO.IS_ACTIVE FROM\r\n" + 
						"					 MASTER_USERS MU INNER JOIN SO_ADMIN SO \r\n" + 
						"					 ON MU.MASTER_USER_ID = SO.MASTER_USER_ID\r\n" + 
						"					 WHERE ((UPPER(SO.LOCATION) LIKE UPPER('%'||?||'%')) OR (? =''))\r\n" + 
						"					 AND ((UPPER(SO.LOCATION_TYPE) LIKE UPPER('%'||?||'%')) OR (? =''))\r\n" + 
						"					AND ((UPPER(MU.SRNUMBER_MAIN) LIKE UPPER('%'||?||'%') ) OR (? =''))\r\n" + 
						"					 AND ((UPPER(MU.USERNAME) LIKE UPPER('%'||?||'%') ) OR (? =NULL))\r\n" + 
						"					order by so.modified_on  desc";

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
							SoAdminModel adminModel = new SoAdminModel();
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

}

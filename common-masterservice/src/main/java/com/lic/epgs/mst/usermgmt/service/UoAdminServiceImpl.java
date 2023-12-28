package com.lic.epgs.mst.usermgmt.service;

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
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.UoAdminRepository;
import com.lic.epgs.mst.usermgmt.repository.UserRoleTypeRepository;

@Service
@Transactional
public class UoAdminServiceImpl implements UoAdminService {

	private static final Logger logger = LogManager.getLogger(UoAdminServiceImpl.class);

	@Autowired
	UoAdminRepository uoAdminRepository;

	@Autowired
	MasterUsersRepository masterUsersRepository;
	
	@Autowired
	private  UserRoleTypeRepository userRoleTypeRepository;

	String className = this.getClass().getSimpleName();
	
	/*
	 * Description: This function is used for adding the data into UO Admin Module
	 * Table Name- UO_ADMIN
	 * Author- Nandini R
	 */

	 public ResponseEntity<Map<String, Object>> addUoAdmin(UOAdminEntity uoAdminEntity) throws Exception {
		logger.info("Enter UserManagementService : add uoAdmin ");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		try {
			Date date = new Date();
			if (uoAdminEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
			 else {
				if(!isValid(uoAdminEntity, "ADD")) {
					response.put(Constant.STATUS, 12);
					response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
				}
				if(!isUnique(uoAdminEntity, "ADD")) {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
				}
				 else  {
					uoAdminEntity.setCreatedOn(date);
					uoAdminEntity.setModifiedOn(date);
					UOAdminEntity uoAdminMapping = uoAdminRepository.save(uoAdminEntity);
					MasterUsersEntity masterUserEntity = new MasterUsersEntity();
					masterUsersRepository.updateisAdminFlag(uoAdminEntity.getMasterUserId());
					UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
					userRoleTypeMappingEntity.setAppModuleId(1L);;
					userRoleTypeMappingEntity.setRoleTypeId(11L);
					userRoleTypeMappingEntity.setMasterUserId(uoAdminEntity.getMasterUserId());
					userRoleTypeMappingEntity.setIsActive("Y");
					userRoleTypeMappingEntity.setIsDeleted("N");
				//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
					userRoleTypeMappingEntity.setModifiedBy("sysadmin");
					userRoleTypeMappingEntity.setModifiedOn(date);
					userRoleTypeMappingEntity.setCreatedBy("sysAdmin");
					userRoleTypeMappingEntity.setCreatedOn(date);
					userRoleTypeRepository.save(userRoleTypeMappingEntity);
					UserRoleTypeMappingEntity savedEntity = userRoleTypeRepository.save(userRoleTypeMappingEntity);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", uoAdminMapping.getUoAdminId());
				}
				
			}

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Could not save UO Admin due to : " + exception.getMessage());
		}
		return null;
	}

	/*
	 * Description: This function is used for adding the data into UO Admin Module
	 * Table Name- UO_ADMIN
	 * Author- Nandini R
	 */

	@Override
	public List<UOAdminEntity> getAllType() throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "get UOAdminEntity");
		logger.info("get UOAdminEntity");
		return uoAdminRepository.findAll();
	}

	/*
	 * Description: This function is used for soft deleting the data in UO Admin Module by using primary key
	 * Table Name- UO_ADMIN
	 * Author- Nandini R
	 */

	@Override
	public ResponseEntity<Map<String, Object>> deleteUoAdmin(Long uoAdminId) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {

			if (uoAdminId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
			
				else {
					UOAdminEntity uoAdmin =uoAdminRepository.getUoAdminDetailByID(uoAdminId);
                	Long masterUserId = uoAdmin.getMasterUserId();
                	MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
                	if (masteruserEntity.getIsActive().equalsIgnoreCase("Y") & masteruserEntity.getIsDeleted().equalsIgnoreCase("N") &  masteruserEntity.getIsAdmin().equalsIgnoreCase("N")) {
                	userRoleTypeRepository.DeleteAdminBymasterId(masterUserId);
                	} else {
                		userRoleTypeRepository.DeleteBymasterId(masterUserId);
                	}
					uoAdminRepository.findAndDeleteByMasterUserId(masterUserId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Deleted UO Admin Id : " + uoAdminId );

				}
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete UO Admin due to : " + ex.getMessage());
		}
		return null;

	}

	private boolean isValidDeletion(Long id) {
		Optional<UOAdminEntity> result = uoAdminRepository.findById(id);
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

	/*
	 * Description: This function is used for updating the data in Uo Admin Module
	 * Table Name-UCO_ADMIN
	 * Author- Nandini R
	 */
	
	@Override
	public ResponseEntity<Map<String, Object>> updateUoAdmin(UOAdminEntity uoAdminEntity) throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			if (uoAdminEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				
			//	Date today = new Date();
				UOAdminEntity uoAdmin = new UOAdminEntity();

				uoAdmin = uoAdminRepository.getUoAdminDetail(uoAdminEntity.getUoAdminId());

				uoAdmin.setUoAdminId(uoAdminEntity.getUoAdminId());
				uoAdmin.setLocation(uoAdminEntity.getLocation());
				uoAdmin.setLocationCode(uoAdminEntity.getLocationCode());
				uoAdmin.setLocationType(uoAdminEntity.getLocationType());
				uoAdmin.setMasterUserId(uoAdminEntity.getMasterUserId());
				uoAdmin.setModifiedOn(uoAdminEntity.getModifiedOn());
				uoAdmin.setModifiedBy(uoAdminEntity.getModifiedBy());

				if(!isValid(uoAdminEntity, "EDIT")) {
					response.put(Constant.STATUS, 12);
					response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
				}
				if(!isUnique(uoAdminEntity, "EDIT")) {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
				}
				else  {
					
					
					uoAdminRepository.save(uoAdmin);
					masterUsersRepository.updateMstUserUO(uoAdminEntity.getMasterUserId(), uoAdminEntity.getLocation(), uoAdminEntity.getLocationCode(), uoAdminEntity.getLocationType());
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Updated UO admin for - " + uoAdminEntity.getMasterUserId());

				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Could not update updateUoAdmin due to : " + exception.getMessage());
		}
		return null;
	}

	private boolean isValid(UOAdminEntity entity, String operation) {

		if(!(masterUsersRepository.findById(entity.getMasterUserId()).isPresent())) {
			return false;
		}
		if(operation.equals("EDIT") && (entity.getUoAdminId() == 0.00 || entity.getMasterUserId() == 0.00)) {
			return false;
		}
		return true;
	}

	private boolean isUnique(UOAdminEntity uoAdminEntity, String operation) throws Exception {
		List<UOAdminEntity> result = uoAdminRepository.findDuplicate(uoAdminEntity.getMasterUserId());
		if(operation.equals("ADD") && !(result.size() > 0)) {
			return true;
		}
		if(operation.equals("EDIT") && !(result.size() > 1)) {
			return true;
		}
		return false;
	}

}

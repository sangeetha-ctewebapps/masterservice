package com.lic.epgs.mst.usermgmt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.entity.ZOAdminEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.ZOAdminException;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.UserRoleTypeRepository;
import com.lic.epgs.mst.usermgmt.repository.ZOAdminRepository;


@Service
@Transactional
public class ZOAdminServiceImpl implements  ZOAdminService  {

	private static final Logger logger = LogManager.getLogger(ZOAdminServiceImpl.class);

	@Autowired
	private ZOAdminRepository zoAdminRepository;
	
	@Autowired
	MasterUsersRepository masterUsersRepository;
	
	@Autowired
	private  UserRoleTypeRepository userRoleTypeRepository;
	
	
	
	
	/*
	 * Description: This function is used for getting adding the data into Zo Admin Module
	 * Table Name- ZO_ADMIN
	 * Author- Sandeep D
	 */
	
	

	 public ResponseEntity<Map<String, Object>> addZoAdmin(ZOAdminEntity zoAdminEntity) throws ZOAdminException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter ZOAdminService : " + methodName);

		try {
			if (zoAdminEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			}
			else {
				Date today = new Date();
				if(!isValid(zoAdminEntity, "ADD")) {
					response.put(Constant.STATUS, 12);
					response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
				}
				if(!isUnique(zoAdminEntity, "ADD")) {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
				}
				 else {
					zoAdminEntity.setCreatedOn(today);
					zoAdminEntity.setModifiedOn(today);
					ZOAdminEntity zOAdmin = zoAdminRepository.save(zoAdminEntity);
					MasterUsersEntity masterUserEntity = new MasterUsersEntity();
					masterUsersRepository.updateisAdminFlag(zoAdminEntity.getMasterUserId());
					UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
					userRoleTypeMappingEntity.setAppModuleId(1L);;
					userRoleTypeMappingEntity.setRoleTypeId(11L);
					userRoleTypeMappingEntity.setMasterUserId(zoAdminEntity.getMasterUserId());
					userRoleTypeMappingEntity.setIsActive("Y");
					userRoleTypeMappingEntity.setIsDeleted("N");
				//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
					userRoleTypeMappingEntity.setModifiedBy("sysadmin");
					userRoleTypeMappingEntity.setModifiedOn(today);
					userRoleTypeMappingEntity.setCreatedBy("sysAdmin");
					userRoleTypeMappingEntity.setCreatedOn(today);
					userRoleTypeRepository.save(userRoleTypeMappingEntity);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", zOAdmin.getZoAdminId());
				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(" add Uo admin exception occured." + e.getMessage());
			throw new ZOAdminException ("Internal Server Error");
		}
		//return null;

	}
	
	/*
	 * Description: This function is used for updating the data in Zo Admin Module
	 * Table Name- ZO_ADMIN
	 * Author- Sandeep D
	 */

	@Override
	public ResponseEntity<Map<String, Object>> updateZoAdmin(ZOAdminEntity zoAdminEntity) throws ZOAdminException {
		logger.info("Enter ZOAdminServiceImpl : updateZoAdmin ");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			if (zoAdminEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				//Date today = new Date();
				ZOAdminEntity zoAdmin = new ZOAdminEntity();

				zoAdmin = zoAdminRepository.getZoAdminDetail(zoAdminEntity.getZoAdminId());

				zoAdmin.setZoAdminId(zoAdminEntity.getZoAdminId());
				zoAdmin.setLocation(zoAdminEntity.getLocation());
				zoAdmin.setLocationCode(zoAdminEntity.getLocationCode());
				zoAdmin.setMasterUserId(zoAdminEntity.getMasterUserId());
				zoAdmin.setModifiedBy(zoAdminEntity.getModifiedBy());
				zoAdmin.setModifiedOn(zoAdminEntity.getModifiedOn());


				if(!isValid(zoAdminEntity, "EDIT")) {
					response.put(Constant.STATUS, 12);
					response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
				}
				if(!isUnique(zoAdminEntity, "EDIT")) {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
				}
				else  {
					zoAdminRepository.save(zoAdmin);

					masterUsersRepository.updateMstUserZO(zoAdminEntity.getMasterUserId(), zoAdminEntity.getLocation(), zoAdminEntity.getLocationCode());
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Updated ZO admin for - " + zoAdminEntity.getMasterUserId());
				}
				
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(" update Uo Admin  exception occured." + e.getMessage());
			throw new ZOAdminException ("Internal Server Error");
		}
		//return null;
	}
	
	/*
	 * Description: This function is used for soft deleting the data in Zo Admin Module by using primary key
	 * Table Name- ZO_ADMIN
	 * Author- Sandeep D
	 */

	@Override
	public ResponseEntity<Map<String, Object>> deleteZoAdmin(Long zoAdminId) throws ZOAdminException{

		try {

			Map<String, Object> response = new HashMap<String, Object>();
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.info("Enter ZOAdminServiceImpl : " + methodName);
			if (zoAdminId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
				 else {
					 ZOAdminEntity zoAdmin =zoAdminRepository.getZoAdminDetailByID(zoAdminId);
	               	Long masterUserId = zoAdmin.getMasterUserId();
	               	MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
                	if (masteruserEntity.getIsActive().equalsIgnoreCase("Y") & masteruserEntity.getIsDeleted().equalsIgnoreCase("N") &  masteruserEntity.getIsAdmin().equalsIgnoreCase("N")) {
                	userRoleTypeRepository.DeleteAdminBymasterId(masterUserId);
                	} else {
                		userRoleTypeRepository.findAndDeleteBymasterId(masterUserId);
                	}
					zoAdminRepository.findAndDeleteByMasterUserId(masterUserId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Deleted Zo Admin Id - " + zoAdminId );
				}

			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(" Delete Zo Admin" + e.getMessage());
			throw new ZOAdminException ("Internal Server Error");
		}
		//return null;
	}

	private boolean isValidDeletion(Long id) {
		Optional<ZOAdminEntity> result = zoAdminRepository.findById(id);
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
	
	private boolean isValid(ZOAdminEntity entity, String operation) {

		if(!(masterUsersRepository.findById(entity.getMasterUserId()).isPresent())) {
			return false;
		}
		if(operation.equals("EDIT") && (entity.getZoAdminId() == 0.00 || entity.getMasterUserId() == 0.00)) {
			return false;
		}
		return true;
	}
	
	private boolean isUnique(ZOAdminEntity zOAdminEntity, String operation) {
		List<ZOAdminEntity> result = zoAdminRepository.findDuplicate(zOAdminEntity.getMasterUserId());
		if(operation.equals("ADD") && !(result.size() > 0)) {
			return true;
		}
		if(operation.equals("EDIT") && !(result.size() > 1)) {
			return true;
		}
		return false;

	}


}

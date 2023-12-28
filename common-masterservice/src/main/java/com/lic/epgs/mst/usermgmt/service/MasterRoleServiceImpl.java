package com.lic.epgs.mst.usermgmt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.controller.MasterRoleController;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleAppEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.repository.MasterRoleAppRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterRoleRepository;

@Service
@Transactional
public class MasterRoleServiceImpl implements MasterRoleService {

	@Autowired
	private MasterRoleRepository masterRoleRepository;

	@Autowired
	private MasterRoleAppRepository masterRoleAppRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MasterRoleController.class);

	@Override
	 public List<MasterRoleEntity> getAllRoles() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get Roles list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return masterRoleRepository.getAllRoles();
	}

	/*
	 * Description: This function is used for getting all the active role data from MASTER ROLE Module
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */
	
	@Override
	public List<MasterRoleEntity> getAllActiveRoleByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return masterRoleRepository.getAllActiveRoleByCondition();
	}

	@Override
	public void removeRole(Long roleId) throws Exception {
		logger.info("Get remove Role");
		masterRoleRepository.deleteByroleId(roleId);
	}

	/*
	 * Description: This function is used for adding the data into MASTER ROLE Module
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> addRole(MasterRoleEntity masterRoleEntity) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService : " + methodName);

		try {
			Date date = new Date();
			if (masterRoleEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if (isValid(masterRoleEntity, "ADD")) {
					masterRoleEntity.setCreatedOn(date);
					masterRoleEntity.setModifiedOn(date);
					MasterRoleEntity uoAdminMapping = masterRoleRepository.save(masterRoleEntity);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", uoAdminMapping.getRoleId());
				} else {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);

				}
			}

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception " + exception.getMessage());
		}
		return null;
	}

	private boolean isValid(MasterRoleEntity masterRoleEntity, String operation) {
		List<MasterRoleEntity> result = masterRoleRepository.findDuplicate(masterRoleEntity.getRoleName(),
				masterRoleEntity.getAppModule());
		if (operation.equals("ADD") && !(result.size() > 0)) {
			return true;
		}
		if (operation.equals("EDIT") && !(result.size() > 1)) {
			return true;
		}
		return false;
	}
	
	/*
	 * Description: This function is used for updating the data in MASTER ROLE Module
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */

	@Override
	public ResponseEntity<Map<String, Object>> updateMasterRole(MasterRoleEntity masterRoleEntity) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {

			if (masterRoleEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if (isValid(masterRoleEntity, "ADD")) {	
					Date date = new Date();
					MasterRoleEntity masterRoleMapping = new MasterRoleEntity();

					masterRoleMapping = masterRoleRepository.getMasterRoleDetail(masterRoleEntity.getRoleId());

					masterRoleMapping.setRoleId(masterRoleEntity.getRoleId());
					masterRoleMapping.setAppModule(masterRoleEntity.getAppModule());
					masterRoleMapping.setRoleName(masterRoleEntity.getRoleName());
					masterRoleMapping.setRoleType(masterRoleEntity.getRoleType());
					masterRoleMapping.setIsActive(masterRoleEntity.getIsActive());
					masterRoleMapping.setIsDeleted(masterRoleEntity.getIsDeleted());
					masterRoleMapping.setModifiedBy(masterRoleEntity.getModifiedBy());
					masterRoleMapping.setModifiedOn(date);
					masterRoleMapping.setCreatedOn(masterRoleEntity.getCreatedOn());

					masterRoleRepository.save(masterRoleMapping);

					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "updated User Role for - " + masterRoleEntity.getRoleId());
				}
				else {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);

				}
			}

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Could not update user role due to : " + exception.getMessage());
		}
		return null;
	}

	/*
	 * Description: This function is used for soft deleting the data in MASTER ROLE Module by using primary key
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */
	
	@Override
	public ResponseEntity<Map<String, Object>> deleteMasterRole(Long roleId) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			if (roleId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if (!isValidDeletion(roleId)) {
					response.put(Constant.STATUS, 10);
					response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				}

				else {
					masterRoleRepository.deleteByroleId(roleId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Deleted user Role Map Id : " + roleId);
				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete user role due to : ", ex.getMessage());
			return null;
		}
	}

	private boolean isValidDeletion(Long id) {
		Optional<MasterRoleEntity> result = masterRoleRepository.findById(id);

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
	 * Description: This function is used for getting all the data from MASTER ROLE Module
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */

	public List<MasterRoleAppEntity> getAllRolesByApp() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get Roles list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return masterRoleAppRepository.getAllRolesByApp();

	}

}

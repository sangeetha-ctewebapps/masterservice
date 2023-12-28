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
import com.lic.epgs.mst.usermgmt.entity.CoAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.COAdminException;
import com.lic.epgs.mst.usermgmt.modal.CoAdminModel;
import com.lic.epgs.mst.usermgmt.repository.CoAdminRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.UserRoleTypeRepository;

@Service
@Transactional

public class CoAdminServiceImpl implements CoAdminService {

	@Autowired
	private CoAdminRepository coAdminRepository;
	
	@Autowired
	private MasterUsersRepository masterUsersRepository;
	
	
	@Autowired
	private  UserRoleTypeRepository userRoleTypeRepository;
	
	@Autowired
	private JDBCConnection jdbcConnection;


	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CoAdminServiceImpl.class);

	/*
	 * Description: This function is used for getting all the data from Co Admin Module
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */
	
	@Override
	 public List<CoAdminEntity> getAllCoAdmin() {
		
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		LoggingUtil.logInfo(className, methodName, "Started");

		return coAdminRepository.getAllCoAdmin();
	}
	
	/*
	 * Description: This function is used for searching the data in Co Admin Module using the filters like LOCATION, SRNUMBER, USERNAME
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */

	@Override
	public List<CoAdminModel> coSearch(String locationType, String location, String srNumber, String userName)
			throws SQLException {
		logger.info("Start Service  coSearch");
		List<CoAdminModel> adminModelList = null;
		String sql = null;
		Connection connection = jdbcConnection.getConnection();
		try {
			
			sql = "SELECT CO.CO_NAME,co.LOCATION_CODE,MU.LOCATION_TYPE, MU.SRNUMBER,MU.SRNUMBER_MAIN, MU.DESIGNATION, MU.USERNAME, CO.MODIFIED_BY, CO.MODIFIED_ON,CO.CO_ADMIN_ID, MU.MASTER_USER_ID , CO.IS_DELETED,CO.IS_ACTIVE FROM\r\n" + 
					"					 MASTER_USERS MU INNER JOIN CO_ADMIN CO \r\n" + 
					"					 ON MU.MASTER_USER_ID = CO.MASTER_USER_ID\r\n" + 
					"					 WHERE ((UPPER(CO.CO_NAME) LIKE UPPER('%'||?||'%')) OR (? =''))\r\n" + 
					"					 AND ((UPPER(MU.LOCATION_TYPE) LIKE UPPER('%'||?||'%')) OR (? =''))\r\n" + 
					"					AND ((UPPER(MU.SRNUMBER_MAIN) LIKE UPPER('%'||?||'%') ) OR (? =''))\r\n" + 
					"					 AND ((UPPER(MU.USERNAME) LIKE UPPER('%'||?||'%') ) OR (? =NULL))\r\n" + 
					"					order by co.modified_on desc";

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
						CoAdminModel adminModel = new CoAdminModel();
						adminModel.setLocation(rs.getString(1));
						adminModel.setLocationCode(rs.getString(2));
						adminModel.setLocationType(rs.getString(3));
						adminModel.setSrNumber(rs.getString(4));
						adminModel.setSrNumber2(rs.getString(5));
						adminModel.setDegignation(rs.getString(6));
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
					logger.error("coSearch Exception : " + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("coSearch Exception : " + e.getMessage());
			}
			return adminModelList;
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			return adminModelList;
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
		
	}

	/*
	 * Description: This function is used for getting adding the data into Co Admin Module
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */
	
	@Override
	public ResponseEntity<Map<String, Object>> addCoAdmin(CoAdminEntity coAdminEntity) throws COAdminException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter CoAdminService : " + methodName);

		try {
			if (coAdminEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			}
			else {
				Date today = new Date();
				if(!isValid(coAdminEntity, "ADD")) {
					response.put(Constant.STATUS, 12);
					response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
				}
				if(!isUnique(coAdminEntity, "ADD")) {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
				}
				 else {
					
					coAdminEntity.setCreatedOn(today);
					coAdminEntity.setModifiedOn(today);
					CoAdminEntity coAdmin = coAdminRepository.save(coAdminEntity);
					MasterUsersEntity masteruserEntity = new MasterUsersEntity();
					//userRoleTypeMappingEntity.setMasterUserId(zoAdminEntity.getMasterUserId());
					masterUsersRepository.updateisAdminFlag(coAdminEntity.getMasterUserId());
					UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
					userRoleTypeMappingEntity.setAppModuleId(1L);;
					userRoleTypeMappingEntity.setRoleTypeId(11L);
					userRoleTypeMappingEntity.setMasterUserId(coAdminEntity.getMasterUserId());
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
					response.put("Data", coAdmin.getCoAdminId());
				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.error("Could not add Coadmin " + exception.getMessage());
			 throw new COAdminException ("Internal Server Error");
		}
		//return null;

	}
	
	/*
	 * Description: This function is used for soft deleting the data in Co Admin Module by using primary key
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */
	
	@Override
    public ResponseEntity<Map<String, Object>> deleteCoAdmin(Long coAdminId) throws COAdminException{
        // TODO Auto-generated method stub
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(Constant.STATUS, 0);
        response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggingUtil.logInfo(className, methodName, "Started");
       
      //  Long masterUserId = null;
        Long userRoleTypeMappingId = null;
 
        try {
        	
        	
            if (coAdminId == null) {
                response.put(Constant.STATUS, 0);
                response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
            } 
                 else {
                	// MasterUsersEntity masteruserEntity = new MasterUsersEntity();
                //	UserRoleTypeMappingEntity userRoleType = userRoleTypeRepository.getUserRoleTypeByRoleMapId(userRoleTypeMappingId);
                	CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
                	Long masterUserId = coAdmin.getMasterUserId();
                	MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
                	if (masteruserEntity.getIsActive().equalsIgnoreCase("Y") & masteruserEntity.getIsDeleted().equalsIgnoreCase("N") &  masteruserEntity.getIsAdmin().equalsIgnoreCase("N")) {
                	userRoleTypeRepository.DeleteAdminBymasterId(masterUserId);
                	} else {
                		userRoleTypeRepository.findAndDeleteBymasterId(masterUserId);
                	}
                	coAdminRepository.findAndDeleteByMasterUserId(masterUserId);
                	
                	
                   
                    response.put(Constant.STATUS, 1);
                    response.put(Constant.MESSAGE, Constant.SUCCESS);
                    response.put("Data", "Deleted CO Admin Id : " + coAdmin.getMasterUserId());
 
                }
            
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
 
        } catch (Exception ex) {
            logger.info("Could not delete CO Admin due to : " + ex.getMessage());
            throw new COAdminException ("Internal Server Error");
        }
        //return null;
 
    }
 
    private boolean isValidDeletion(Long id) {
        java.util.Optional<CoAdminEntity> result = coAdminRepository.findById(id);
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
	 * Description: This function is used for updating the data in Co Admin Module
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */
    
    @Override
    public ResponseEntity<Map<String, Object>> updateCoAdmin(CoAdminEntity coAdminEntity) throws COAdminException {
    // TODO Auto-generated method stub
    String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
    LoggingUtil.logInfo(className, methodName, "Started");



    Map<String, Object> response = new HashMap<String, Object>();
    response.put(Constant.STATUS, 0);
    response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);



    try {
    if (coAdminEntity == null) {
    response.put(Constant.STATUS, 0);
    response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
    } else {

    	 Date date = new Date();

    CoAdminEntity coAdmin = new CoAdminEntity();



    coAdmin = coAdminRepository.getCoAdminDetail(coAdminEntity.getCoAdminId());



    coAdmin.setCoAdminId(coAdminEntity.getCoAdminId());
    coAdmin.setCoAdminName(coAdminEntity.getCoAdminName());
    coAdmin.setLocationCode(coAdminEntity.getLocationCode());
    coAdmin.setMasterUserId(coAdminEntity.getMasterUserId());
    coAdmin.setModifiedBy(coAdminEntity.getModifiedBy());
    coAdmin.setModifiedOn(date);
    coAdmin.setCreatedBy(coAdminEntity.getCreatedBy());


    if (!isValid(coAdminEntity, "EDIT")) {
    response.put(Constant.STATUS, 12);
    response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
    }
    if (!isUnique(coAdminEntity, "EDIT")) {
    response.put(Constant.STATUS, 11);
    response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
    } else {


    coAdminRepository.save(coAdmin);
    masterUsersRepository.updateMstUserCO(coAdminEntity.getMasterUserId(),
    coAdminEntity.getCoAdminName(), coAdminEntity.getLocationCode());
    response.put(Constant.STATUS, 1);
    response.put(Constant.MESSAGE, Constant.SUCCESS);
    response.put("Data", "Updated CO admin for - " + coAdminEntity.getMasterUserId());



    }
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);



    } catch (Exception exception) {
    logger.info("Could not update update CoAdmin due to : " + exception.getMessage());
    throw new COAdminException ("Internal Server Error");
    }
  //  return null;
    }
    
    private boolean isValid(CoAdminEntity entity, String operation) {
    	// TODO Auto-generated method stub
    	if (!(masterUsersRepository.findById(entity.getMasterUserId()).isPresent())) {
    	return false;
    	}
    	if (operation.equals("EDIT") && (entity.getCoAdminId() == 0.00 || entity.getMasterUserId() == 0.00)) {
    	return false;
    	}
    	return true;
    	}



    	private boolean isUnique(CoAdminEntity coAdminEntity, String operation) {
    	// TODO Auto-generated method stub
    	List<CoAdminEntity> result = coAdminRepository.findDuplicate(coAdminEntity.getMasterUserId());



    	if (operation.equals("ADD") && !(result.size() > 0)) {
    	return true;
    	}
    	if (operation.equals("EDIT") && !(result.size() > 1)) {
    	return true;
    	}
    	return false;
    	}

	}



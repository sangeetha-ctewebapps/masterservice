package com.lic.epgs.mst.service;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.controller.DesignationController;
import com.lic.epgs.mst.entity.DesignationMaster;
import com.lic.epgs.mst.exceptionhandling.DesignationServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.DesignationRepository;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.usermgmt.entity.FunctionalAreaEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersLoginDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;

@Service
@Transactional
public class DesignationServiceImpl implements DesignationService {

	private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);
	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private JDBCConnection jdbcConnection;

	String className = this.getClass().getSimpleName();

	@Override
	public List<DesignationMaster> getAllDesignation() {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		LoggingUtil.logInfo(className, methodName, "Started");

		return designationRepository.getAllDesignation();
	}

	@Override
	public List<DesignationMaster> getAllDesignationByCondition() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return designationRepository.findAllByCondition();
	}

	@Override
	public DesignationMaster getDesignationById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<DesignationMaster> designationDb = this.designationRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for designation By Id" + id);
		if (designationDb.isPresent()) {
			logger.info("designation is  found with id" + id);
			return designationDb.get();
		} else {
			throw new ResourceNotFoundException("designation not found with id:" + id);
		}
	}

	@Override
	public DesignationMaster getDesignationtByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		DesignationMaster desginationDb = designationRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for designation By code" + code);
		if (desginationDb != null) {
			logger.info("designation is  found with code" + code);
			return desginationDb;
		} else {
			throw new ResourceNotFoundException("designation not found with code:" + code);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> addDesignation(DesignationMaster designation) {
		// TODO Auto-generated method stub
		logger.info("Enter Designation Controller : add  Designation");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		try {
			Date date = new Date();
			if (designation == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} 
				else  {
					if(!isValid(designation, "ADD")) {
						response.put(Constant.STATUS, 201);
						response.put(Constant.MESSAGE, Constant.FAILED);
						response.put(Constant.DATA,"Invalid Payload");
					}
					if(!isUnique(designation, "ADD")) {
						response.put(Constant.STATUS, 201);
						response.put(Constant.MESSAGE, Constant.FAILED);
						response.put(Constant.DATA,"Duplicate Entry");
					}
					else {
					//masterUsersLoginDetailsEntity.setLoggedOutTime(date);
					DesignationMaster masterUsersLoginDetails = designationRepository.save(designation);
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("DESIGNATIONID", designation.getDesignationId());
				}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} 
		}catch (Exception exception) {
			logger.info("Could not save  Designation: " + exception.getMessage());
		}
		return null;
	}
	
	private boolean isValid(DesignationMaster designation, String operation) {
		
		if(!(designationRepository.findById(designation.getDesignationId()).isPresent())) {
			return false;
		}
		if(operation.equals("EDIT") && (designation.getDesignationId() == 0.00 || designation.getDesignationId() == 0.00)) {
			return false;
		}
		return true;
	}
		
		private boolean isUnique(DesignationMaster designation, String operation) throws Exception {
			List<DesignationMaster> result = designationRepository.findDuplicate( designation.getDescription(), designation.getDesignationCode());
			if(operation.equals("ADD") && !(result.size() > 0)) {
				return true;
			}
			if(operation.equals("EDIT") && !(result.size() > 1)) {
				return true;
			}
			return false;
		}

	/*@Override
	public DesignationMaster updateDesignation(DesignationMaster designation) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Optional<DesignationMaster> designationDb = this.designationRepository.findById(designation.getDesignationId());
		LoggingUtil.logInfo(className, methodName, "update for designation By Id" + designation);
		if (designationDb.isPresent()) {
			DesignationMaster designationUpdate = designationDb.get();
			designationUpdate.setDesignationId(designation.getDesignationId());
			designationUpdate.setDescription(designation.getDescription());
			designationUpdate.setDesignationCode(designation.getDesignationCode());
			designationUpdate.setIsActive(designation.getIsActive());
			designationUpdate.setModifiedBy(designation.getModifiedBy());
			designationRepository.save(designationUpdate);
			return designationUpdate;
		} else {
			throw new ResourceNotFoundException("designation not found with id:" + designation.getDesignationId());
		}
	}*/

	@Override
	public ResponseEntity<Map<String, Object>> deleteDesignation(Long designationId)
			throws DesignationServiceException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			if (designationId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			}

			else {
				designationRepository.deleteBydesignationId(designationId);
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", " Deleted designation with designation Id : " + designationId);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete district due to : ", ex.getMessage());
			throw new DesignationServiceException("internal server error");
		}
	}

	@Override

	public List<DesignationMaster> getSearchByDesignationCode(String designationCode, String description ,String officeType)
			throws SQLException {
		logger.info("Start Service  master users Search");
		List<DesignationMaster> designationSearchList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();

		try {

			sqlmu = "SELECT MD.DESIGNATION_ID, MD.DESIGNATION_CODE, MD.DESCRIPTION, MD.OFFICETYPE,MD.MODIFIED_ON,MD.MODIFIED_BY,MD.ISINCHARGE FROM MASTER_DESIGNATION MD \r\n" + 
					"																			WHERE (MD.DESIGNATION_CODE) = ? \r\n" + 
					"																			OR (MD.DESCRIPTION) = ?\r\n" + 
					"										                                    AND (MD.OFFICETYPE) = ?\r\n" + 
					"																			order by md.modified_on desc";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setString(1, designationCode);
				preparestatements.setString(2, description);
				preparestatements.setString(3, officeType);
				try(ResultSet rs = preparestatements.executeQuery();)
				{
	
					designationSearchList = new ArrayList<>();
					while (rs.next()) {
						DesignationMaster designationMaster = new DesignationMaster();
		
						designationMaster.setDesignationId(rs.getLong("DESIGNATION_ID"));
						designationMaster.setModifiedBy(rs.getString("MODIFIED_BY"));
						designationMaster.setModifiedOn(rs.getDate("MODIFIED_ON"));
						designationMaster.setDescription(rs.getString("DESCRIPTION"));
						 designationMaster.setOfficeType(rs.getString("OFFICETYPE"));
						// designationMaster.setIsDeleted(rs.getString("IS_DELETED"));
						designationMaster.setDesignationCode(rs.getString("DESIGNATION_CODE"));
						designationMaster.setIsIncharge(rs.getString("ISINCHARGE"));
		
						designationSearchList.add(designationMaster);
					}
		
					logger.info("designation Search executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getSearchByDesignationCode exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getSearchByDesignationCode exception occured." + e.getMessage());
			}
			return designationSearchList;
		} catch (Exception e) {
			logger.error("exception occured." + e.getMessage());
			throw new SQLException("internal server error");
		} finally {
			if (!connection.isClosed()) {
				connection.close();
			}
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> updateDesignation(DesignationMaster designation) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {

			 if (designation == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				
					Date date = new Date();
					DesignationMaster existingFunctionalArea = designationRepository.getAllDesignationBydesignationId(designation.getDesignationId());
					existingFunctionalArea.setDesignationCode(designation.getDesignationCode());
					existingFunctionalArea.setDescription(designation.getDescription());
					existingFunctionalArea.setIsActive(designation.getIsActive());
					existingFunctionalArea.setIsDeleted(designation.getIsDeleted());
					existingFunctionalArea.setCreatedBy(designation.getCreatedBy());
					existingFunctionalArea.setCreatedOn(designation.getCreatedOn());
					existingFunctionalArea.setModifiedBy(designation.getModifiedBy());
					existingFunctionalArea.setModifiedOn(designation.getModifiedOn());
					existingFunctionalArea.setOfficeType(designation.getOfficeType());
					existingFunctionalArea.setIsIncharge(designation.getIsIncharge());
					
					if(!isValid(designation, "EDIT")) {
						response.put(Constant.STATUS, 201);
						response.put(Constant.MESSAGE, Constant.FAILED);
						response.put(Constant.DATA,"Invalid Payload");
					}
					if(!isUnique(designation, "EDIT")) {
						response.put(Constant.STATUS, 201);
						response.put(Constant.MESSAGE, Constant.FAILED);
						response.put(Constant.DATA,"Duplicate Entry");
					}
					else {
					designationRepository.save(existingFunctionalArea);
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Updated  Designation for: " + designation.getDesignationId());
				
				
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} 
		}catch (Exception ex) {
			logger.info("Could not update Functional Area  due to : " + ex.getMessage());
		}
		return null;
	}

	

}

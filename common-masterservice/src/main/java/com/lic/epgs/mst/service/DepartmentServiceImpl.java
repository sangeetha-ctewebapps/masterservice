package com.lic.epgs.mst.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.DepartmentController;
import com.lic.epgs.mst.entity.DepartmentMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.DepartmentRepository;
import com.lic.epgs.mst.repository.JDBCConnection;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private JDBCConnection jdbcConnection;
	
	String className = this.getClass().getSimpleName();

	@Override
	public List<DepartmentMaster> getAllDepartment() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return departmentRepository.findAll();
	}
	
	@Override
	public List<DepartmentMaster> getAllDepartmentByCondition() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return departmentRepository.findAllByCondition();
	}
	
	@Override
	public DepartmentMaster getDepartmentById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<DepartmentMaster> departmentDb = this.departmentRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for department By Id" + id);
		if (departmentDb.isPresent()) {
			logger.info("department is  found with id" + id);
			return departmentDb.get();
		} else {
			throw new ResourceNotFoundException("department not found with id:" + id);
		}
	}

	@Override
	public DepartmentMaster getDepartmentByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<DepartmentMaster> departmentDb = this.departmentRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for department By code" + code);
		if (departmentDb.isPresent()) {
			logger.info("department is  found with code" + code);
			return departmentDb.get();
		} else {
			throw new ResourceNotFoundException("department not found with code:" + code);
		}
	}

	@Override
	public DepartmentMaster addDepartment(DepartmentMaster department) {
		// TODO Auto-generated method stub
		return departmentRepository.save(department);
	}

	@Override
	public DepartmentMaster updateDepartment(DepartmentMaster department) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Optional<DepartmentMaster> departmentDb = this.departmentRepository.findById(department.getDepartmentId());
		LoggingUtil.logInfo(className, methodName, "update for department By Id" + department);
		if (departmentDb.isPresent()) {
			DepartmentMaster departmentUpdate = departmentDb.get();
			departmentUpdate.setDepartmentId(department.getDepartmentId());
			departmentUpdate.setDescription(department.getDescription());
			departmentUpdate.setDepartmentCode(department.getDepartmentCode());
			departmentUpdate.setIsActive(department.getIsActive());
			departmentUpdate.setModifiedBy(department.getModifiedBy());
			departmentRepository.save(departmentUpdate);
			return departmentUpdate;
		} else {
			throw new ResourceNotFoundException("department not found with id:" + department.getDepartmentId());
		}

	}
	
	@Override
	public void deleteDeparment(Long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		Integer departmentDb = this.departmentRepository.findDeletedById(id);
		LoggingUtil.logInfo(className, methodName, "delete department ById" + id);
		if (departmentDb != null) {
			logger.info("department soft not deleted with id " + id);

		} else {
			throw new ResourceNotFoundException("department soft not found :" + id);
		}

	}

	
	@Override

	public List<DepartmentMaster> getSearchByDepartmentCode(String departmentCode, String description) throws SQLException {
		logger.info("Start Service  master users Search");
		List<DepartmentMaster> departmentSearchList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
			sqlmu = "SELECT MD.DEPARTMENT_ID, MD.DEPARTMENT_CODE, MD.DESCRIPTION, MD.IS_ACTIVE, MD.IS_DELETED,\r\n"
					+ "					MD.MODIFIED_ON, MD.MODIFIED_BY FROM MASTER_DEPARTMENT MD\r\n"
					+ "					WHERE ((UPPER(MD.DEPARTMENT_CODE) LIKE UPPER('%'||?||'%') ) OR (? ='')) \r\n"
					+ "					AND ((UPPER(MD.DESCRIPTION) LIKE UPPER('%'||?||'%') ) OR (? =''))\r\n"
					+ "					order by md.modified_on desc";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setString(1, departmentCode);
				preparestatements.setString(2, departmentCode);
				preparestatements.setString(3, description);
				preparestatements.setString(4, description);
				
				try(ResultSet rs = preparestatements.executeQuery();)
				{
	
					departmentSearchList = new ArrayList<>();
					while (rs.next()) {
						DepartmentMaster departmentMaster = new DepartmentMaster();
						
						departmentMaster.setDepartmentId(rs.getLong("DEPARTMENT_ID"));
						departmentMaster.setModifiedBy(rs.getString("MODIFIED_BY"));
						departmentMaster.setModifiedOn(rs.getDate("MODIFIED_ON"));
						departmentMaster.setDescription(rs.getString("DESCRIPTION"));
					//	departmentMaster.setIsActive(rs.getString("IS_ACTIVE"));
					//	departmentMaster.setIsDeleted(rs.getString("IS_DELETED"));
						departmentMaster.setDepartmentCode(rs.getString("DEPARTMENT_CODE"));
						
						departmentSearchList.add(departmentMaster);
					}	
		
					logger.info("department code Search executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getSearchByDepartmentCode exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getSearchByDepartmentCode exception occured." + e.getMessage());
			}
			return departmentSearchList;
		}  catch (Exception e) {
			logger.error("exception occured." + e.getMessage());
			throw new SQLException ("Internal server error");
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}

	}
	
}

package com.lic.epgs.mst.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.entity.CitySearchEntity;
import com.lic.epgs.mst.entity.DesignationMaster;
import com.lic.epgs.mst.exceptionhandling.DesignationServiceException;

public interface DesignationService {

	List<DesignationMaster> getAllDesignation();

	List<DesignationMaster> getAllDesignationByCondition();

	public DesignationMaster getDesignationById(long id);

	public DesignationMaster getDesignationtByCode(String code);

	ResponseEntity<Map<String, Object>> addDesignation(DesignationMaster designation);

	ResponseEntity<Map<String, Object>> updateDesignation(DesignationMaster designation);

	// void deleteDesignation(Long id);

	public ResponseEntity<Map<String, Object>> deleteDesignation(Long designationId) throws DesignationServiceException;

	List<DesignationMaster> getSearchByDesignationCode(String designationCode, String description,String officeType) throws SQLException;

}

package com.lic.epgs.mst.usermgmt.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.FunctionalAreaEntity;


public interface FunctionalAreaService {
	List<FunctionalAreaEntity> getAllFunctionalArea();
	
	public ResponseEntity<Map<String, Object>> addFuncionalArea(FunctionalAreaEntity functionalAreaEntity) throws Exception;
	
	public ResponseEntity<Map<String, Object>> editFunctionalArea(FunctionalAreaEntity functionalAreaEntity) throws Exception;
	
	public ResponseEntity<Map<String, Object>> deleteFuctionalArea(Long functionId) throws Exception;
	
	public List<FunctionalAreaEntity> getSearchByFunctionName( String functionName) throws SQLException;
}

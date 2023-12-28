package com.lic.epgs.mst.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.modal.StateCodeModel;
import com.lic.epgs.mst.modal.UnitCodeModel;
import com.lic.epgs.mst.modal.UnitTypeModel;
import com.lic.epgs.mst.modal.UnitTypeModelDescription;

public interface UnitCodeService {

	public List<UnitCodeModel> getDisCodeStatCodeByunitCode(String unitcode) throws Exception;
	
	public StateCodeModel getStatCodeByunitCode(String unitcode) throws Exception;

	public List<UnitTypeModel> getOperatingUnitTypeByunitCode(String unitCode) throws Exception;
	
	public UnitTypeModelDescription getOperatingUnitTypeAndDescriptionByunitCode(String unitCode) throws Exception;
	
	public ResponseEntity<Object> getMasterUnitStreamMapping(String unitCode) throws SQLException;
}

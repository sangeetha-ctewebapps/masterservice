package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.UnitEntity;
import com.lic.epgs.mst.entity.UnitOffice;
import com.lic.epgs.mst.entity.UnitStateEntity;
import com.lic.epgs.mst.modal.StateUnitModel;
import com.lic.epgs.mst.modal.UnitModel;
import com.lic.epgs.mst.modal.UserStatecodeGstinModel;

public interface UnitOfficeService {
	
	public List<UnitEntity> getAllUnitOffice();
	
	 public List<UnitOffice> getAllUnitOfficeByCondition();

	 public UnitOffice getUnitOfficeById(long unitId);

	public UnitOffice findByUnitOfficeCode(String unitCode);
	
	UnitOffice addUnitOffice(long zonalId, UnitOffice unit);

	UnitEntity updateUnitOffice(long zonalId, long unitId, UnitEntity unit);

	void deleteUnitOffice(long id);
	
	public UnitModel getServiceDetailsByUnitCode(String unitCode) throws Exception;
	
	public UserStatecodeGstinModel getLoggedinuserdetailsSearch(String username) throws Exception;
	
	public List<UnitStateEntity> getAllUnitsByStateCode(String stateCode);
	
	public StateUnitModel getAllStatesByUnitCode(String unitCode) ;

}
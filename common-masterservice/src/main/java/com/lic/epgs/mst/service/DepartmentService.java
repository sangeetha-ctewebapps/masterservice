package com.lic.epgs.mst.service;

import java.sql.SQLException;
import java.util.List;

import com.lic.epgs.mst.entity.CitySearchEntity;
import com.lic.epgs.mst.entity.DepartmentMaster;

public interface DepartmentService {

	List<DepartmentMaster> getAllDepartment();
	
	List<DepartmentMaster> getAllDepartmentByCondition();

	public DepartmentMaster getDepartmentById(long id);

	public DepartmentMaster getDepartmentByCode(String code);

	DepartmentMaster addDepartment(DepartmentMaster department);

	DepartmentMaster updateDepartment(DepartmentMaster department);

	void deleteDeparment(Long id);

	 List<DepartmentMaster> getSearchByDepartmentCode(String departmentCode, String description) throws SQLException;

}

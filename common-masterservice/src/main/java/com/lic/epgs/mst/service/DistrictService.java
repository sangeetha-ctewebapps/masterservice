package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.entity.District;
import com.lic.epgs.mst.entity.DistrictEntity;
import com.lic.epgs.mst.exceptionhandling.DistrictServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface DistrictService {

	List<DistrictEntity> getAllDistrict() throws ResourceNotFoundException, DistrictServiceException;

	public DistrictEntity getDistrictById(long districtId);

	public District findByDistrictCode(String districtCode);

	District createDistrict(long stateId, District district);

	DistrictEntity updateDistrict(long stateId, long districtId, DistrictEntity district);

	public ResponseEntity<Map<String, Object>> deleteDistrict(Long districtId) throws DistrictServiceException;

	List<District> getAllDistrictByCondition();

	List<DistrictEntity> getAllDistrictByStateId(String stateId);
}

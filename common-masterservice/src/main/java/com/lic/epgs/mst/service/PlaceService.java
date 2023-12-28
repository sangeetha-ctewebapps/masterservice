package com.lic.epgs.mst.service;

import java.util.List;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.lic.epgs.mst.service.PlaceService;
import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.modal.PlaceModel;

public interface PlaceService {

	public List<PlaceModel> getAllPlacesById(Long districtId, Long stateId, Long countryId) throws Exception;
	
}

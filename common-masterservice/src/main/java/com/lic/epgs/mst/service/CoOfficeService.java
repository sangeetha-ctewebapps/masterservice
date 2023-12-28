package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.entity.CoOffice;

public interface CoOfficeService {

	List<CoOffice> getAllCoOffice();

	public CoOffice getCoOfficeById(long coId);

	public CoOffice findByCoCode(String cocode);
	
	CoOffice addCoOffice(CoOffice co);
	
	CoOffice updateCoOffice(CoOffice co);
	
	void deleteZonalOffice(Long id);
	
	public ResponseEntity<Map<String, Object>> deleteCoOffice(Long coId);

}

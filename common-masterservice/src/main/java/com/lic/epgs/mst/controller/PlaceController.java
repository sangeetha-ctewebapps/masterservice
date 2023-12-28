package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.controller.PlaceController;
import com.lic.epgs.mst.modal.PlaceModel;
import com.lic.epgs.mst.service.PlaceService;


@CrossOrigin("*")
@RestController
@RequestMapping(value = "/LIC_ePGS")
public class PlaceController {
	private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
	
	@Autowired
	PlaceService placeService;
	
	@PostMapping(value = "/getAllPlacesById")
	public ResponseEntity<Map<String, Object>> getAllPlacesById(@RequestBody PlaceModel placeModel) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 1);
		response.put(Constant.MESSAGE, Constant.SUCCESS);
		List<PlaceModel> Places = placeService.getAllPlacesById(placeModel.getDistrictId(),placeModel.getStateId(),placeModel.getCountryId());
		response.put("list", Places);
		return ResponseEntity.accepted().body(response);
	}

}

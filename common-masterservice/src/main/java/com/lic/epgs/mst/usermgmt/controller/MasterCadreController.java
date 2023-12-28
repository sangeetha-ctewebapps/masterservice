package com.lic.epgs.mst.usermgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.entity.MasterCadre;
import com.lic.epgs.mst.usermgmt.service.MasterCadreService;

@CrossOrigin("*")
@RestController
public class MasterCadreController {
	@Autowired
	private MasterCadreService cadreService;

	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(MasterCadreController.class);

	/*
	 * Description: This function is used for getting all the data from Cadres Module
	 * Table Name- MASTER_CADRE
	 * Author- Nandini R
	 */
	
	 @GetMapping("/usermgmt/getmastercadre")
	public ResponseEntity<Map<String, Object>> getAllCadres()  {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MasterCadre> cadres = cadreService.getAllCadres();

			if (cadres.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", cadres);
			}
			return ResponseEntity.accepted().body(response);

		} catch (Exception ex) {
			logger.error(" get master cadre  exception occured." + ex.getMessage());
		}
		return null;
	}

}
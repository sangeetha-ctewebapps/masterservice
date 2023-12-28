package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.entity.CauseOfSubEventEntity;
import com.lic.epgs.mst.service.CauseOfSubEventService;

@CrossOrigin("*")
@RestController

public class CauseOfSubEventController {
	

	@Autowired
	private CauseOfSubEventService causeOfSubEventService;


	 String className = this.getClass().getSimpleName();
	
	
	@GetMapping(value = "/causeOfSubEvent/{subCauseIndicator}")
	public ResponseEntity<Map<String, Object>> getCauseOfEventbyId(@PathVariable String subCauseIndicator) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		
		List<CauseOfSubEventEntity> subEvenslist = causeOfSubEventService.getAllCauseOfSubEvent(subCauseIndicator);
		if(subEvenslist == null || subEvenslist.isEmpty()) {
			response.put(Constant.STATUS, 10);
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		}
		else  {
			response.put(Constant.STATUS, 1);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put(Constant.DATA, subEvenslist);
		}
		
		return ResponseEntity.accepted().body(response);
	}

}

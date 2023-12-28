package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.SatelliteEntity;
import com.lic.epgs.mst.entity.SatelliteOffice;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.SatelliteServiceException;
import com.lic.epgs.mst.service.SatelliteOfficeService;

@RestController
@CrossOrigin("*")
public class SatelliteOfficeController {
	@Autowired
	private SatelliteOfficeService satelliteOfficeService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(SatelliteOfficeController.class);

	 @GetMapping("/satellite")
	public List<SatelliteEntity> getAllSatelliteOffice() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			logger.info("before list satellite ");
			List<SatelliteEntity> satelliteList = satelliteOfficeService.getAllSatelliteOffice();
			logger.info("inside all satellite list ");
			if (satelliteList.isEmpty()) {
				logger.debug("inside satellitesatelliteController getAllsatellite() method");
				logger.info("satellite type list is empty ");
				throw new ResourceNotFoundException("satellite not found");
			}
			return satelliteList;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}
	
	@GetMapping("/satelliteByCondition")
	public List<SatelliteOffice> getAllSatelliteOfficeByCondition() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			logger.info("before list satellite ");
			List<SatelliteOffice> satelliteList = satelliteOfficeService.getAllSatelliteOfficeByCondition();
			logger.info("inside all satellite list ");
			if (satelliteList.isEmpty()) {
				logger.debug("inside satellitesatelliteController getAllsatelliteByCondition() method");
				logger.info("satellite type list is empty ");
				throw new ResourceNotFoundException("satellite not found");
			}
			return satelliteList;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}
	
	@GetMapping("/satelliteById/{id}")
	public ResponseEntity<SatelliteOffice> getSatelliteOfficeById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(satelliteOfficeService.getSatelliteOfficeById(id));
	}

	@GetMapping("/satelliteByCode/{satellitecode}")
	public ResponseEntity<SatelliteOffice> getSatelliteOfficeCode(@PathVariable String satellitecode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("satellite search by satelliteCode");

		return ResponseEntity.ok().body(satelliteOfficeService.findBySatelliteOfficeCode(satellitecode));
	}

	/*
	 * @PostMapping("/addSatellite") public ResponseEntity<SatelliteOffice>
	 * addSatelliteOffice(@Valid @RequestBody SatelliteOffice satellite) {
	 * logger.info("satellite list is found" + satellite.toString()); return
	 * ResponseEntity.ok().body(satelliteOfficeService.addSatelliteOffice(satellite)
	 * ); }
	 */
	@PostMapping("/unit/satellite/{unitId}")
	public ResponseEntity <Map<String, Object>> addSatelliteOffice(@PathVariable(value = "unitId") 
	Long unitId, @Valid @RequestBody SatelliteOffice satellite) {
		logger.info("satellite list is found" + satellite.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		SatelliteOffice satelliteOffice = new SatelliteOffice();
		try {
			
		
		if (satellite.getSatelliteCode()!=null && !satellite.getSatelliteCode().isEmpty()) {
			
			satelliteOffice = satelliteOfficeService.findBySatelliteOfficeCode(satellite.getSatelliteCode());
		}
		else {
			
			erroMsgDto= new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
            response.put("status", 0);
            response.put("error", erroMsgDto);
            return ResponseEntity.badRequest().body(response);	
        }
		}
		catch (Exception e) {
			logger.error(" add satelite Office exception occured." + e.getMessage());
			throw new ResourceNotFoundException("internal server error");
		}
		if(satelliteOffice == null) {
			SatelliteOffice savesatellite= satelliteOfficeService.addSatelliteOffice(unitId, satellite);
			response.put("unitId", unitId);
			response.put("satellite", savesatellite);
			response.put("message", "Satellite Office created succesfully");
				return ResponseEntity.accepted().body(response);
		}
		else {
			response.put("message", "Satellite Office already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@PutMapping("/unit/{unitId}/satellite/{satelliteId}")
	public ResponseEntity <Map<String, Object>> updateSatelliteOffice(@PathVariable(value = "unitId") Long unitId,
			@PathVariable(value = "satelliteId") Long satelliteId, @Valid @RequestBody SatelliteEntity satellite) throws SatelliteServiceException{
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started"+satellite.getUnitId());
		satellite.setSatelliteId(satelliteId);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		SatelliteOffice satelliteOffice = null;
		SatelliteOffice satelliteOffice1 = null;
		if (null == satellite) {
			erroMsgDto= new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
            response.put("status", 0);
            response.put("error", erroMsgDto);
            return ResponseEntity.badRequest().body(response);	
        }
		try {
			satelliteOffice = satelliteOfficeService.findBySatelliteOfficeCode(satellite.getSatelliteCode());
			satelliteOffice1 = satelliteOfficeService.getSatelliteOfficeById(satelliteId);
		}
		catch (Exception e) {
			logger.error(" update Satelite Office  exception occured." + e.getMessage());
			throw new SatelliteServiceException("internal server error");
		}
		if(satelliteOffice == null || satelliteOffice.getSatelliteCode().equals(satelliteOffice1.getSatelliteCode()) ) {
			SatelliteEntity savesatellite= satelliteOfficeService.updateSatelliteOffice(unitId, satelliteId, satellite);
			response.put("unitId", satellite.getUnitId());
			response.put("satellite", savesatellite);
			response.put("message", "Satellite Office modified succesfully");
				return ResponseEntity.accepted().body(response);
		}
		else {
			response.put("message", "Satellite Office already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	
	@DeleteMapping(value = "/deleteSateliteOffice/{satelliteId}")
	public ResponseEntity<Map<String, Object>> deleteSatelliteOffice(@PathVariable("satelliteId") Long satelliteId) throws SatelliteServiceException{
		return satelliteOfficeService.deleteSateliteOffie(satelliteId);
	}
	

	/*@DeleteMapping("/deleteSatellite/{id}")
	public ResponseEntity<HttpStatus> deleteSatelliteOffice(@PathVariable Long id) {

		try {
			this.satelliteOfficeService.deleteSatelliteOffice(id);
			logger.info("satellites deleted with id" + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());

		}
	}*/

	
	
}

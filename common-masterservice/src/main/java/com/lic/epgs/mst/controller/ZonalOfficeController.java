package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.UnitStateEntity;
import com.lic.epgs.mst.entity.ZonalEntity;
import com.lic.epgs.mst.entity.ZonalOffice;
import com.lic.epgs.mst.exceptionhandling.CityServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ZonalOfficeService;

@RestController
@CrossOrigin("*")
public class ZonalOfficeController {
	@Autowired
	private ZonalOfficeService zonalOfficeService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ZonalOfficeController.class);

	@GetMapping("/zonal")
	 public List<ZonalEntity> getAllZonalOffice() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			logger.info("before list zonal ");
			List<ZonalEntity> zonalList = zonalOfficeService.getAllZonalOffice();
			logger.info("inside all zonal list ");
			if (zonalList.isEmpty()) {
				logger.debug("inside zonalatelliteController getAllzonal() method");
				logger.info("zonal Office list is empty ");
				throw new ResourceNotFoundException("zonal not found");
			}
			return zonalList;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}
	
	@GetMapping("/zonalByCondition")
	public List<ZonalOffice> getAllZonalOfficeByCondition() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			logger.info("before list zonal ");
			List<ZonalOffice> zonalList = zonalOfficeService.getAllZonalOfficeByCondition();
			logger.info("inside all zonal list ");
			if (zonalList.isEmpty()) {
				logger.debug("inside zonalatelliteController getAllzonalByCondition() method");
				logger.info("zonal Office list is empty ");
				throw new ResourceNotFoundException("zonal not found");
			}
			return zonalList;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}
	
	@GetMapping("/zonalById/{id}")
	public ResponseEntity<ZonalOffice> getZonalOfficeById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(zonalOfficeService.getZonalOfficeById(id));
	}

	@GetMapping("/zonalByCode/{zonalcode}")
	public ResponseEntity<ZonalOffice> getZonalOfficeCode(@PathVariable String zonalcode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("zonal search by zonalCode");

		return ResponseEntity.ok().body(zonalOfficeService.findByZonalOfficeCode(zonalcode));
	}
	
	@PostMapping("/addZonal")
	public ResponseEntity <Map<String, Object>>  addZonalOffice(@Valid @RequestBody ZonalOffice zonal) {
		logger.info("zonal list is found" + zonal.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		ZonalOffice zonalOffice = new ZonalOffice();
		try {
			
		
		if (zonal.getZonalCode()!=null && !zonal.getZonalCode().isEmpty()) {
			zonalOffice = zonalOfficeService.findByZonalOfficeCode(zonal.getZonalCode());
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
			logger.error(" add Zonal Office  exception occured." + e.getMessage());
			throw new ResourceNotFoundException ("Internal server error");
		}
		if(zonalOffice == null) {
			ZonalOffice savezonal= zonalOfficeService.addZonalOffice(zonal);
			response.put("zonal", savezonal);
			response.put("message", "Zonal Office created succesfully");
				return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "Zonal Office already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@PutMapping("/modifyZonal/{zonalId}")
	public ResponseEntity <Map<String, Object>>  updateZonalOffice(@PathVariable Long zonalId, @RequestBody ZonalOffice zonal) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		zonal.setZonalId(zonalId);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		ZonalOffice zonalOffice = null;
		ZonalOffice zonalOffice1 = null;
		if (null == zonal) {
			erroMsgDto= new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
            response.put("status", 0);
            response.put("error", erroMsgDto);
            return ResponseEntity.badRequest().body(response);	
        }
		try {
			zonalOffice = zonalOfficeService.findByZonalOfficeCode(zonal.getZonalCode());
			zonalOffice1 = zonalOfficeService.getZonalOfficeById(zonalId);
		}
		catch (Exception e) {
			logger.error(" modify Zonal Office  exception occured." + e.getMessage());
			throw new ResourceNotFoundException ("Internal server error");
		}
		if(zonalOffice == null || zonalOffice.getZonalCode().equals(zonalOffice1.getZonalCode())) {
			ZonalOffice savezonal= zonalOfficeService.updateZonalOffice(zonal);
			response.put("zonal", savezonal);
			response.put("message", "Zonal Office modified succesfully");
				return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "Zonal Office already exist");
			return ResponseEntity.accepted().body(response);
		}
	}
	
	@GetMapping("/getAllZoneDetailsByUnitCode/{unitCode}")
	public ResponseEntity<Map<String, Object>> getAllUnitsByStateCode(@PathVariable String unitCode) throws CityServiceException {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.FAILED);

		LoggingUtil.logInfo(className, methodName, "Started" + unitCode);
		try {
			ZonalEntity   cityList = zonalOfficeService.getAllZoneDetailsByUnitCode(unitCode);


			if ( cityList == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				logger.info("unit list is empty");
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", cityList);
			}
			return ResponseEntity.accepted().body(response);

		} catch (Exception ex) {
			logger.error("Exception in fetching Cities using disctrict id", ex.getMessage());
			throw new CityServiceException("internal server error");
		}

	}
	

	@DeleteMapping("/deleteZonal/{id}")
	public ResponseEntity<HttpStatus> deleteZonalOffice(@PathVariable long id) {

		try {
			this.zonalOfficeService.deleteZonalOffice(id);
			logger.info("zonal deleted with id" + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());

		}
	}

}

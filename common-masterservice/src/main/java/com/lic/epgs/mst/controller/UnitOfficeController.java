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
import com.lic.epgs.mst.entity.CityEntity;
import com.lic.epgs.mst.entity.StateUnitEntity;
import com.lic.epgs.mst.entity.UnitEntity;
import com.lic.epgs.mst.entity.UnitOffice;
import com.lic.epgs.mst.entity.UnitStateEntity;
import com.lic.epgs.mst.exceptionhandling.CityServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.modal.StateUnitModel;
import com.lic.epgs.mst.modal.UnitModel;
import com.lic.epgs.mst.modal.UserStatecodeGstinModel;
import com.lic.epgs.mst.service.UnitOfficeService;

@RestController
@CrossOrigin("*")
public class UnitOfficeController {
	@Autowired
	private UnitOfficeService unitOfficeService;
	
	

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(UnitOfficeController.class);

	 @GetMapping("/unit")
	public List<UnitEntity> getAllUnitOffice() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			logger.info("before list unit ");
			List<UnitEntity> unitList = unitOfficeService.getAllUnitOffice();
			logger.info("inside all unit list ");
			if (unitList == null) {
				logger.debug("inside unitatelliteController getAllunit() method");
				logger.info("unit type list is empty ");
				throw new ResourceNotFoundException("unit not found");
			}
			return unitList;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}
	
	@GetMapping("/unitByCondition")
	public List<UnitOffice> getAllUnitOfficeByCondition() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			logger.info("before list unit ");
			List<UnitOffice> unitList = unitOfficeService.getAllUnitOfficeByCondition();
			logger.info("inside all unit list ");
			if (unitList == null) {
				logger.debug("inside unitatelliteController getAllunitByCondition() method");
				logger.info("unit type list is empty ");
				throw new ResourceNotFoundException("unit not found");
			}
			return unitList;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}

	@GetMapping("/unitById/{id}")
	public ResponseEntity<UnitOffice> getUnitOfficeById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return ResponseEntity.ok().body(unitOfficeService.getUnitOfficeById(id));
	}

	@GetMapping("/unitByCode/{unitcode}")
	public ResponseEntity<UnitOffice> getUnitOfficeCode(@PathVariable String unitcode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("unit search by unitCode");
		return ResponseEntity.ok().body(unitOfficeService.findByUnitOfficeCode(unitcode));
	}

	@PostMapping("/zonal/unit/{zonalId}")
	public ResponseEntity <Map<String, Object>> addUnitOffice(@PathVariable(value = "zonalId") 
	Long zonalId, @Valid @RequestBody UnitOffice unit) {
		logger.info("unit list is found" + unit.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		UnitOffice unitOffice = new UnitOffice();
		try {
			
		
		if (unit.getUnitCode()!=null && !unit.getUnitCode().isEmpty()) {
			unitOffice = unitOfficeService.findByUnitOfficeCode(unit.getUnitCode());
			
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
			logger.error(" add Unit Office  exception occured." + e.getMessage());
			throw new ResourceNotFoundException("internal server error");
		}
		if(unitOffice == null) {
			UnitOffice saveunit= unitOfficeService.addUnitOffice(zonalId, unit);
			response.put("zonalId", zonalId);
			response.put("unit", saveunit);
			response.put("message", "Unit Office created succesfully");
				return ResponseEntity.accepted().body(response);
		}
		else {
			response.put("zonalId", zonalId);
			response.put("message", "Unit Office already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@PutMapping("/zonal/{zonalId}/unit/{unitId}")
	public ResponseEntity <Map<String, Object>> updateUnitOffice(@PathVariable(value = "zonalId") Long zonalId,
			@PathVariable(value = "unitId") Long unitId, @Valid  @RequestBody UnitEntity unit) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		unit.setUnitId(unitId);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto;
		UnitOffice unitOffice = null;
		UnitOffice unitOffice1 = null;
		if (null == unit) {
			erroMsgDto= new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("zonalId", zonalId);
            response.put("status", 0);
            response.put("error", erroMsgDto);
            return ResponseEntity.badRequest().body(response);	
        }
		try {
			unitOffice = unitOfficeService.findByUnitOfficeCode(unit.getUnitCode());
			unitOffice1 = unitOfficeService.getUnitOfficeById(unitId);
		}
		catch (Exception e) {
			logger.error(" update Unit Office  exception occured." + e.getMessage());
			throw new ResourceNotFoundException("internal server error");
		}
		if(unitOffice == null || unitOffice.getUnitCode().equals(unitOffice1.getUnitCode())) {
			UnitEntity saveunit= unitOfficeService.updateUnitOffice(zonalId, unitId, unit);
			response.put("zonalId", unit.getZonalId());
			response.put("unit", saveunit);
			response.put("message", "Unit Office modified succesfully");
				return ResponseEntity.accepted().body(response);
		}
		else {
			response.put("message", "Unit Office already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@DeleteMapping("/deleteUnit/{id}")
	public ResponseEntity<HttpStatus> deleteUnitOffice(@PathVariable Long id) {
		try {
			this.unitOfficeService.deleteUnitOffice(id);
			logger.info("unit deleted with id" + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/getAllUnitsByStateCode/{stateCode}")
	public ResponseEntity<Map<String, Object>> getAllUnitsByStateCode(@PathVariable String stateCode) throws CityServiceException {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.FAILED);

		LoggingUtil.logInfo(className, methodName, "Started" + stateCode);
		try {
			List<UnitStateEntity> cityList = unitOfficeService.getAllUnitsByStateCode(stateCode);


			if (cityList.isEmpty() || cityList == null) {
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

	 @GetMapping("/getAllStatesByunitCode/{unitCode}")
	 public ResponseEntity<Map<String, Object>> getAllStatesByStateCode(@PathVariable String unitCode) throws Exception {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.FAILED);
         
		//StateUnitModel objUnitModel = null;
		LoggingUtil.logInfo(className, methodName, "Started" + unitCode);
		try {
			StateUnitModel objUnitModel = unitOfficeService.getAllStatesByUnitCode(unitCode);


			if ( objUnitModel == null ) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				logger.info("State list is empty");
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", objUnitModel);
			}
			return ResponseEntity.accepted().body(response);

		} catch (Exception ex) {
			logger.error("Exception in fetching  using disctrict id", ex.getMessage());
			throw new Exception("internal server error");
		}

	}

	@GetMapping(value = "/getServiceDetailsByUnitCode/{unitCode}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> getServiceDetailsByUnitCode(@PathVariable String unitCode) throws ResourceNotFoundException {
		logger.info("Method Start--getServiceDetailsByUnitCode--");
		
		logger.info("unitCode::"+unitCode);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 1);
		response.put(Constant.MESSAGE, Constant.SUCCESS);
		
		UnitModel objUnitModel = null;
		try {
			objUnitModel = unitOfficeService.getServiceDetailsByUnitCode(unitCode);
			logger.info("UnitModel.getServiceDetailsByUnitCode::"+objUnitModel.getServicingUnit());
		} catch (Exception e) {
			logger.error(" update Unit Office  exception occured." + e.getMessage());
			throw new ResourceNotFoundException("internal server error");
			
		}
		
		if(objUnitModel==null) {
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		} else {
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", objUnitModel);
		}
		logger.info("Method End--getServiceDetailsByUnitCode--");
		return ResponseEntity.accepted().body(response);
	}
	
	
	@GetMapping(value = "/getLoggedinuserdetailsSearch/{username}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> getLoggedinuserdetailsSearch(@PathVariable String username) throws ResourceNotFoundException {
		logger.info("Method Start--getServiceDetailsByUnitCode--");
		
		logger.info("unitCode::"+username);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 1);
		response.put(Constant.MESSAGE, Constant.SUCCESS);
		
		UserStatecodeGstinModel objUnitModel = null;
		try {
			objUnitModel = unitOfficeService.getLoggedinuserdetailsSearch(username);
			logger.info("UnitModel.getServiceDetailsByUnitCode::"+objUnitModel.getUserUnitGstin());
		} catch (Exception e) {
			logger.error(" update Unit Office  exception occured." + e.getMessage());
			throw new ResourceNotFoundException("internal server error");
			
		}
		
		if(objUnitModel==null) {
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		} else {
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", objUnitModel);
		}
		logger.info("Method End--getServiceDetailsByUnitCode--");
		return ResponseEntity.accepted().body(response);
	}

}



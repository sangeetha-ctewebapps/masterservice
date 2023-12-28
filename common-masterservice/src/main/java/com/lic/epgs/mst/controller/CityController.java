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

import com.lic.epgs.constant.Constant;
import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.CityEntity;
import com.lic.epgs.mst.entity.CityMaster;
import com.lic.epgs.mst.entity.CitySearchEntity;
import com.lic.epgs.mst.exceptionhandling.CityServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CityEntityRepository;
import com.lic.epgs.mst.service.CityService;

@RestController
@CrossOrigin("*")
public class CityController {

	private static final Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private CityService cityService;
	String className = this.getClass().getSimpleName();

	@Autowired
	CityEntityRepository cityEntityRepository;

	@GetMapping("/city/{start_t}/{end_t}")
	public List<CityEntity> getAllCity(@PathVariable Long start_t, @PathVariable Long end_t)
			throws ResourceNotFoundException, CityServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CityEntity> city = cityService.getAllCity(start_t, end_t);

			if (city == null) {
				logger.debug("inside city controller getAllDepartmentType() method");
				logger.info("city list is empty ");
				throw new ResourceNotFoundException("city not found");
			}
			return city;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new CityServiceException("internal server error");
		}
	}

//	Start Commented by Vijay
	/*
	 * @GetMapping("/city") public List<CityEntity> getAllCity() throws
	 * ResourceNotFoundException, CityServiceException { String methodName =
	 * Thread.currentThread().getStackTrace()[1].getMethodName();
	 * LoggingUtil.logInfo(className, methodName, "Started");
	 * 
	 * try { List<CityEntity> city = cityService.getAllCity();
	 * 
	 * if (city == null) {
	 * logger.debug("inside city controller getAllDepartmentType() method");
	 * logger.info("city list is empty "); throw new
	 * ResourceNotFoundException("city not found"); } return city; } catch
	 * (Exception ex) { logger.error("Internal Server Error"); throw new
	 * CityServiceException("internal server error"); } }
	 * 
	 * @GetMapping("/cityByCondition") public List<CityMaster>
	 * getAllCityByCondition() throws ResourceNotFoundException,
	 * CityServiceException { String methodName =
	 * Thread.currentThread().getStackTrace()[1].getMethodName();
	 * LoggingUtil.logInfo(className, methodName, "Started");
	 * 
	 * try { List<CityMaster> city = cityService.getAllCityByCondition();
	 * 
	 * if (city == null) { logger.
	 * debug("inside city controller getAllDepartmentTypeByCondition() method");
	 * logger.info("city list is empty "); throw new
	 * ResourceNotFoundException("city not found"); } return city; } catch
	 * (Exception ex) { logger.error("Internal Server Error"); throw new
	 * CityServiceException("internal server error"); } }
	 */

//	End Commented by Vijay

	@GetMapping("/city/{id}")
	public ResponseEntity<CityEntity> getCityById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(cityService.getCitytById(id));

	}

	@GetMapping("/cityByCode/{code}")
	public ResponseEntity<CityMaster> getCityByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(cityService.getCityByCode(code));

	}

	/*
	 * @PostMapping("/addCity") public ResponseEntity<CityMaster>
	 * addCity(@RequestBody CityMaster city) { logger.info("city list is found" +
	 * city.toString()); return ResponseEntity.ok().body(cityService.addCity(city));
	 * }
	 */

	/*
	 * @PostMapping("/addCity") public ResponseEntity<?> addCity(@Valid @RequestBody
	 * CityMaster city) { logger.info("city list is found" + city.toString());
	 * CityMaster cityMaster = null; try { cityMaster =
	 * cityService.getCityByCode(city.getCityCode()); } catch (Exception e) { }
	 * if(cityMaster == null) { return
	 * ResponseEntity.ok().body(cityService.addCity(city)); } else { return
	 * ResponseEntity.ok().body("City Code Already Exists"); } }
	 */
	@PostMapping("/district/city/{id}")
	public ResponseEntity<Map<String, Object>> addCity(@PathVariable(value = "id") Long id,
			@Valid @RequestBody CityMaster city) throws CityServiceException {
		logger.info("city list is found" + city.toString());

		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		CityMaster cityMaster = new CityMaster();
		try {
			if (city.getCityCode() != null && !city.getCityCode().isEmpty()) {
				cityMaster = cityService.getCityByCode(city.getCityCode());
			} else {
				erroMsgDto = new ErrorMessageDTO();
				erroMsgDto.setCode("400");
				erroMsgDto.setMessage("Invalid RequestStates Payload.");
				response.put("status", 0);
				response.put("error", erroMsgDto);
				return ResponseEntity.badRequest().body(response);
			}

		} catch (Exception e) {
			logger.error(" add City  exception occured." + e.getMessage());
			throw new CityServiceException("internal server error");
		}
		if (cityMaster == null) {
			CityMaster saveCity = cityService.addCity(id, city);
			response.put("districtId", id);
			response.put("city", saveCity);
			response.put("message", " city created succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", " city already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@PutMapping("/district/{id}/city/{cityId}")
	public ResponseEntity<Map<String, Object>> updateCity(@PathVariable(value = "id") Long id,
			@PathVariable(value = "cityId") Long cityId, @Valid @RequestBody CityEntity city)
			throws CityServiceException {
//		city.setCityId(cityId);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		CityMaster cityMaster = null;
		CityEntity cityMaster1 = null;
		if (city != null) {
			try {
				cityMaster = cityService.getCityByCode(city.getCityCode());
				cityMaster1 = cityService.getCitytById(cityId);

				if (cityMaster == null || cityMaster.getCityCode().equals(cityMaster1.getCityCode())) {
					CityEntity saveCity = cityService.updateCity(id, cityId, city);
					response.put("districtId", city.getDistrictId());
					response.put("city", saveCity);
					response.put("message", " city modified succesfully");
					return ResponseEntity.accepted().body(response);
				} else {
					response.put("message", " city already exist");
					return ResponseEntity.accepted().body(response);
				}
			} catch (Exception e) {
				logger.error(" update City  exception occured." + e.getMessage());
				throw new CityServiceException("internal server error");
			}
		} else {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}

	}

	@DeleteMapping(value = "/deleteCity/{cityId}")
	public ResponseEntity<Map<String, Object>> deleteCity(@PathVariable("cityId") Long cityId) {
		return cityService.deleteCity(cityId);
	}

	@GetMapping("/getAllCityByDistrictId/{districtId}/{startAt}")
	public ResponseEntity<Map<String, Object>> getAllCityByDistrictId(@PathVariable Long districtId,
			@PathVariable Long startAt) throws CityServiceException {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.FAILED);

		LoggingUtil.logInfo(className, methodName, "Started" + districtId);
		try {
			List<CityEntity> cityList = cityService.getAllCityByDistrictId(districtId, startAt);

			int resultCount = cityEntityRepository.findCityCount(districtId);

			if (cityList.isEmpty() || cityList == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				logger.info("City list is empty");
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", cityList);
				response.put("Count", resultCount);
			}
			return ResponseEntity.accepted().body(response);

		} catch (Exception ex) {
			logger.error("Exception in fetching Cities using disctrict id", ex.getMessage());
			throw new CityServiceException("internal server error");
		}

	}

	@PostMapping(("/getSearchByCityCode"))
	ResponseEntity<Map<String, Object>> getSearchByCityCode(
			@RequestBody(required = false) CitySearchEntity citySearchEntity)
			throws ResourceNotFoundException, CityServiceException {

		logger.info("Method Start--CityCode--");

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<CitySearchEntity> mstResponse = cityService.getSearchByCityCode(citySearchEntity.getCityCode(),
					citySearchEntity.getDescription());
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);

			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			logger.error(" get Search By City Code  exception occured." + e.getMessage());
			throw new CityServiceException("internal server error");
		}

	}
	
	@GetMapping("/getAllCityByStateCode/{stateCode}")
	public ResponseEntity<Map<String, Object>> getAllCityByStateCode(@PathVariable String stateCode) throws CityServiceException {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.FAILED);

		LoggingUtil.logInfo(className, methodName, "Started" + stateCode);
		try {
			List<CityEntity> cityList = cityService.getAllCitiesByStateCode(stateCode);


			if (cityList.isEmpty() || cityList == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				logger.info("City list is empty");
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

	@GetMapping(value = "/getCityByDistrictId/{districtId}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> getCityByDistrictId(@PathVariable Long districtId)
			throws ResourceNotFoundException, CityServiceException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		logger.info("Method Start--getUserRoleByLocation--");

		try {
			List<CityEntity> objCityEntity = cityService.getCityByDistrictId(districtId);
			// logger.info("CityEntity.getCityByDistrictId::"+objCityEntity.getCityByDistrictId());
			if (objCityEntity == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", objCityEntity);
			}
			logger.info("Method End--getCityByDistrictId--");
			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage());
			throw new CityServiceException("internal server error");
		}
	}

}

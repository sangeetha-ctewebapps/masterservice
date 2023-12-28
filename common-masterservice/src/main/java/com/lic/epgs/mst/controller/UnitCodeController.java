package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.entity.CoOffice;
import com.lic.epgs.mst.entity.UnitCodeEntity;
import com.lic.epgs.mst.entity.UnitOffice;
import com.lic.epgs.mst.entity.ZonalOffice;
import com.lic.epgs.mst.exceptionhandling.UnitCodeServiceException;
import com.lic.epgs.mst.modal.StateCodeModel;
import com.lic.epgs.mst.modal.UnitCodeModel;
import com.lic.epgs.mst.modal.UnitTypeModel;
import com.lic.epgs.mst.modal.UnitTypeModelDescription;
import com.lic.epgs.mst.repository.CoOfficeRepository;
import com.lic.epgs.mst.repository.UnitOfficeRepository;
import com.lic.epgs.mst.repository.ZonalOfficeRepository;
import com.lic.epgs.mst.service.UnitCodeService;

@RestController
@CrossOrigin("*")
public class UnitCodeController {
	@Autowired
	private UnitCodeService unitCodeService;
	
	@Autowired
	ZonalOfficeRepository zonalOfficeRepository;
	
	@Autowired
	private CoOfficeRepository  coOfficeRepository;
	
	@Autowired
	private UnitOfficeRepository unitOfficeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(UnitCodeController.class);
	
	

	@PostMapping(("/getDisCodeStatCodeByunitCode"))
	ResponseEntity<Map<String, Object>> getUnitCodeSearch(
			@RequestBody(required = false) UnitCodeEntity unitCodeEntity) throws Exception {
		{

			logger.info("Method Start--getUnitCodeSearch-");
		}

		logger.info("UNIT_CODE:" + unitCodeEntity.getUnitCode()) ;

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		List<UnitCodeModel> mstResponse = unitCodeService.getDisCodeStatCodeByunitCode(unitCodeEntity.getUnitCode());
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);

		try {
			
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error(" get Unit Code search  exception occured." + ex.getMessage());
			throw new UnitCodeServiceException ("Internal server error");
		}
	}

	@GetMapping(("/getStatCodeByunitCode"))
	ResponseEntity<Map<String, Object>> getStatCodeByunitCode(@RequestParam("unitCode") String unitCode) throws Exception {
		{

			logger.info("Method Start--getUnitCodeSearch-");
		}

		logger.info("UNIT_CODE:" + unitCode) ;

		Map<String, Object> response = new HashMap<String, Object>();
 		  response.put(Constant.STATUS, 0);
		  response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		StateCodeModel mstResponse = unitCodeService.getStatCodeByunitCode(unitCode);
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);

		try {
			
			if (mstResponse == null ) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error(" get Unit Code search  exception occured." + ex.getMessage());
			throw new UnitCodeServiceException ("Internal server error");
		}
	}
	
	
	@GetMapping(("/getOperatingUnitTypeByunitCode"))
	ResponseEntity<Map<String, Object>> getOperatingUnitTypeByunitCode(@RequestParam("unitCode") String unitCode) throws Exception {
		{

			logger.info("Method Start--getUnitCodeSearch-");
		}

		logger.info("UNIT_CODE:" + unitCode) ;

		Map<String, Object> response = new HashMap<String, Object>();
 		  response.put(Constant.STATUS, 0);
		  response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		List<UnitTypeModel> mstResponse = unitCodeService.getOperatingUnitTypeByunitCode(unitCode);
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);

		try {
			
			if (mstResponse == null ) {
				response.put(Constant.STATUS,201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS,200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data",mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error(" get Unit Code search  exception occured." + ex.getMessage());
			throw new UnitCodeServiceException ("Internal server error");
		}
	}
	
	
	@GetMapping(("/getLocationByUnitCode"))
	ResponseEntity<Map<String, Object>> getLocationByUnitCode(@RequestParam("unitCode") String unitCode) throws Exception {
		{

			logger.info("Method Start--getUnitCodeSearch-");
		}

		logger.info("UNIT_CODE:" + unitCode) ;

		Map<String, Object> response = new HashMap<String, Object>();
 		  response.put(Constant.STATUS, 0);
		  response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		  UnitTypeModelDescription mstResponse = unitCodeService.getOperatingUnitTypeAndDescriptionByunitCode(unitCode);
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);

		try {
			
			if (mstResponse == null ) {
				response.put(Constant.STATUS,201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				 response.put(Constant.STATUS,200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error(" get Unit Code search  exception occured." + ex.getMessage());
			throw new UnitCodeServiceException ("Internal server error");
		}
	}
 
	@GetMapping(("/getOperatingUnitTypeAndDescriptionByunitCode"))
	ResponseEntity<Map<String, Object>> getOperatingUnitTypeAndDescriptionByunitCode(@RequestParam("unitCode") String unitCode,@RequestParam("loggedInUserUnitCode") String loggedInUserUnitCode) throws Exception {
		

			logger.info("Method Start--getUnitCodeSearch-");
		logger.info("UNIT_CODE:" + unitCode) ;

		Map<String, Object> response = new HashMap<String, Object>();
 		  response.put(Constant.STATUS, 0);
		  response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		UnitTypeModelDescription unitCodeModel = unitCodeService.getOperatingUnitTypeAndDescriptionByunitCode(unitCode);
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + unitCodeModel);

		try {
			
			if (unitCodeModel.getOperatingUnitType() == null && unitCodeModel.getUnitDescription() ==null) {
				response.put(Constant.STATUS, 201);
				response.put("Data", unitCodeModel);
				response.put(Constant.MESSAGE, "User does not belong to the allowed location type");
			}
			else {
				 if (unitCodeModel.getOperatingUnitType().equalsIgnoreCase("CO")) {
					CoOffice co   = coOfficeRepository.getDetailsByCoCode(loggedInUserUnitCode);
					if(unitCodeModel.getOperatingUnitType().equalsIgnoreCase("CO") && co!= null && co.getCo_code().equalsIgnoreCase(unitCode)) {
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", unitCodeModel);
					}
					else {
						response.put(Constant.STATUS, 201);
						response.put(Constant.MESSAGE, "Invalid srNumber, loggedin user does not belong to Central Office");
						response.put("Data", unitCodeModel);
						//response.put("Data", mstResponse);
					}
				}
				 else if (unitCodeModel.getOperatingUnitType().equalsIgnoreCase("ZO")) {
				ZonalOffice zoOfficeDetails=zonalOfficeRepository.getZonalOfficeDetails(loggedInUserUnitCode);
				
				if(zoOfficeDetails==null ) {
					CoOffice co   = coOfficeRepository.getDetailsByCoCode(loggedInUserUnitCode);
					List<ZonalOffice>  cos =zonalOfficeRepository.getAllZonesbasedOnCoId(co.getCoId());
					boolean zonalPresent = false;
					
					 for (ZonalOffice zonalOffice : cos) {
						 if (zonalOffice.getZonalCode().equalsIgnoreCase(unitCode)) {
							 zonalPresent = true;
							 
							  } 
						 try {
						  if (zonalPresent) {
	                    	  response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", unitCodeModel);
								
							}
						 else  {
	                    	  response.put(Constant.STATUS, 201);
								response.put(Constant.MESSAGE, "This Zone does not belong to the particular Central Unit");
								response.put("Data", unitCodeModel);
	                      }
						 }catch (Exception ex) {
								logger.error(" get Unit Code search  exception occured." + ex.getMessage());
								throw new UnitCodeServiceException ("Internal server error");
							}
					 }
					 }
				else  if(unitCodeModel.getOperatingUnitType().equalsIgnoreCase("ZO") && zoOfficeDetails !=null && zoOfficeDetails.getZonalCode().equalsIgnoreCase(unitCode)) {
							response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", unitCodeModel);
						}
			       else {
						response.put(Constant.STATUS, 201);
						response.put(Constant.MESSAGE, "Invalid Srnumber, loggedin user does not belong to Zonal Office");
						response.put("Data", unitCodeModel);
						//response.put("Data", mstResponse);
					}
						
					} 
				 else if (unitCodeModel.getOperatingUnitType().equalsIgnoreCase("UO")) {
						UnitOffice unit = unitOfficeRepository.getAllUnitDetails(loggedInUserUnitCode);
						CoOffice co   = coOfficeRepository.getDetailsByCoCode(loggedInUserUnitCode);
						
//						if(unit==null && co!=null) {
//							
//							CoOffice codetails   = coOfficeRepository.getDetailsByCoCode(loggedInUserUnitCode);
//							
//							try {
////								  if (codetails != null && codetails.getCo_code().equalsIgnoreCase(unitCode)) {
////			                    	  response.put(Constant.STATUS, 200);
////										response.put(Constant.MESSAGE, Constant.SUCCESS);
////										response.put("Data", unitCodeModel);
////										
////									}
////								 else  {
////			                    	  response.put(Constant.STATUS, 201);
////										response.put(Constant.MESSAGE, "Unit User cannot be added in central Office");
////										response.put("Data", unitCodeModel);
////			                      }
//								
//								 
//							}catch (Exception ex) {
//								logger.error(" get Unit Code search  exception occured." + ex.getMessage());
//								throw new UnitCodeServiceException ("Internal server error");
//							}
//							
//						}
			
						 if(unit==null && co ==null) {
						ZonalOffice zoOffice=zonalOfficeRepository.getZonalOfficeDetails(loggedInUserUnitCode);
						List<UnitOffice>  Units=unitOfficeRepository.getUnnitsFromZonalCode(zoOffice.getZonalId());
						boolean unitPresent = false;
						
						 for (UnitOffice unitOffice : Units) {
							 if (unitOffice.getUnitCode().equalsIgnoreCase(unitCode)) {
								 unitPresent = true;
								 
								  } 
							 try {
							  if (unitPresent) {
		                    	  response.put(Constant.STATUS, 200);
									response.put(Constant.MESSAGE, Constant.SUCCESS);
									response.put("Data", unitCodeModel);
									
								}
							 else  {
		                    	  response.put(Constant.STATUS, 201);
									response.put(Constant.MESSAGE, "This unit does not belong to the particular Zonal Unit");
									response.put("Data", unitCodeModel);
		                      }
							
							 
						}catch (Exception ex) {
							logger.error(" get Unit Code search  exception occured." + ex.getMessage());
							throw new UnitCodeServiceException ("Internal server error");
						}
						 }
						}
						else if(unitCodeModel.getOperatingUnitType().equalsIgnoreCase("UO") && unit!=null && unit.getUnitCode().equalsIgnoreCase(unitCode)) {
						   response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", unitCodeModel);
					}
						
	                      
//	                      else {
//	  						response.put(Constant.STATUS, 201);
//	  						response.put(Constant.MESSAGE, "Invalid Srnumber, loggedin user does not belong to Unit Office");
//	  						response.put("Data", unitCodeModel);
//	  						//response.put("Data", mstResponse);
//	  					}
	                      
						 response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", unitCodeModel);
			}
			 
			

			return ResponseEntity.ok().body(response);
			}
		}catch (Exception ex) {
			logger.error(" get Unit Code search  exception occured." + ex.getMessage());
			throw new UnitCodeServiceException ("Internal server error");
		}
		return ResponseEntity.ok().body(response);
	
	

}
	
	//APi to get master unit stream mapping
		@GetMapping(("/getMasterUnitStreamMapping"))
		 public ResponseEntity<Object> getMasterUnitStreamMapping(@RequestParam("unitCode") String unitCode) throws Exception {
			return ResponseEntity.status(HttpStatus.OK).body(unitCodeService.getMasterUnitStreamMapping(unitCode));
		}
		
}
	
	
	

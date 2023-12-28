
package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.StateUnitEntity;
import com.lic.epgs.mst.entity.UnitEntity;
import com.lic.epgs.mst.entity.UnitOffice;
import com.lic.epgs.mst.entity.UnitStateEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.modal.StateUnitModel;
import com.lic.epgs.mst.modal.UnitModel;
import com.lic.epgs.mst.modal.UserStatecodeGstinModel;
import com.lic.epgs.mst.repository.StateUnitRepository;
import com.lic.epgs.mst.repository.UnitEntityRepository;
import com.lic.epgs.mst.repository.UnitOfficeRepository;
import com.lic.epgs.mst.repository.UnitStateRepository;
import com.lic.epgs.mst.repository.ZonalOfficeRepository;

@Service

@Transactional

public class UnitOfficeServiceImpl implements UnitOfficeService {

	@Autowired
	UnitOfficeRepository unitOfficeRepository;
	
	@Autowired
	UnitStateRepository  unitStateRepository;

	@Autowired
	private ZonalOfficeRepository zonalOfficeRepository;
	
	@Autowired
	UnitEntityRepository unitEntityRepository;
	
	@Autowired
	StateUnitRepository stateUnitRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(UnitOfficeServiceImpl.class);

	@Override

	public List<UnitEntity> getAllUnitOffice() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("i get unit type list ");
		LoggingUtil.logInfo(className, methodName, "Started");
		List<UnitEntity> objUnitEntity= unitEntityRepository.findAllWithZonalIds();
		return objUnitEntity;
	}
	
	public List<UnitOffice> getAllUnitOfficeByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("i get unit type list ");
		LoggingUtil.logInfo(className, methodName, "Started");
		return unitOfficeRepository.findAllByCondition();
	}

	@Override

	public UnitOffice getUnitOfficeById(long unitId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<UnitOffice> unitDb = this.unitOfficeRepository.findById(unitId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for unit By Id" + unitId);
		if (unitDb.isPresent()) {
			logger.info("unit  type is found with id" + unitId);
			return unitDb.get();
		} else {
			throw new ResourceNotFoundException("unit not found with id:" + unitId);
		}
	}

	@Override

	public UnitOffice findByUnitOfficeCode(String unitCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		UnitOffice unitDb = this.unitOfficeRepository.findByUnitOfficeCode(unitCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search unit details By unitCode with " + unitCode);

		if (unitDb != null ) {
			logger.info("unitCode is present with code" + unitCode);

			return unitDb;
		} else {
			throw new ResourceNotFoundException("unitCode not found with code :" + unitCode);
		}
	}
	
	@Override
	public List<UnitStateEntity> getAllUnitsByStateCode(String stateCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<UnitStateEntity> cityList= unitStateRepository.getAllUnitbasedOnStateCode(stateCode);
		return cityList;
	}
	
	
	  public StateUnitModel getAllStatesByUnitCode(String unitCode) {
		logger.info("Start getServiceDetailsByUnitCode");
		 
		StateUnitModel unitModel = new StateUnitModel();
		
		logger.info("unitCode--"+unitCode);
		
		StateUnitEntity objUnitEntity = stateUnitRepository.getStatesByCode(unitCode);
		unitModel.setStateCode(objUnitEntity.getStateCode());
		unitModel.setDescription(objUnitEntity.getDescription());
		
		//unitModel.setUnitEmailId(objUnitEntity.getEmailId());
		//unitModel.setUnitCode(unitCode+"");
		logger.info("End getClaimAmountDetails");
		
		return unitModel;
	}

	@Override
	public UnitOffice addUnitOffice(long zonalId, UnitOffice unit) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return zonalOfficeRepository.findById(zonalId).map(zonal -> {
			unit.setZonal(zonal);
		return unitOfficeRepository.save(unit);
		}).orElseThrow(() -> new ResourceNotFoundException("zonalId " + zonalId + " not found"));
	}
	
	@Override

	public UnitEntity updateUnitOffice(long zonalId, long unitId, UnitEntity unit) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		if (!zonalOfficeRepository.existsById(zonalId)) {
			throw new ResourceNotFoundException("CountryId " + zonalId + " not found");
		}
		return unitEntityRepository.findById(unitId).map(unitUpdate -> {
		/*
		 * Optional<UnitOffice> unitDb =
		 * this.unitOfficeRepository.findById(unit.getUnitId()); if (unitDb.isPresent())
		 * { UnitOffice unitUpdate = unitDb.get();
		 * unitUpdate.setUnitId(unit.getUnitId());
		 */
			unitUpdate.setZonalId(unit.getZonalId());
			unitUpdate.setDescription(unit.getDescription());
			unitUpdate.setUnitCode(unit.getUnitCode());
			unitUpdate.setIsActive(unit.getIsActive());
			unitUpdate.setModifiedBy(unit.getModifiedBy());
			unitUpdate.setPincode(unit.getPincode());
			unitUpdate.setCityName(unit.getCityName());
			unitUpdate.setDistrictName(unit.getDistrictName());
			unitUpdate.setStateName(unit.getStateName());
			unitUpdate.setAddress1(unit.getAddress1());
			unitUpdate.setAddress2(unit.getAddress2());
			unitUpdate.setAddress3(unit.getAddress3());
			unitUpdate.setAddress4(unit.getAddress4());
			unitUpdate.setEmailId(unit.getEmailId());
			unitUpdate.setTelephoneNo(unit.getTelephoneNo());
			unitUpdate.setTin(unit.getTin());
			unitUpdate.setGstIn(unit.getGstIn());
			return unitEntityRepository.save(unitUpdate);
		}).orElseThrow(() -> new ResourceNotFoundException("unitId " + unitId + " not found"));
		}
	/*		unitOfficeRepository.save(unitUpdate);
			return unitUpdate;
		} else {
			throw new ResourceNotFoundException("Record not found with id:" + unit.getUnitId());
		}
	}*/
	
	@Override
	public void deleteUnitOffice(long unitId) {
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	LoggingUtil.logInfo(className, methodName, "Started");
	Integer unitDB = this.unitOfficeRepository.findDeletedById(unitId);
	LoggingUtil.logInfo(className, methodName, "delete unitOffice ById" + unitId);
	if (unitDB != null) {
	logger.info("unitOffice soft not deleted with id " + unitId);

	} else {
	throw new ResourceNotFoundException("unitOffice soft not found :" + unitId);
	}
	}


	
	

	@Override
	 public UnitModel getServiceDetailsByUnitCode(String unitCode) throws Exception {
		logger.info("Start getServiceDetailsByUnitCode");
		 
		UnitModel unitModel = new UnitModel();
		
		logger.info("unitCode--"+unitCode);
		
		UnitEntity objUnitEntity = unitEntityRepository.getServiceDetailsByUnitCode(unitCode);
		unitModel.setServicingUnitGstNumber(objUnitEntity.getGstIn());
		unitModel.setServicingUnit(objUnitEntity.getDescription());
		unitModel.setServicingUnitAddress1(objUnitEntity.getAddress1());
		unitModel.setServicingUnitAddress2(objUnitEntity.getAddress2());
		unitModel.setServicingUnitAddress3(objUnitEntity.getAddress3());
		unitModel.setServicingUnitAddress4(objUnitEntity.getAddress4());
		//unitModel.setUnitEmailId(objUnitEntity.getEmailId());
		//unitModel.setUnitCode(unitCode+"");
		logger.info("End getClaimAmountDetails");
		
		return unitModel;
	}

	@Override
	public UserStatecodeGstinModel getLoggedinuserdetailsSearch(String username) throws Exception {
		logger.info("Start getLoggedinuserdetailsSearch");
		 
		UserStatecodeGstinModel unitModel = new UserStatecodeGstinModel();
		
		logger.info("unitCode--"+username);
		
		UnitStateEntity objUnitStateEntity =unitStateRepository.getLoggedinuserdetailsSearch(username);
		unitModel.setUserUnitPan(objUnitStateEntity.getPan());
         unitModel.setUserUnitGstin(objUnitStateEntity.getGstIn());
         unitModel.setUserStateCode(objUnitStateEntity.getStateCode());
         unitModel.setStateCode(objUnitStateEntity.getCode());
         
         logger.info("End getLoggedinuserdetailsSearch");
         
         return unitModel;
}
	
}




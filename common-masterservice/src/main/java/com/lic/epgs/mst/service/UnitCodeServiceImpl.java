package com.lic.epgs.mst.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.modal.StateCodeModel;
import com.lic.epgs.mst.modal.UnitCodeModel;
import com.lic.epgs.mst.modal.UnitTypeModel;
import com.lic.epgs.mst.modal.UnitTypeModelDescription;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.repository.UnitCodeRepository;

@Service
@Transactional
public class UnitCodeServiceImpl implements UnitCodeService {
	
	@Autowired
	UnitCodeRepository unitCodeRepository;
	
	
	@Autowired
	private JDBCConnection jdbcConnection;

	
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(UnitCodeServiceImpl.class);

	@Override
	 public List<UnitCodeModel> getDisCodeStatCodeByunitCode(String unitCode) throws SQLException {
		// TODO Auto-generated method stub
		
		logger.info("Start Service  unitcode Search");
		List<UnitCodeModel> adminModelList2 = null;
		String sqlzo = null;
		Connection connection1 = jdbcConnection.getConnection();

		try {
			
			sqlzo = "SELECT distinct MU.UNIT_CODE,MS.STATE_CODE,MD.DISTRICT_CODE FROM MASTER_UNIT MU,master_city mc, MASTER_STATE MS, MASTER_DISTRICT MD  where\r\n" + 
					"lower(MU.DISTRICT_NAME)=lower(MD.DESCRIPTION) and lower(MU.STATE_NAME)=lower(MS.DESCRIPTION)\r\n" + 
					"and mc.DISTRICT_ID=MD.DISTRICT_ID and MD.STATE_ID=MS.STATE_ID and MU.UNIT_CODE= ?";

			try(PreparedStatement preparestatements2 = connection1.prepareStatement(sqlzo);)
			{
				preparestatements2.setString(1, unitCode);
				//preparestatements2.setString(2, unitCode);
				
	
				try(ResultSet rs2 = preparestatements2.executeQuery();)
				{
	
					adminModelList2 = new ArrayList<>();
					while (rs2.next()) {
						UnitCodeModel unitCodeModel = new UnitCodeModel();
						unitCodeModel.setUnitCode(rs2.getString(1));
						unitCodeModel.setStateCode(rs2.getString(2));
						unitCodeModel.setDistrictCode(rs2.getString(3));
						adminModelList2.add(unitCodeModel);
					}
		
					logger.info("zoSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getDisCodeStatCodeByunitCode Exception : " + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getDisCodeStatCodeByunitCode Exception : " + e.getMessage());
			}
			return adminModelList2;
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new SQLException ("Internal Server Error");
		} finally {
			if(!connection1.isClosed()) {
				connection1.close();
			}
		}
	}
	
	
	@Override
	 public StateCodeModel getStatCodeByunitCode(String unitCode) throws SQLException {
		// TODO Auto-generated method stub
		
		logger.info("Start Service  unitcode Search");
		StateCodeModel adminModelList2 = null;
		StateCodeModel unitCodeModel = new StateCodeModel();
		String sqlzo = null;
		Connection connection1 = jdbcConnection.getConnection();

		try {
			
//			sqlzo = "SELECT distinct MU.UNIT_CODE,MS.STATE_CODE,MD.DISTRICT_CODE FROM MASTER_UNIT MU,master_city mc, MASTER_STATE MS, MASTER_DISTRICT MD  where\r\n" + 
//					"lower(MU.DISTRICT_NAME)=lower(MD.DESCRIPTION) and lower(MU.STATE_NAME)=lower(MS.DESCRIPTION)\r\n" + 
//					"and mc.DISTRICT_ID=MD.DISTRICT_ID and MD.STATE_ID=MS.STATE_ID and MU.UNIT_CODE= ?";
			
			sqlzo = "select ms.code \"STATE CODE\", ms.type \"STATE TYPE\", 'ZO' \"OPERATING UNIT TYPE\" from liccommon.master_zonal mz left join liccommon.master_state ms on mz.state_name = ms.description where mz.is_Active='Y' and mz.zonal_code = ?"
					+ " UNION  select ms.code \"STATE CODE\", ms.type \"STATE TYPE\", 'UO' \"OPERATING UNIT TYPE\" from liccommon.master_unit mu left join liccommon.master_state ms on mu.state_name = ms.description where mu.is_Active='Y' and mu.unit_code = ? "
					+ "UNION select ms.code \"STATE CODE\", ms.type \"STATE TYPE\", 'SO' \"OPERATING UNIT TYPE\"  from LICCOMMON.master_satellite msat left join liccommon.master_state ms on msat.state_name = ms.description where msat.is_Active='Y' and msat.satellite_code = ?";

			try(PreparedStatement preparestatements2 = connection1.prepareStatement(sqlzo);)
			{
				preparestatements2.setString(1, unitCode);
				preparestatements2.setString(2, unitCode);
				preparestatements2.setString(3, unitCode);
	
				try(ResultSet rs2 = preparestatements2.executeQuery();)
				{
	
				//	adminModelList2 = new ArrayList<>();
					while (rs2.next()) {
						//StateCodeModel unitCodeModel = new StateCodeModel();
						unitCodeModel.setStateCode(rs2.getString(1));
						unitCodeModel.setStateType(rs2.getString(2));
						unitCodeModel.setOperatingUnitType(rs2.getString(3));
						//adminModelList2.add(unitCodeModel);
					}
		
					logger.info("zoSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getDisCodeStatCodeByunitCode Exception : " + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getDisCodeStatCodeByunitCode Exception : " + e.getMessage());
			}
			return unitCodeModel;
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new SQLException ("Internal Server Error");
		} finally {
			if(!connection1.isClosed()) {
				connection1.close();
			}
		}
	}


	@Override
	public List<UnitTypeModel> getOperatingUnitTypeByunitCode(String unitCode) throws Exception {
		logger.info("Start Service  unitcode Search");
		List<UnitTypeModel> adminModelList2 = null;
		//StateCodeModel unitCodeModel = new StateCodeModel();
		String sqlzo = null;
		Connection connection1 = jdbcConnection.getConnection();

		try {
			
//			sqlzo = "SELECT distinct MU.UNIT_CODE,MS.STATE_CODE,MD.DISTRICT_CODE FROM MASTER_UNIT MU,master_city mc, MASTER_STATE MS, MASTER_DISTRICT MD  where\r\n" + 
//					"lower(MU.DISTRICT_NAME)=lower(MD.DESCRIPTION) and lower(MU.STATE_NAME)=lower(MS.DESCRIPTION)\r\n" + 
//					"and mc.DISTRICT_ID=MD.DISTRICT_ID and MD.STATE_ID=MS.STATE_ID and MU.UNIT_CODE= ?";
			
			sqlzo = " select  'CO' \"OPERATING UNIT TYPE\" from liccommon.master_co_office mz left join liccommon.master_state ms on mz.state_name = ms.description where mz.co_code = ?\r\n" + 
					"UNION select  'ZO' \"OPERATING UNIT TYPE\" from liccommon.master_zonal mz left join liccommon.master_state ms on mz.state_name = ms.description where mz.zonal_code = ?\r\n" + 
					"UNION select  'UO' \"OPERATING UNIT TYPE\" from liccommon.master_unit mu left join liccommon.master_state ms on mu.state_name = ms.description where mu.unit_code = ?\r\n" + 
					"UNION select  'SO' \"OPERATING UNIT TYPE\" from LICCOMMON.master_satellite msat left join liccommon.master_state ms on msat.state_name = ms.description where msat.satellite_code = ?";

			try(PreparedStatement preparestatements2 = connection1.prepareStatement(sqlzo);)
			{
				preparestatements2.setString(1, unitCode);
				preparestatements2.setString(2, unitCode);
				preparestatements2.setString(3, unitCode);
				preparestatements2.setString(4,unitCode);
	
				try(ResultSet rs2 = preparestatements2.executeQuery();)
				{
	
					adminModelList2 = new ArrayList<>();
					while (rs2.next()) {
						UnitTypeModel unitCodeModel = new UnitTypeModel();
					//	unitCodeModel.setStateCode(rs2.getString(1));
					//	unitCodeModel.setStateType(rs2.getString(2));
						unitCodeModel.setOperatingUnitType(rs2.getString(1));
						adminModelList2.add(unitCodeModel);
					}
		
					logger.info("zoSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getDisCodeStatCodeByunitCode Exception : " + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getDisCodeStatCodeByunitCode Exception : " + e.getMessage());
			}
			return adminModelList2;
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new SQLException ("Internal Server Error");
		} finally {
			if(!connection1.isClosed()) {
				connection1.close();
			}
		}
	}
	
	
	@Override
	public UnitTypeModelDescription getOperatingUnitTypeAndDescriptionByunitCode(String unitCode) throws Exception {
		logger.info("Start Service  unitcode Search");
		UnitTypeModelDescription adminModelList2 = null;
		UnitTypeModelDescription unitCodeModel = new UnitTypeModelDescription();
		//StateCodeModel unitCodeModel = new StateCodeModel();
		String sqlzo = null;
		Connection connection1 = jdbcConnection.getConnection();

		try {
			
//			sqlzo = "SELECT distinct MU.UNIT_CODE,MS.STATE_CODE,MD.DISTRICT_CODE FROM MASTER_UNIT MU,master_city mc, MASTER_STATE MS, MASTER_DISTRICT MD  where\r\n" + 
//					"lower(MU.DISTRICT_NAME)=lower(MD.DESCRIPTION) and lower(MU.STATE_NAME)=lower(MS.DESCRIPTION)\r\n" + 
//					"and mc.DISTRICT_ID=MD.DISTRICT_ID and MD.STATE_ID=MS.STATE_ID and MU.UNIT_CODE= ?";
			
		sqlzo = "select  'CO' \"OPERATING UNIT TYPE\", mc.DESCRIPTION from liccommon.master_co_office mc left join liccommon.master_state ms on mc.state_name = ms.description where mc.co_code = :unitCode\r\n" + 
				"										UNION select  'ZO' \"OPERATING UNIT TYPE\",mz.DESCRIPTION from liccommon.master_zonal mz left join liccommon.master_state ms on mz.state_name = ms.description where mz.zonal_code = :unitCode\r\n" + 
				"										UNION select  'UO' \"OPERATING UNIT TYPE\",mu.DESCRIPTION from liccommon.master_unit mu left join liccommon.master_state ms on mu.state_name = ms.description where mu.unit_code = :unitCode\r\n" + 
				"									UNION select  'SO' \"OPERATING UNIT TYPE\",msat.DESCRIPTION from LICCOMMON.master_satellite msat left join liccommon.master_state ms on msat.state_name = ms.description where msat.satellite_code =:unitCode";
			
			try(PreparedStatement preparestatements2 = connection1.prepareStatement(sqlzo);)
			{
				preparestatements2.setString(1, unitCode);
				preparestatements2.setString(2, unitCode);
				preparestatements2.setString(3, unitCode);
				preparestatements2.setString(4,unitCode);
	
				try(ResultSet rs2 = preparestatements2.executeQuery();)
				{
	
					//adminModelList2 = new ArrayList<>();
					while (rs2.next()) {
					//	unitCodeModel.setStateCode(rs2.getString(1));
					//	unitCodeModel.setStateType(rs2.getString(2));
						unitCodeModel.setOperatingUnitType(rs2.getString(1));
						unitCodeModel.setUnitDescription(rs2.getNString(2));
						//adminModelList2.add(unitCodeModel);
					}
					
		
					logger.info("zoSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getDisCodeStatCodeByunitCode Exception : " + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getDisCodeStatCodeByunitCode Exception : " + e.getMessage());
			}
			return unitCodeModel;
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new SQLException ("Internal Server Error");
		} finally {
			if(!connection1.isClosed()) {
				connection1.close();
			}
		}
	}
	
	//APi to get master unit stream mapping
	@Override
	 public ResponseEntity<Object> getMasterUnitStreamMapping(String unitCode) throws SQLException {
		logger.info("Start Service  getMasterUnitStreamMapping");
		String sqlStatement = null;
		Connection connection1 = jdbcConnection.getConnection();
		Map<String, Object> map = null;
		try {			
			sqlStatement = "select OPERATING_UNIT_CODE,CONVENTIONAL,LINKED,SWAVALAMBAN from MASTER_UNIT_STREAM_MAPPING where OPERATING_UNIT_CODE =" +"'"+unitCode+"'";

			try(PreparedStatement preparedstatement = connection1.prepareStatement(sqlStatement);)
			{
				try(ResultSet resultSet = preparedstatement.executeQuery();)
				{	
					while (resultSet.next()) {
						map = new HashMap<>();
						map.put("UnitCode", resultSet.getString(1));
						map.put("Conventional", resultSet.getString(2));
						map.put("Linked", resultSet.getString(3));
						map.put("Swavalamban", resultSet.getString(4));
						map.put("Message", "Record retrieved successfully");
					}
					if(map == null) {
						map = new HashMap<>();
						map.put("Message", "No record found");
					}
		
					logger.info("Master unit stream mapping retrieved successfully.");
				}
				catch(Exception e)
				{
					logger.error("getMasterUnitStreamMapping Exception : " + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getMasterUnitStreamMapping Exception : " + e.getMessage());
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new SQLException ("Internal Server Error");
		} finally {
			if(!connection1.isClosed()) {
				connection1.close();
			}
		}
	}
}

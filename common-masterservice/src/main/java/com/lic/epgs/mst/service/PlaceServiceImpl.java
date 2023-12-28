package com.lic.epgs.mst.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.exceptionhandling.PlaceServiceException;
import com.lic.epgs.mst.modal.PlaceModel;
import com.lic.epgs.mst.repository.JDBCConnection;

@Service
public class PlaceServiceImpl implements PlaceService {
	
    @Autowired
    private JDBCConnection jdbcConnection;
	
	String transaction_success = "Success";

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(PlaceServiceImpl.class);

	
	 public List<PlaceModel> getAllPlacesById(Long districtId, Long stateId, Long countryId) throws PlaceServiceException, SQLException {
		Connection con = null;

		con = jdbcConnection.getConnection();
		List<PlaceModel> searchResult = new ArrayList<PlaceModel>();

		try {

			String Sql = "SELECT md.DESCRIPTION DISTRICT,ms.DESCRIPTION STATE,mc.DESCRIPTION COUNTRY FROM master_district md, master_state ms,\r\n" + 
					"master_country mc WHERE md.state_id = ms.state_id AND ms.country_id = mc.country_id\r\n" + 
					"AND (( md.district_id = ? ) OR ( ? = 0 ))\r\n" + 
					"AND (( ms.state_id = ? ) OR ( ? = 0 ))\r\n" + 
					"AND ( ( ms.country_id = ? ) OR ( ? = 0 ) )";

			try(PreparedStatement ps = con.prepareStatement(Sql);)
			{

				ps.setLong(1, districtId);
				ps.setLong(2, districtId);
				ps.setLong(3, stateId);
				ps.setLong(4, stateId);
				ps.setLong(5, countryId);
				ps.setLong(6, countryId);
				try(ResultSet rs = ps.executeQuery();)
				{
	
					while (rs.next()) {
						PlaceModel Model = new PlaceModel();
						Model.setDistrictName(rs.getString("DISTRICT"));
						Model.setStateName(rs.getString("STATE"));
						Model.setCountryName(rs.getString("COUNTRY"));
						searchResult.add(Model);
					}
				}
				catch(Exception e)
				{
					logger.error("getAllPlacesById exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getAllPlacesById exception occured." + e.getMessage());
			}

		} catch (Exception ex) {
			logger.error("exception occured." + ex.getMessage());
			throw new SQLException("internal server error");
		} finally {
			if(!con.isClosed()) {
				con.close();
			}
		}

		return searchResult;

	}

}

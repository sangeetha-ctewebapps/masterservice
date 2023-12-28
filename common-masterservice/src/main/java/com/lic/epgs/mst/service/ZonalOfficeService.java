package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ZonalEntity;
import com.lic.epgs.mst.entity.ZonalOffice;

public interface ZonalOfficeService {
	
	List<ZonalEntity> getAllZonalOffice();
	
	List<ZonalOffice> getAllZonalOfficeByCondition();

	public ZonalOffice getZonalOfficeById(long zonalId);

	public ZonalOffice findByZonalOfficeCode(String zonalCode);
	
	ZonalOffice addZonalOffice(ZonalOffice zonal);

	ZonalOffice updateZonalOffice(ZonalOffice zonal);
	
	public ZonalEntity getAllZoneDetailsByUnitCode(String unitCode);

	void deleteZonalOffice(long id);

}
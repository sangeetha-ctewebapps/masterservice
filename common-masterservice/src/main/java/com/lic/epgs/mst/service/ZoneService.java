package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ZoneMaster;

public interface ZoneService {

	List<ZoneMaster> getAllZone();

	public ZoneMaster getZoneById(long zoneId);

	public ZoneMaster getZoneByCode(String code);

}

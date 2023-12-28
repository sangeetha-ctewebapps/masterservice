package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CauseOfEventEntity;
import com.lic.epgs.mst.entity.ReasonForClaim;

public interface CauseOfEventService {
	
	List<CauseOfEventEntity> getAllCauseOfEvent();

	public CauseOfEventEntity getById(Long causeId);
	
	//public CauseOfEventEntity getCauseOfEventByCode(String CauseCode);


}

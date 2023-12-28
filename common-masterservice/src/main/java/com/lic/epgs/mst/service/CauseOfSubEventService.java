package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CauseOfSubEventEntity;
import com.lic.epgs.mst.repository.CauseOfSubEventRepository;

public interface CauseOfSubEventService {
	
	 public List<CauseOfSubEventEntity> getAllCauseOfSubEvent(String subCauseIndicator);

}

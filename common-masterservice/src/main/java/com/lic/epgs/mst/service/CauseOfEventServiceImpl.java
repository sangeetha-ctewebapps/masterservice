package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.CauseOfEventEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CauseOfEventRepository;

@Service
public class CauseOfEventServiceImpl implements CauseOfEventService{
	

@Autowired
CauseOfEventRepository causeOfEventRepository;

String className=this.getClass().getSimpleName();
private static final Logger logger = LoggerFactory.getLogger(CauseOfEventServiceImpl.class);

	@Override
	public List<CauseOfEventEntity> getAllCauseOfEvent() {
			//String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LoggingUtil.logInfo(className, className, "Started");
			
			return causeOfEventRepository.findAllCuaseOfEvent();
		
		}
	

	@Override
	public CauseOfEventEntity getById(Long causeId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CauseOfEventEntity> reasonforclaimDb = this.causeOfEventRepository.findById(causeId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for causeOfEvent By Id" + causeId);
		if (reasonforclaimDb.isPresent()) {
			logger.info("causeOfEvent is not found with id"+causeId);
			return reasonforclaimDb.get();
		} else {
			throw new ResourceNotFoundException("causeOfEvent not found with id:" + causeId);
		}

	}

	
}

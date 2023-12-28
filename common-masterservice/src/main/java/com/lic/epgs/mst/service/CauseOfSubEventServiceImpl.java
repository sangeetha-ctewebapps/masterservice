package com.lic.epgs.mst.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.CauseOfSubEventEntity;
import com.lic.epgs.mst.repository.CauseOfSubEventRepository;

@Service
@Transactional
public class CauseOfSubEventServiceImpl implements CauseOfSubEventService {

@Autowired
private CauseOfSubEventRepository causeOfSubEventRepository;




@Override
public List<CauseOfSubEventEntity> getAllCauseOfSubEvent(String subCauseIndicator) {
	// TODO Auto-generated method stub
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
    String actualSubCauseIndicator =  subCauseIndicator.equals("6") ? "2" : "1";
	return causeOfSubEventRepository.getAllCauseOfSubEvent(actualSubCauseIndicator);
}


}

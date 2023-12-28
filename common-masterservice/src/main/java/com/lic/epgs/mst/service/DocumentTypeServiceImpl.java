package com.lic.epgs.mst.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.DocumentTypeEntity;
import com.lic.epgs.mst.entity.DocumentTypeForCommonModuleEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.repository.DocumentTypeForCommonModuleRepository;
import com.lic.epgs.mst.repository.DocumentTypeRepository;

@Service
public class DocumentTypeServiceImpl  implements DocumentTypeService{
	
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	
	@Autowired
	DocumentTypeForCommonModuleRepository documentTypeForCommonModuleRepository;
	
	String className=this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(DocumentTypeServiceImpl.class);

	@Override
	public List<DocumentTypeEntity> getAllDocumentType() {
		
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		return documentTypeRepository.findAll();
	
	}
	
	
	@Override
	public List<DocumentTypeForCommonModuleEntity> getAllDocumentsForCommonModule() { 
		
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		return documentTypeForCommonModuleRepository.findAll();
	
	}

		
	

}

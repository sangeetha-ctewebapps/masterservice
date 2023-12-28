package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.CauseOfEventEntity;
import com.lic.epgs.mst.entity.DocumentTypeEntity;
import com.lic.epgs.mst.entity.DocumentTypeForCommonModuleEntity;
import com.lic.epgs.mst.exceptionhandling.DocumentTypeException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ReasonForClaimException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.DocumentTypeService;

@RestController
@CrossOrigin("*")
public class DocumentTypeController {
	
	@Autowired
	DocumentTypeService documentTypeService;
	
private static final Logger logger = LoggerFactory.getLogger(DocumentTypeController.class);
	
	 String className = this.getClass().getSimpleName();

	@GetMapping("/getAllDocumentType")
	public List<DocumentTypeEntity> getAllDocumentType() throws ResourceNotFoundException, DocumentTypeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<DocumentTypeEntity> documentType = documentTypeService.getAllDocumentType();

			if (documentType.isEmpty()) {
				logger.debug("inside DocumentType controller getAllDocumentTypem() method");
				logger.info("DocumentType list is empty ");
				throw new ResourceNotFoundException("DocumentType not found");
			}
			return documentType;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new DocumentTypeException("internal server error");
		}
	}
	
	
	@GetMapping("/getAllDocumentsForCommonModule")
	public List<DocumentTypeForCommonModuleEntity> getAllDocumentsForCommonModule() throws ResourceNotFoundException, DocumentTypeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<DocumentTypeForCommonModuleEntity> documentType = documentTypeService.getAllDocumentsForCommonModule();

			if (documentType.isEmpty()) {
				logger.debug("inside DocumentType controller getAllDocumentsForCommonModule() method");
				logger.info("DocumentType list is empty ");
				throw new ResourceNotFoundException("DocumentType not found");
			}
			return documentType;
		} catch (Exception ex) { 
			logger.error("Internal Server Error");
			throw new DocumentTypeException("internal server error");
		}
	}

}

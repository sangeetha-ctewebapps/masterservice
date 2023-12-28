package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CauseOfEventEntity;
import com.lic.epgs.mst.entity.DocumentTypeEntity;
import com.lic.epgs.mst.entity.DocumentTypeForCommonModuleEntity;

public interface DocumentTypeService {
	
	List<DocumentTypeEntity> getAllDocumentType();
	
	List<DocumentTypeForCommonModuleEntity> getAllDocumentsForCommonModule(); 

}

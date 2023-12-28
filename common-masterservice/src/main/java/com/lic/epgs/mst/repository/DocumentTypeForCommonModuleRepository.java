package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lic.epgs.mst.entity.DocumentTypeForCommonModuleEntity;

@Repository
public interface DocumentTypeForCommonModuleRepository extends JpaRepository<DocumentTypeForCommonModuleEntity, Long> {

	
	

}

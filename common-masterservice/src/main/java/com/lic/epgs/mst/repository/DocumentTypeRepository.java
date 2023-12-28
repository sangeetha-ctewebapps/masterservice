package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.DocumentTypeEntity;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentTypeEntity, Long> {

	
	
	

}

package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterCadre;

@Repository
public interface MasterCadreRepo  extends JpaRepository<MasterCadre, Long> { 	
	Optional<MasterCadre> findByCadre(String cadre);	

	@Query(value = "SELECT * FROM MASTER_CADRE WHERE IS_ACTIVE='Y' and IS_DELETED='N' order by CADREID DESC", nativeQuery = true)
	List<MasterCadre> findAll();
}




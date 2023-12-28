package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterCategoryDesignationsEntity;

@Repository
public interface MasterCategoryDesignationsRepository extends JpaRepository<MasterCategoryDesignationsEntity, Long> {

	@Query(value = "select * from MASTER_CATEGORY_DESIGNATION WHERE IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public List<MasterCategoryDesignationsEntity> getALLCategoryDesignations();

}

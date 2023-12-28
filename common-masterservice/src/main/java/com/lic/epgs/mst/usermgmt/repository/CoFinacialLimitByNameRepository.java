package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitNameEntity;

@Repository
public interface CoFinacialLimitByNameRepository extends JpaRepository<CoFinancialLimitNameEntity, Long> {

	@Query(value = "select CFL.*,MC.DESIGNATION as DESIGNATION1,FA.function_name from CO_FINANCIAL_LIMIT CFL left join MASTER_CADRE MC ON CFL.CADRE_ID = MC.CADREID left join functional_area fa on cfl.FUNCTIONAREA_ID=fa.FUNCTIONAREA_ID order by cfl.CREATED_ON DESC", nativeQuery = true)
	public List<CoFinancialLimitNameEntity> getCoFinancialLimitByName();
}

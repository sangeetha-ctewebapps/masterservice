package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.BankDetails;
import com.lic.epgs.mst.entity.SpecailClaimReasonEntity;
@Repository
public interface SpecailClaimRepository extends JpaRepository<SpecailClaimReasonEntity, Long> {


	}





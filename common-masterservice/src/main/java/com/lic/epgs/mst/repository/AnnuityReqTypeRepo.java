package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.AnnuityReqType;

@Repository
public interface AnnuityReqTypeRepo extends JpaRepository<AnnuityReqType, Long> {

	Optional<AnnuityReqType> findByAnnuityCode(String Code);

}

package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ClaimRequirementType;

@Repository
public interface ClaimRequirementTypeRepo extends JpaRepository<ClaimRequirementType, Long> {

	Optional<ClaimRequirementType> findByClaimCode(String claimCode);

}

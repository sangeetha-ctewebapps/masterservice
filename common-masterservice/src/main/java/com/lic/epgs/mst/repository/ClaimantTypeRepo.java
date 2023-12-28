package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ClaimantType;

@Repository
public interface ClaimantTypeRepo extends JpaRepository<ClaimantType, Long> {

	Optional<ClaimantType> findByClaimantCode(String code);

}
package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TypeOfClaim;

@Repository
public interface TypeOfClaimRepository extends JpaRepository<TypeOfClaim, Long> {

	@Query("SELECT DISTINCT r FROM TypeOfClaim r WHERE r.typeoc_code = :claimcode")
	public Object findByTypeOfClaimCode(String claimcode);

}

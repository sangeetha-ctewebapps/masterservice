package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.RelationshipMst;

@Repository
public interface RelationshipRepository extends JpaRepository<RelationshipMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM RELATIONSHIP_MST WHERE RELATIONSHIP_MST_CODE = :reasonForClaimCode"
	 * , nativeQuery = true) Optional<RelationshipMst> findByCode(String
	 * reasonForClaimCode);
	 */
	@Query("FROM RelationshipMst WHERE realtionshipMstCode = :relationshipcode")
	public Optional<RelationshipMst> findByRelationshipCode(String relationshipcode);
	
}

package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.EndorsementSubType;

@Repository
public interface EndorsementSubTypeRepository extends JpaRepository<EndorsementSubType, Long> {

	@Query("SELECT DISTINCT r FROM EndorsementSubType r WHERE r.endorsementst_code = :endorsementstcode")
	public Object findByEndorsementSTCode(String endorsementstcode);

}

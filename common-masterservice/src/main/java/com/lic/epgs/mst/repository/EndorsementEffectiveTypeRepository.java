package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.EndorsementEffectiveType;

@Repository
public interface EndorsementEffectiveTypeRepository extends JpaRepository<EndorsementEffectiveType, Long> {

	@Query("SELECT DISTINCT r FROM EndorsementEffectiveType r WHERE r.endorsementet_code = :endorsementetcode")
	public Object findByEndorsementETCode(String endorsementetcode);

}

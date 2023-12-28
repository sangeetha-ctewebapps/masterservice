package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TypeOfEndorsement;

@Repository
public interface TypeOfEndorsementRepository extends JpaRepository<TypeOfEndorsement, Long> {

	@Query("SELECT DISTINCT r FROM TypeOfEndorsement r WHERE r.typeoe_code = :Typeoecode")
	public Object findByTypeOfEndorsementCode(String Typeoecode);

}

package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TypeOfService;

@Repository
public interface TypeOfServiceRepository extends JpaRepository<TypeOfService, Long> {

	@Query("SELECT DISTINCT r FROM TypeOfService r WHERE r.typeos_code = :typeoscode")
	public Object findByTypeOfServiceCode(String typeoscode);

}

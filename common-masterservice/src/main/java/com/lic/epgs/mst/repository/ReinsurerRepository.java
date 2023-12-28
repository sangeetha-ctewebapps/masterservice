package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ReinsurerType;

@Repository
public interface ReinsurerRepository extends JpaRepository<ReinsurerType, Long> {

	@Async
	@Query("SELECT r FROM ReinsurerType r WHERE r.reinsurerTypeCode = :code")
	public ReinsurerType getReinsurerByCode(String code);

}

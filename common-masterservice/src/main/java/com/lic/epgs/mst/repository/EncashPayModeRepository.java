package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.EncashPayModeMst;

@Repository
public interface EncashPayModeRepository extends JpaRepository<EncashPayModeMst, Long> {

	@Query("FROM EncashPayModeMst WHERE encashPayModeCode = :code")
	public Optional<EncashPayModeMst> findByEncashPayCode(String code);
	
}

package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.RetentionLevel;

@Repository
public interface RetentionLevelRepo extends JpaRepository<RetentionLevel, Long> {
	
	public Optional<RetentionLevel> findByRetentionCode(String code);
}


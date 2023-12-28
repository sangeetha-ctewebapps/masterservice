package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TermType;

@Repository
public interface TermTypeRepo extends JpaRepository<TermType, Long> {
	Optional<TermType> findByTermCode(String termCode);
}

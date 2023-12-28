package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.IndustryType;

@Repository
public interface IndustryTypeRepo extends JpaRepository<IndustryType, Long> {

	 Optional<IndustryType> findByIndustryCode(String industryCode);
}

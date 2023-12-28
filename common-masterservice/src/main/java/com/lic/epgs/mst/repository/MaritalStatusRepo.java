package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.MaritalStatus;

@Repository
public interface MaritalStatusRepo extends JpaRepository<MaritalStatus, Long> {

	Optional<MaritalStatus> findByMaritalCode(String maritalCode);

}

package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CessionType;

@Repository
public interface CessionTypeRepo extends JpaRepository<CessionType, Long> {

	Optional<CessionType> findByCessionCode(String code);

}

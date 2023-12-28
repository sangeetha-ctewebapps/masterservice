package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.DynamicUrlConfig;

@Repository
public interface DynamicUrlConfigRepository extends JpaRepository<DynamicUrlConfig, Long> {

	List<DynamicUrlConfig> findByName(String name);
}

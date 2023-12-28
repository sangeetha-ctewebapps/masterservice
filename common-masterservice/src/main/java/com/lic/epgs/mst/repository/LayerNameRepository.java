package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.LayerNameMaster;

@Repository
public interface LayerNameRepository extends JpaRepository<LayerNameMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_LAYER_NAME WHERE LAYER_NAME_CODE = :layerNameCode", nativeQuery = true)
	Optional<LayerNameMaster> findByCode(String layerNameCode);

}

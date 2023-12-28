package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lic.epgs.mst.entity.LayerTypeMaster;

@Repository
public interface LayerTypeRepository extends JpaRepository<LayerTypeMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_LAYER_TYPE WHERE LAYER_TYPE_CODE = :layerTypeCode", nativeQuery = true)
	Optional<LayerTypeMaster> findByCode(String layerTypeCode);

}

package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ProductNameMaster;

@Repository
public interface ProductNameRepository extends JpaRepository<ProductNameMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_PRODUCT_NAME WHERE PRODUCT_NAME_CODE = :productNameCode", nativeQuery = true)
	Optional<ProductNameMaster> findByCode(String productNameCode);
	
	@Modifying
	@Query(value = "UPDATE MASTER_PRODUCT_NAME SET IS_DELETED ='Y' WHERE PRODUCT_NAME_ID =:productNameId", nativeQuery = true)
	Integer findDeletedById(long productNameId);
	
	@Query(value = "select * from MASTER_PRODUCT_NAME where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	  public List<ProductNameMaster> findAllByCondition();

}

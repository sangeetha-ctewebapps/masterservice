package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.GroupType;

@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {

	@Query("SELECT DISTINCT r FROM GroupType r WHERE r.group_code = :groupcode")
	public Object findByGroupCode(String groupcode);

}

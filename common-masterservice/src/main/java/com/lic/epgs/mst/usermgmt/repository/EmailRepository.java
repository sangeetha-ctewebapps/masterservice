package com.lic.epgs.mst.usermgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterEmailEntity;

@Repository
public interface EmailRepository extends JpaRepository<MasterEmailEntity,Long> {

}

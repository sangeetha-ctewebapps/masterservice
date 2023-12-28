package com.lic.epgs.mst.usermgmt.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterChangePassSmsEntity;

@Repository
public interface SmsChangePassRepository extends JpaRepository<MasterChangePassSmsEntity,Long> {

}

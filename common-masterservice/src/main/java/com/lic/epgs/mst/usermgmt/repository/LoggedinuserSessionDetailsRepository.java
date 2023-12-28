package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.usermgmt.entity.LoggedInUserSessionDetailsEntity;

public interface LoggedinuserSessionDetailsRepository extends JpaRepository<LoggedInUserSessionDetailsEntity, Long>{
	
	
	@Query(value = "select * from loggedinuser_session_details where lower(username) = lower(:userName) and lower(browser)= lower(:browser)", nativeQuery = true)
	public List<LoggedInUserSessionDetailsEntity> getAllUserSessionsBasedOnUserNameAndBrowser(@Param("userName") String userName,@Param("browser") String browser);
	
	@Query(value = "select * from loggedinuser_session_details where lower(username) = lower(:userName)", nativeQuery = true)
	public List<LoggedInUserSessionDetailsEntity> getAllUserSessionsBasedOnUserName(@Param("userName") String userName);
	
	@Transactional
	@Modifying	
	@Query(value = "DELETE FROM loggedinuser_session_details where username = :userName", nativeQuery = true)
    public void DeleteLoggedInUserByUserName(@Param("userName") String userName);

}

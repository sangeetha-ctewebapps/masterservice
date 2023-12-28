package com.lic.epgs.mst.usermgmt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lic.epgs.mst.usermgmt.entity.EnableDisableUserEntity;

@Repository
public interface EnableDisableUserRepository extends JpaRepository<EnableDisableUserEntity, Long> {

	@Query(value = "select * from enable_disable_user where username = :username", nativeQuery = true)
	public EnableDisableUserEntity getInvalidAttemptCount(@Param("username") String username);
  
  @Query(value = "select INVALID_ATTEMPT_COUNT from enable_disable_user where username = :username", nativeQuery = true)
	public String getInvalidAttemptCountNum(@Param("username") String username);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM ENABLE_DISABLE_USER WHERE username=:username", nativeQuery = true)
	public void deleteUnsuccessfulLoginUser(@Param("username") String username);
}

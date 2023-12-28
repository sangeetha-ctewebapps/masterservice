package com.lic.epgs.mst.usermgmt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterUsersLoginDetailsEntity;

@Repository
public interface MasterUsersLoginDetailsRepository extends JpaRepository<MasterUsersLoginDetailsEntity, Long> {
	
	@Modifying
	 @Query(value = "UPDATE MASTER_USERS_LOGIN_DETAILS SET LOGGEDOUTTIME = :loggedOutTime WHERE USERNAME= :userName and LOGINID =:loginId", nativeQuery = true)
	 public void updateLoggedOutTime(@Param("loginId") Long loginId,@Param("userName")String userName,@Param("loggedOutTime") Date loggedOutTime  );
	
	@Query(value = "SELECT * FROM MASTER_USERS_LOGIN_DETAILS  WHERE  LOGINID =:loginId", nativeQuery = true)
	 public MasterUsersLoginDetailsEntity getLoginDetails(@Param("loginId") Long loginId );
	
	@Query(value = "SELECT * FROM MASTER_USERS_LOGIN_DETAILS  WHERE  USERNAME =:username order by modifiedon desc fetch  first 10 rows only", nativeQuery = true)
	 public List<MasterUsersLoginDetailsEntity> getLoginUserDetails(@Param("username") String username );
	
	@Modifying
	 @Query(value = "UPDATE MASTER_USERS_LOGIN_DETAILS SET LOGGEDOUTTIME = :loggedOutTime WHERE USERNAME= :userName and LOGGEDOUTTIME is null", nativeQuery = true)
	 public void updateLogOutTime(@Param("userName")String userName,@Param("loggedOutTime") Date loggedOutTime  );
}

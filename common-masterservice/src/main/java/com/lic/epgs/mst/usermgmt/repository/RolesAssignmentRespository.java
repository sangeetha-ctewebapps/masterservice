package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.RolesAssignmentHistory;

@Repository
public interface RolesAssignmentRespository extends JpaRepository<RolesAssignmentHistory, Long> {

	@Query(value = "select * from master_portal_roles where created_by= :loggedInusername order by created_on desc", nativeQuery = true)
	public List<RolesAssignmentHistory> getRolesAssignmentByUsername(@Param("loggedInusername") String loggedInusername);
	
	
	@Query(value = "select * from master_portal_roles where created_by= :loggedInusername  and status= :status order by created_on desc", nativeQuery = true)
	public List<RolesAssignmentHistory> getRolesAssignmentByUsernameAndStatus(@Param("loggedInusername") String loggedInusername,@Param("status") String status);

	@Query(value = "select * from master_portal_roles where PORTAL_ROLES_ID = :portalRolesId", nativeQuery = true)
	public RolesAssignmentHistory getAllRoleDetailByPortalrolesid(@Param("portalRolesId") Long portalRolesId);
	
	@Query(value = "select * from master_portal_roles where created_by != :loggedInusername  and status='sentforapproval' and action= :action and trunc(created_on) between TO_DATE(:startDate,'YYYY-MM-DD') and TO_DATE(:endDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true)
	public List<RolesAssignmentHistory> getRolesAssignmentByUsernameAndStatusAndDate(@Param("loggedInusername") String loggedInusername,@Param("action") String action,@Param("startDate") String startDate,@Param("endDate") String endDate);

	@Query(value = "select * from master_portal_roles where created_by != :loggedInusername  and status='sentforapproval'  and trunc(created_on) between TO_DATE(:startDate,'YYYY-MM-DD') and TO_DATE(:endDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true)
	public List<RolesAssignmentHistory> getRolesAssignmentByUsernameAndStartDate(@Param("loggedInusername") String loggedInusername,@Param("startDate") String startDate,@Param("endDate") String endDate);

	@Query(value = "select * from master_portal_roles where created_by != :loggedInusername  and status='sentforapproval' and trunc(created_on) <= TO_DATE(:endDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true)
	public List<RolesAssignmentHistory> getRolesAssignmentByUsernameAndEndDate(@Param("loggedInusername") String loggedInusername,@Param("endDate") String endDate);
	
	@Query(value = "select * from master_portal_roles where created_by != :loggedInusername  and status='sentforapproval' and action= :action and trunc(created_on) between TO_DATE(:startDate,'YYYY-MM-DD') and TO_DATE(:endDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true)
	public List<RolesAssignmentHistory> getRolesAssignmentByUsernameAndStatusAndStartDate(@Param("loggedInusername") String loggedInusername,@Param("action") String action,@Param("startDate") String startDate,@Param("endDate") String endDate);

	@Query(value = "select * from master_portal_roles where created_by != :loggedInusername  and status='sentforapproval' and action= :action and trunc(created_on) <= TO_DATE(:endDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true)
	public List<RolesAssignmentHistory> getRolesAssignmentByUsernameAndStatusAndEndDate(@Param("loggedInusername") String loggedInusername,@Param("action") String action,@Param("endDate") String endDate);

	
}

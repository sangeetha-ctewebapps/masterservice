package com.lic.epgs.mst.entity;

import java.io.Serializable;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*@SqlResultSetMapping(
	    name="districtStateMapping",
////	    classes={
////	        @ConstructorResult(
//	            targetClass=DistrictStateResponseModel.class,
	            columns={
	                @ColumnResult(name="stateId"),
	                @ColumnResult(name="STATE_CODE"),
	                @ColumnResult(name="DISTRICT_ID"),
	                @ColumnResult(name="DISTRICT_CODE"),
	                @ColumnResult(name="DESCRIPTION"),
	                @ColumnResult(name="IS_ACTIVE"),
	                @ColumnResult(name="IS_DELETED"),
	                @ColumnResult(name="CREATED_ON"),
	                @ColumnResult(name="CREATED_BY"),
	                @ColumnResult(name="MODIFIED_ON"),
	                @ColumnResult(name="MODIFIED_BY")
	            }
//	        )
//	    }
	)*/

//@NamedNativeQuery(name="getGroupDetails", query="SELECT g.*, gm.* FROM group g LEFT JOIN group_members gm ON g.group_id = gm.group_id and gm.user_id = :userId WHERE g.group_id = :groupId", resultSetMapping="groupDetailsMapping")
//@NamedNativeQuery(name="findAllWithStateIds", query ="SELECT md.STATE_ID,ms.STATE_CODE,md.DISTRICT_ID,md.DISTRICT_CODE,md.DESCRIPTION,md.IS_ACTIVE,md.IS_DELETED,md.CREATED_ON,md.CREATED_BY,md.MODIFIED_ON,md.MODIFIED_BY FROM master_state ms, master_district md WHERE ms.STATE_ID=md.STATE_ID ", resultSetMapping="districtStateMapping")


public class DistrictStateResponseModel implements Serializable {

	private long stateId;
	private String stateCode;
	private long districtId;
	private String districtCode;
	private String description;
	private char isActive;
	private char isDeleted;
	private Date createdOn;
	private String createdBy;
	private Date modifiedOn;
	private String modifiedBy;
	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public char getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	

	
}

package com.lic.epgs.mst.usermgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ENABLE_DISABLE_USER")
public class EnableDisableUserEntity implements Serializable{

	private static final long serialVersionUID = 2264263195138765828L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "USERNAME")
	private String username;

	@Column(name = "INVALID_ATTEMPT_COUNT")
	private int invalidAttemptCount;
	
	@Column(name = "DISABLED_TIME")
	private Date disabledTime;
	
	@Column(name = "IS_DISABLED")
	private String isDisabled;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	

	public int getInvalidAttemptCount() {
		return invalidAttemptCount;
	}

	public void setInvalidAttemptCount(int invalidAttemptCount) {
		this.invalidAttemptCount = invalidAttemptCount;
	}

	public Date getDisabledTime() {
		return disabledTime;
	}

	public void setDisabledTime(Date disabledTime) {
		this.disabledTime = disabledTime;
	}

	public String getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}


}

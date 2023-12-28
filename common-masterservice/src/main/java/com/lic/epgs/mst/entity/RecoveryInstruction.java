package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "MASTER_RECOVERY_INSTRUCTION")
public class RecoveryInstruction implements Serializable {
	private static final long serialVersionUID = 9132235849284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RECOVERY_ID")
	private long id;

	@Column(name = "RECOVERY_CODE")
	private String recoveryCode;

	@Column(name = "DESCRIPTION")
	private String recoveryDesc;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRecoveryCode() {
		return recoveryCode;
	}

	public void setRecoveryCode(String recoveryCode) {
		this.recoveryCode = recoveryCode;
	}

	public String getRecoveryDesc() {
		return recoveryDesc;
	}

	public void setRecoveryDesc(String recoveryDesc) {
		this.recoveryDesc = recoveryDesc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "RecoveryInstruction [id=" + id + ", recoveryCode=" + recoveryCode + ", recoveryDesc=" + recoveryDesc
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}

	
}

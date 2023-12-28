package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_SMS")
public class MasterChangePassSmsEntity {
	private static final long serialVersionUID = 2064263195138765828L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="master_Sms_Otp_generator")
	 @SequenceGenerator(name="master_Sms_Otp_generator", sequenceName ="MASTER_MSGPASSWORD_SEQ",allocationSize=1)
	 @Column(name = "ID")
	 private int id;
	
	 @Column(name = "SOURCE")
	private String source;
	
	@Column(name = "MOBILE")
	private String mobile;
	
	@Column(name = "USER_NAME")
	private String username;
	
	@Column(name = "RESPONSE_MESSAGE")
	private String responsemsg;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "RETURN_CODE")
	private String returncode;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedby;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResponsemsg() {
		return responsemsg;
	}

	public void setResponsemsg(String responsemsg) {
		this.responsemsg = responsemsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturncode() {
		return returncode;
	}

	public void setReturncode(String returncode) {
		this.returncode = returncode;
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

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifiedon() {
		return modifiedon;
	}

	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}

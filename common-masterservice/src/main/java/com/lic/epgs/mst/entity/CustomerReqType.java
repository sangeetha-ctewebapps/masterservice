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
@Table(name = "MASTER_CUSTOMER_REQUIREMENT")
public class CustomerReqType implements Serializable {

	private static final long serialVersionUID = 9148345849284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CUSTOMER_ID")
	private long id;

	@Column(name = "CUSTOMER_CODE")
	private String customerCode;

	@Column(name = "DESCRIPTION")
	private String customerDesc;

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

	public String getCustomerDesc() {
		return customerDesc;
	}

	public void setCustomerDesc(String customerDesc) {
		this.customerDesc = customerDesc;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	@Override
	public String toString() {
		return "CustomerReqType [id=" + id + ", customerDesc=" + customerDesc + ", customerCode=" + customerCode
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}

}

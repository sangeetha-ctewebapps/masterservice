package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_TYPE_OF_LOAN")
public class TypeOfLoanMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TYPE_OF_LOAN_ID")
	private long typeOfLoanId;

	@Column(name = "TYPE_OF_LOAN_CODE")
	private String typeOfLoanCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getTypeOfLoanId() {
		return typeOfLoanId;
	}

	public void setTypeOfLoanId(long typeOfLoanId) {
		this.typeOfLoanId = typeOfLoanId;
	}

	public String getTypeOfLoanCode() {
		return typeOfLoanCode;
	}

	public void setTypeOfLoanCode(String typeOfLoanCode) {
		this.typeOfLoanCode = typeOfLoanCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

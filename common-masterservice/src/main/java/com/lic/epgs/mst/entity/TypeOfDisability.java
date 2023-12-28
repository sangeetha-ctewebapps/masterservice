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
@Table(name = "MASTER_TYPE_OF_DISABILITY")
public class TypeOfDisability implements Serializable {

	private static final long serialVersionUID = 9111345849284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DISABILITY_ID")
	private long id;

	@Column(name = "DISABILITY_CODE")
	private String disabilityCode;

	@Column(name = "DESCRIPTION")
	private String disabilityDesc;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Override
	public String toString() {
		return "TypeOfDisability [id=" + id + ", disabilityCode=" + disabilityCode + ", disabilityDesc="
				+ disabilityDesc + ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDisabilityCode() {
		return disabilityCode;
	}
    
	public void setDisabilityCode(String disabilityCode) {
		this.disabilityCode = disabilityCode;
	}

	public String getDisabilityDesc() {
		return disabilityDesc;
	}

	public void setDisabilityDesc(String disabilityDesc) {
		this.disabilityDesc = disabilityDesc;
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
}


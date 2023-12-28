package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_LAYER_TYPE")

public class LayerTypeMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LAYER_TYPE_ID")
	private long layerTypeId;

	@Column(name = "LAYER_TYPE_CODE")
	private String layerTypeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getLayerTypeId() {
		return layerTypeId;
	}

	public void setLayerTypeId(long layerTypeId) {
		this.layerTypeId = layerTypeId;
	}

	public String getLayerTypeCode() {
		return layerTypeCode;
	}

	public void setLayerTypeCode(String layerTypeCode) {
		this.layerTypeCode = layerTypeCode;
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

}

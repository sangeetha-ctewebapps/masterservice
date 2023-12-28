package com.lic.epgs.mst.modal;

import java.util.List;

public class UOModel {
	private Long unitId;
	private String unitCode;
	private String unitCity;
	private String unitDescription;
	private List<SOModel> soList;

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitCity() {
		return unitCity;
	}

	public void setUnitCity(String unitCity) {
		this.unitCity = unitCity;
	}

	public String getUnitDescription() {
		return unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public List<SOModel> getSoList() {
		return soList;
	}

	public void setSoList(List<SOModel> soList) {
		this.soList = soList;
	}

}

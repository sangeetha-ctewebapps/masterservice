package com.lic.epgs.mst.modal;

import java.util.List;

public class ZOModel {
	private Long zoId;
	private String zoCode;
	private String zoDescription;
	private List<UOModel> uoList;

	public Long getZoId() {
		return zoId;
	}

	public void setZoId(Long zoId) {
		this.zoId = zoId;
	}

	public String getZoCode() {
		return zoCode;
	}

	public void setZoCode(String zoCode) {
		this.zoCode = zoCode;
	}

	public String getZoDescription() {
		return zoDescription;
	}

	public void setZoDescription(String zoDescription) {
		this.zoDescription = zoDescription;
	}

	public List<UOModel> getUoList() {
		return uoList;
	}

	public void setUoList(List<UOModel> uoList) {
		this.uoList = uoList;
	}

}

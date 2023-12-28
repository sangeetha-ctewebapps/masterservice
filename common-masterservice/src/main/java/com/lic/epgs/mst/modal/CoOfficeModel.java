package com.lic.epgs.mst.modal;

import java.util.List;

public class CoOfficeModel {

	private Long coId;
	private String coCode;
	private String coDescription;
	private List<ZOModel> zoList;

	public Long getCoId() {
		return coId;
	}

	public void setCoId(Long coId) {
		this.coId = coId;
	}

	public String getCoCode() {
		return coCode;
	}

	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}

	public String getCoDescription() {
		return coDescription;
	}

	public void setCoDescription(String coDescription) {
		this.coDescription = coDescription;
	}

	public List<ZOModel> getZoList() {
		return zoList;
	}

	public void setZoList(List<ZOModel> zoList) {
		this.zoList = zoList;
	}

}

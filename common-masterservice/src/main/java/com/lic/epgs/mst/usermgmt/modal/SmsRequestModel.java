package com.lic.epgs.mst.usermgmt.modal;

public class SmsRequestModel {
	
	private String mobileNo;
	private String name;
	private String msgTxt;
	private String policyNumber;
	private String divisionCode;
  
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsgTxt() {
		return msgTxt;
	}
	public void setMsgTxt(String msgTxt) {
		this.msgTxt = msgTxt;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	
	
	
}

package com.lic.epgs.mst.usermgmt.modal;

import java.util.List;

public class EmailRequestModel {

	private String to;
	private String subject;
	private String emailBody;
	private List<String> pdfFile;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public List<String> getPdfFile() {
		return pdfFile;
	}
	public void setPdfFile(List<String> pdfFile) {
		this.pdfFile = pdfFile;
	}
	
	

	
}

package com.lic.epgs.mst.usermgmt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERMGMT_COMMON_CONFIG")
public class CommonConfig {
	
	@Id
    @Column(name = "COMM_CONFIG_ID")
    private long commConfigId;

	@Column(name = "KEY1")
    private String key1;
	
	@Column(name = "KEY2")
    private String key2;
	
	@Column(name = "VALUE")
    private String value;
	
	@Column(name = "MODULE")
    private String module;
	
	@Column(name = "CREATED_BY")
    private String created_by;
	
	@Column(name = "CREATED_ON")
    private String created_on;
	
	@Column(name = "MODIFIED_BY")
    private String modified_by;
	
	@Column(name = "MODIFIED_ON")
    private String modified_on;
	
	@Column(name = "REMARKS")
    private String remarks;

	public long getCommConfigId() {
		return commConfigId;
	}

	public void setCommConfigId(long commConfigId) {
		this.commConfigId = commConfigId;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public String getModified_on() {
		return modified_on;
	}

	public void setModified_on(String modified_on) {
		this.modified_on = modified_on;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}

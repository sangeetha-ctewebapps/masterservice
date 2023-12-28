package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "MASTER_REINSURER")
public class ReinsurerType implements Serializable {
	
	private static final long serialVersionUID = 101L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REINSURER_ID")
	private long id;
	
	@Column(name = "REINSURER_CODE")
	private String reinsurerTypeCode;
	
	@Column(name = "DESCRIPTION")
	private String desc;	
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	//@UpdateTimestamp
	@Column(name = "CREATED_BY")
	private String createdBy;
		
	public ReinsurerType() 
	{		
	}

	public ReinsurerType(long id, String reinsurerTypeCode, String desc, Date createdOn, String createdBy) 
	{
		super();
		this.id = id;
		this.reinsurerTypeCode = reinsurerTypeCode;
		this.desc = desc;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}



	public long getId() 
	{
		return id;
	}
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public String getReinsurerTypeCode() 
	{
		return reinsurerTypeCode;
	}
	
	public void setReinsurerTypeCode(String reinsurerTypeCode) 
	{
		this.reinsurerTypeCode = reinsurerTypeCode;
	}
	
	public String getDesc() 
	{
		return desc;
	}
	
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
	
	public Date getCreatedOn() 
	{
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) 
	{
		this.createdOn = createdOn;
	}
	
	public String getCreatedBy() 
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}
	
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}
	
	
	 @Override
	    public String toString() 
	 {
	        return String
	        		.format("{id:%s,reinsurerTypeCode:%s,desc:%s,createdOn:%s,createdBy:%s}"
	        				, id, reinsurerTypeCode, desc, createdOn,createdBy);
	    }	

		
}

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
@Table(name = "MASTER_LENDER_BORROWER_GRP_CATE")
public class LenderBorGrpCate implements Serializable {

	private static final long serialVersionUID = 101L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LENDER_BOR_GRP_CATE_ID")
	private long id;
	
	@Column(name = "LENDER_BOR_GRP_CATE_CODE")
	private String lenderBorGrpCateeCode;
	
	@Column(name = "DESCRIPTION")
	private String desc;	
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	//@UpdateTimestamp
	@Column(name = "CREATED_BY")
	private String createdBy;

	public LenderBorGrpCate() 
	{		
	}

	public LenderBorGrpCate(long id, String lenderBorGrpCateeCode, String desc, Date createdOn, String createdBy) {
		super();
		this.id = id;
		this.lenderBorGrpCateeCode = lenderBorGrpCateeCode;
		this.desc = desc;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	
	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	
	public String getLenderBorGrpCateeCode() {
		return lenderBorGrpCateeCode;
	}

	
	public void setLenderBorGrpCateeCode(String lenderBorGrpCateeCode) {
		this.lenderBorGrpCateeCode = lenderBorGrpCateeCode;
	}

	
	public String getDesc() {
		return desc;
	}

	
	public void setDesc(String desc) {
		this.desc = desc;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (id ^ (id >>> 32));
			result = prime * result + ((lenderBorGrpCateeCode == null) ? 0 : lenderBorGrpCateeCode.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LenderBorGrpCate other = (LenderBorGrpCate) obj;
			if (id != other.id)
				return false;
			if (lenderBorGrpCateeCode == null) {
				if (other.lenderBorGrpCateeCode != null)
					return false;
			} else if (!lenderBorGrpCateeCode.equals(other.lenderBorGrpCateeCode))
				return false;
			return true;
		}
	
	
	
	
}

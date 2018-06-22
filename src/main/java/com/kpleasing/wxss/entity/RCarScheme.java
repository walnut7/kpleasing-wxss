package com.kpleasing.wxss.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RCarScheme generated by hbm2java
 */
@Entity
@Table(name = "r_car_scheme", catalog = "wxss")
public class RCarScheme implements java.io.Serializable {

	/** *  */
	private static final long serialVersionUID = -4616251845098598748L;
	
	private Integer id;
	
	private int bpId;
	
	private int planId;
	
	private byte enabledFlag;
	
	public RCarScheme(){
	}
	
	public RCarScheme(Integer id, int bpId, int planId, byte enabledFlag) {
		super();
		this.id = id;
		this.bpId = bpId;
		this.planId = planId;
		this.enabledFlag = enabledFlag;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "bp_id", nullable = false)
	public int getBpId() {
		return bpId;
	}

	public void setBpId(int bpId) {
		this.bpId = bpId;
	}

	@Column(name = "plan_id", nullable = false)
	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	@Column(name = "enabled_flag", nullable = false)
	public byte getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(byte enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

}
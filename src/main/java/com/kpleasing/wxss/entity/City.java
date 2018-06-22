package com.kpleasing.wxss.entity;
// Generated 2018-3-27 9:41:18 by Hibernate Tools 5.0.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbCity generated by hbm2java
 */
@Entity
@Table(name = "tb_city", catalog = "wxss")
public class City implements java.io.Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 1018528248621998748L;
	private Integer id;
	private String provinceCode;
	private String cityCode;
	private String cityName;
	private String zipCode;
	private byte isMain;

	public City() {
	}

	public City(String provinceCode, String cityCode, String cityName, byte isMain) {
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.isMain = isMain;
	}

	public City(String provinceCode, String cityCode, String cityName, String zipCode, byte isMain) {
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.zipCode = zipCode;
		this.isMain = isMain;
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

	@Column(name = "province_code", nullable = false, length = 6)
	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Column(name = "city_code", nullable = false, length = 6)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "city_name", nullable = false, length = 20)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "zip_code", length = 6)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "is_main", nullable = false)
	public byte getIsMain() {
		return this.isMain;
	}

	public void setIsMain(byte isMain) {
		this.isMain = isMain;
	}

}
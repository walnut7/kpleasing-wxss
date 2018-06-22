package com.kpleasing.wxss.protocol.request;

import java.io.Serializable;
import java.util.List;

public class SyncCarInfoRequest extends RequestHead implements Serializable {

	/** *  */
	private static final long serialVersionUID = 8799882980624489833L;
	
	private String model_id;

	private String first_letter;
	
	private String brand;
	
	private String series;
	
	private String series_logo;
	
	private String model;
	
	private String sale_status;
	
	private String menufactory;
	
	private String year;
	
	private String public_time;
	
	private String msrp;
	
	private String model_type;
	
	private String enabled_flag;
	
	private List<SyncCarParam> car_params;

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getFirst_letter() {
		return first_letter;
	}

	public void setFirst_letter(String first_letter) {
		this.first_letter = first_letter;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getSeries_logo() {
		return series_logo;
	}

	public void setSeries_logo(String series_logo) {
		this.series_logo = series_logo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSale_status() {
		return sale_status;
	}

	public void setSale_status(String sale_status) {
		this.sale_status = sale_status;
	}

	public String getMenufactory() {
		return menufactory;
	}

	public void setMenufactory(String menufactory) {
		this.menufactory = menufactory;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPublic_time() {
		return public_time;
	}

	public void setPublic_time(String public_time) {
		this.public_time = public_time;
	}

	public String getMsrp() {
		return msrp;
	}

	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}

	public String getModel_type() {
		return model_type;
	}

	public void setModel_type(String model_type) {
		this.model_type = model_type;
	}
	
	public String getEnabled_flag() {
		return enabled_flag;
	}

	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}

	public List<SyncCarParam> getCar_params() {
		return car_params;
	}

	public void setCar_params(List<SyncCarParam> car_params) {
		this.car_params = car_params;
	}
	
}

package com.kpleasing.wxss.protocol.request;

import java.io.Serializable;

public class SyncFinSchemeRequest extends RequestHead implements Serializable {

	/** *  */
	private static final long serialVersionUID = 5513325507697796388L;
	
	private String plan_id;
	
	private String plan_desc;
	
	private String bp_id;
	
	private String model_id;
	
	private String lease_item_amount;
	
	private String downpayment_amount;
	
	private String deposit_amount;
	
	private String purchase_tax;
	
	private String insurance_fee_financing;
	
	private String car_plate_fee;
	
	private String gps_fee;
	
	private String taxinsurance;
	
	private String pckprice;
	
	private String lease_times;
	
	private String plan_type;
	
	private String rental;
	
	private String int_rate;
	
	private String rental_1_12;
	
	private String rental_13_48;
	
	private String buyout_amount;
	
	private String valid_date_from;
	
	private String valid_date_to;
	
	private String plan_synopsis;

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getPlan_desc() {
		return plan_desc;
	}

	public void setPlan_desc(String plan_desc) {
		this.plan_desc = plan_desc;
	}

	public String getBp_id() {
		return bp_id;
	}

	public void setBp_id(String bp_id) {
		this.bp_id = bp_id;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getLease_item_amount() {
		return lease_item_amount;
	}

	public void setLease_item_amount(String lease_item_amount) {
		this.lease_item_amount = lease_item_amount;
	}

	public String getDownpayment_amount() {
		return downpayment_amount;
	}

	public void setDownpayment_amount(String downpayment_amount) {
		this.downpayment_amount = downpayment_amount;
	}

	public String getDeposit_amount() {
		return deposit_amount;
	}

	public void setDeposit_amount(String deposit_amount) {
		this.deposit_amount = deposit_amount;
	}

	public String getPurchase_tax() {
		return purchase_tax;
	}

	public void setPurchase_tax(String purchase_tax) {
		this.purchase_tax = purchase_tax;
	}

	public String getInsurance_fee_financing() {
		return insurance_fee_financing;
	}

	public void setInsurance_fee_financing(String insurance_fee_financing) {
		this.insurance_fee_financing = insurance_fee_financing;
	}

	public String getCar_plate_fee() {
		return car_plate_fee;
	}

	public void setCar_plate_fee(String car_plate_fee) {
		this.car_plate_fee = car_plate_fee;
	}

	public String getGps_fee() {
		return gps_fee;
	}

	public void setGps_fee(String gps_fee) {
		this.gps_fee = gps_fee;
	}

	public String getTaxinsurance() {
		return taxinsurance;
	}

	public void setTaxinsurance(String taxinsurance) {
		this.taxinsurance = taxinsurance;
	}

	public String getPckprice() {
		return pckprice;
	}

	public void setPckprice(String pckprice) {
		this.pckprice = pckprice;
	}

	public String getLease_times() {
		return lease_times;
	}

	public void setLease_times(String lease_times) {
		this.lease_times = lease_times;
	}

	public String getPlan_type() {
		return plan_type;
	}

	public void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}

	public String getRental() {
		return rental;
	}

	public void setRental(String rental) {
		this.rental = rental;
	}

	public String getInt_rate() {
		return int_rate;
	}

	public void setInt_rate(String int_rate) {
		this.int_rate = int_rate;
	}

	public String getRental_1_12() {
		return rental_1_12;
	}

	public void setRental_1_12(String rental_1_12) {
		this.rental_1_12 = rental_1_12;
	}

	public String getRental_13_48() {
		return rental_13_48;
	}

	public void setRental_13_48(String rental_13_48) {
		this.rental_13_48 = rental_13_48;
	}

	public String getBuyout_amount() {
		return buyout_amount;
	}

	public void setBuyout_amount(String buyout_amount) {
		this.buyout_amount = buyout_amount;
	}

	public String getValid_date_from() {
		return valid_date_from;
	}

	public void setValid_date_from(String valid_date_from) {
		this.valid_date_from = valid_date_from;
	}

	public String getValid_date_to() {
		return valid_date_to;
	}

	public void setValid_date_to(String valid_date_to) {
		this.valid_date_to = valid_date_to;
	}

	public String getPlan_synopsis() {
		return plan_synopsis;
	}

	public void setPlan_synopsis(String plan_synopsis) {
		this.plan_synopsis = plan_synopsis;
	}

}

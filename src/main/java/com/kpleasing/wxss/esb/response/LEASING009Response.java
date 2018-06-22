package com.kpleasing.wxss.esb.response;

import java.io.Serializable;

public class LEASING009Response extends ESBResponseHead implements Serializable {
	
	/**	 * 	 */
	private static final long serialVersionUID = 2389861574536080175L;
	private String result_code;  
	private String result_desc;
	private String status;      // 业务订单状态
	private String applyno;      // 合同编号
	private String finance_id;  // 报价方案ID
	private String brand;    // 品牌
	private String series;    // 车系
	private String model;     // 车型
	private String sku_desc; // 宝贝名称
	private String down_payment;    // 首付款
	private String deposit;         // 保证金
	private String finance_amount;  // 贷款金额
	private String total_term;          // 期数
	private String repay_date;  //  每月还款日
	private String total_principal;   //  本金
	private String total_interest;     // 利息
	private String received_principal;   // 已还本金
	private String last_interest;       // 剩余利息
	private String finance_type;        // 资金类型，0-自有资金， 1-浦发资金
	
	public String getResult_code() {
		return result_code;
	}
	
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getResult_desc() {
		return result_desc;
	}

	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyno() {
		return applyno;
	}

	public void setApplyno(String applyno) {
		this.applyno = applyno;
	}

	public String getFinance_id() {
		return finance_id;
	}

	public void setFinance_id(String finance_id) {
		this.finance_id = finance_id;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSku_desc() {
		return sku_desc;
	}

	public void setSku_desc(String sku_desc) {
		this.sku_desc = sku_desc;
	}

	public String getDown_payment() {
		return down_payment;
	}

	public void setDown_payment(String down_payment) {
		this.down_payment = down_payment;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getFinance_amount() {
		return finance_amount;
	}

	public void setFinance_amount(String finance_amount) {
		this.finance_amount = finance_amount;
	}

	public String getTotal_term() {
		return total_term;
	}

	public void setTotal_term(String total_term) {
		this.total_term = total_term;
	}

	public String getRepay_date() {
		return repay_date;
	}

	public void setRepay_date(String repay_date) {
		this.repay_date = repay_date;
	}

	public String getTotal_principal() {
		return total_principal;
	}

	public void setTotal_principal(String total_principal) {
		this.total_principal = total_principal;
	}

	public String getTotal_interest() {
		return total_interest;
	}

	public void setTotal_interest(String total_interest) {
		this.total_interest = total_interest;
	}

	public String getReceived_principal() {
		return received_principal;
	}

	public void setReceived_principal(String received_principal) {
		this.received_principal = received_principal;
	}

	public String getLast_interest() {
		return last_interest;
	}

	public void setLast_interest(String last_interest) {
		this.last_interest = last_interest;
	}

	public String getFinance_type() {
		return finance_type;
	}

	public void setFinance_type(String finance_type) {
		this.finance_type = finance_type;
	}
}

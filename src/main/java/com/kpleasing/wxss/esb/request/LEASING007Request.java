package com.kpleasing.wxss.esb.request;

import java.io.Serializable;

public class LEASING007Request extends ESBRequestHead implements Serializable {
	/**	 * 	 */
	private static final long serialVersionUID = 7556584256966567608L;
	private String cust_id;
	private String cert_code;
	private String agent_name;
	private String finance_plan;
	private String agent_code;
	private String sale_id;

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getCert_code() {
		return cert_code;
	}

	public void setCert_code(String cert_code) {
		this.cert_code = cert_code;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getFinance_plan() {
		return finance_plan;
	}

	public void setFinance_plan(String finance_plan) {
		this.finance_plan = finance_plan;
	}

	public String getAgent_code() {
		return agent_code;
	}

	public void setAgent_code(String agent_code) {
		this.agent_code = agent_code;
	}

	public String getSale_id() {
		return sale_id;
	}

	public void setSale_id(String sale_id) {
		this.sale_id = sale_id;
	}
}

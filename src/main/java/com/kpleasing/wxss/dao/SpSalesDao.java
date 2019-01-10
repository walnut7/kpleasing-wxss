package com.kpleasing.wxss.dao;

import java.util.List;

import com.kpleasing.wxss.entity.SpSales;

public interface SpSalesDao extends BaseDao<SpSales, Integer> {

	public SpSales findSalesInfoBySaleId(Integer saleId);
	
	public List<SpSales> findSalesListByBpId(Integer bpId);

	public void updateSalesBySQL(Integer bpId);
}

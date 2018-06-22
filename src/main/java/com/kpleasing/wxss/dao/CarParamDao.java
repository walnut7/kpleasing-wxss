package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.CarParam;

public interface CarParamDao extends BaseDao<CarParam, Integer> {

	public CarParam findCarParamByPoropertys(Integer modelId, String param_name);

}

package com.kpleasing.wxss.dao;

import java.util.List;

import com.kpleasing.wxss.entity.Car;
import com.kpleasing.wxss.entity.SpCar;

public interface CarDao extends BaseDao<Car, Integer> {
	public List<Car> getSpCarList(Integer bp_id, String bp_code);

	public Car findCarInfoByModelId(Integer modelId);
}

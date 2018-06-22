package com.kpleasing.wxss.dao;

import java.util.List;

import com.kpleasing.wxss.entity.Car;
import com.kpleasing.wxss.entity.SpCar;

public interface SpCarDao extends BaseDao<SpCar, Integer> {
	public List<SpCar> getSpCarList(Integer bpId, String bpCode);
	
	public SpCar getSpCarByModelIdUniqueOne(Integer bpId,String bpCode,Integer modelId);

}

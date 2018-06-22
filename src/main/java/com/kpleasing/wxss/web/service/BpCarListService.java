package com.kpleasing.wxss.web.service;

import java.util.List;

import com.kpleasing.wxss.entity.Car;
import com.kpleasing.wxss.entity.Scheme;
import com.kpleasing.wxss.entity.SpCar;

/**
 * 门店车辆信息查询
 * 
 * @author zhangxing
 *
 */
public interface BpCarListService {

	
	
	/**
	 * 根据bp_id,bp_code查询SP下面准入的车型信息
	 * @param bp_id
	 * @param bp_code
	 * @return
	 */
	public List<SpCar> getSpCarList(Integer bpId,String bpCode);
	
	/**
	 * 通过SP及车型信息，查找方案信息
	 * @param bpId
	 * @param bpCode
	 * @param modelId
	 * @return
	 */
	public List<Scheme> getSchemeList(Integer bpId,String bpCode,Integer modelId);
	
	/**
	 * 通过modelId查询车辆基本信息
	 * @param modelId 车型ID
	 * @return
	 */
	public Car getCarInfo(Integer modelId);
	
	/**
	 * 获取SP车型的唯一描述
	 * @param bpId
	 * @param bpCode
	 * @param modelId
	 * @return
	 */
	public SpCar getSpCarByModelIdUniqueOne(Integer bpId,String bpCode,Integer modelId);

}

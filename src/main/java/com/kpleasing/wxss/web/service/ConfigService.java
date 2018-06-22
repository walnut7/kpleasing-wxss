package com.kpleasing.wxss.web.service;

import java.util.List;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.City;
import com.kpleasing.wxss.entity.DictConfig;
import com.kpleasing.wxss.entity.Province;

public interface ConfigService {
	
	public Configurate getConfig();
	
	
	public List<DictConfig> getDictByPId(Integer pId);
	
	
	public String getDictByCode(String code);


	/**
	 * 省份配置信息
	 * @return
	 */
	public List<Province> getProvinceList();


	/**
	 * 
	 * @param provinceCode
	 * @return
	 */
	public List<City> getCities(String provinceCode);

}

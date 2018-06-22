package com.kpleasing.wxss.web.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.dao.CarDao;
import com.kpleasing.wxss.dao.SchemeDao;
import com.kpleasing.wxss.dao.SpCarDao;
import com.kpleasing.wxss.dao.SpInfoDao;
import com.kpleasing.wxss.dao.impl.CarDaoImpl;
import com.kpleasing.wxss.dao.impl.SpInfoDaoImpl;
import com.kpleasing.wxss.entity.Car;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.entity.Scheme;
import com.kpleasing.wxss.entity.SpCar;
import com.kpleasing.wxss.entity.SpInfo;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.web.service.BpCarListService;

/**
 * 处理Sp与车型相关的信息
 * 
 * @author zhangxing
 *
 */
@Service("BpCarListService")
@Transactional
public class BpCarListServiceImpl implements Serializable, BpCarListService {

	/** * */
	private static final long serialVersionUID = 7535541948391979579L;
	private static final Logger logger = Logger.getLogger(BpCarListServiceImpl.class);

	@Autowired
	private SpCarDao spCarDao;
	
	@Autowired
	private SchemeDao schemeDao;
	
	@Autowired
	private CarDao carDao;

	/**
	 * 获取SP下的车型及报价汇总信息
	 */
	@SuppressWarnings("null")
	@Override
	public List<SpCar> getSpCarList(Integer bpId, String bpCode) {
		 List<SpCar> cars=(List<SpCar>) spCarDao.getSpCarList(bpId, bpCode);
		return cars;
	}
	
	@Override
	public List<Scheme> getSchemeList(Integer bpId,String bpCode,Integer modelId){
		 List<Scheme> schemes= schemeDao.getSchemeByModel(bpId, bpCode,modelId);
		 
		 return schemes;
	}
	
	@Override
	public Car getCarInfo(Integer modelId) {
		Car car=carDao.findCarInfoByModelId(modelId);
		return car;
	}
	
	@Override
	public SpCar getSpCarByModelIdUniqueOne(Integer bpId,String bpCode,Integer modelId) {
		SpCar spcar=spCarDao.getSpCarByModelIdUniqueOne(bpId, bpCode, modelId);
		return spcar;
	}
}

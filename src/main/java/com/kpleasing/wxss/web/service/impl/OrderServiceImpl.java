package com.kpleasing.wxss.web.service.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.dao.CarDao;
import com.kpleasing.wxss.dao.FaceVideoDao;
import com.kpleasing.wxss.dao.OrderDao;
import com.kpleasing.wxss.dao.SchemeDao;
import com.kpleasing.wxss.dao.SpInfoDao;
import com.kpleasing.wxss.entity.Car;
import com.kpleasing.wxss.entity.FaceVideo;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.entity.Scheme;
import com.kpleasing.wxss.entity.SpInfo;
import com.kpleasing.wxss.enums.LOGIN_TYPE;
import com.kpleasing.wxss.esb.action.Leasing009Action;
import com.kpleasing.wxss.esb.response.LEASING009Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.web.service.ConfigService;
import com.kpleasing.wxss.web.service.OrderService;
import com.kpleasing.wxss.web.spring.SpringContextHolder;

@Service("OrderService")
@Transactional
public class OrderServiceImpl implements Serializable, OrderService {

	/**	 * 	 */
	private static final long serialVersionUID = 7535541948391979579L;
	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private FaceVideoDao faceVideoDao;
	
	@Autowired
	private SpInfoDao spInfoDao;
	
	@Autowired
	private SchemeDao schemeDao;
	
	@Autowired
	private CarDao carDao;
	
	
	@Override
	public Order getCurrentOrderByLoginUser(LoginUser loginUser) {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(loginUser.getUserId()));
		if(null == order) {
			logger.info("用户不存在任何信息，开始新增用户信息......！");
			order = new Order();
			order.setCustId(Integer.valueOf(loginUser.getUserId()));
			order.setPhone(loginUser.getPhone());
			order.setStatus((byte)1);
			
			if(StringUtils.isNotBlank(loginUser.getOpenId())) {
				order.setWxOpenId(loginUser.getOpenId());
			}
			
			order.setCreateAt(DateUtil.getDate());
			order.setUpdateAt(DateUtil.getDate());
			orderDao.save(order);
		}
		return order;
	}


	@Override
	public void setBpInfo(Order order, String bpId, String bpCode, String planId, String saleId) {
		if(StringUtils.isNotBlank(bpId)) {
			order.setBpId(Integer.valueOf(bpId));
		}
		
		if(StringUtils.isNotBlank(bpCode)) {
			order.setBpCode(bpCode);
		}
		
		if(StringUtils.isNotBlank(planId)) {
			order.setLoginChannel((byte)LOGIN_TYPE.MANUAL.CODE);
			order.setPlanId(Integer.valueOf(planId));
		}
		
		if(StringUtils.isNotBlank(saleId)) {
			order.setSaleId(Integer.valueOf(saleId));
		}
		order.setUpdateAt(DateUtil.getDate());
		orderDao.update(order);
	}


	@Override
	public void updateStepNo(Integer custId, byte stepNo) {
		Order order = (Order) orderDao.findByCustId(custId);
		if(null != order) {
			order.setStepNo(stepNo);
			order.setUpdateAt(DateUtil.getDate());
			orderDao.update(order);
		}
	}


	@Override
	public void updateStepNo(Order order, byte stepNo) {
		if(null != order) {
			order.setStepNo(stepNo);
			order.setUpdateAt(DateUtil.getDate());
			orderDao.update(order);
		}
	}


	@Override
	public void initFaceVideoInfo(String custId, String bpId, String planId) {
		FaceVideo fVideo = faceVideoDao.findByCustId(Integer.valueOf(custId));
		
		SpInfo sp = null;
		if(StringUtils.isNotBlank(bpId)) {
			sp = spInfoDao.findSpInfoByBpId(Integer.valueOf(bpId));
		}
		
		Scheme scheme = null;
		if(StringUtils.isNotBlank(planId)) {
			scheme = schemeDao.findSchemeByPlanId(Integer.valueOf(planId));
		}
		
		Car carInfo = null;
		if(null != scheme ) {
			carInfo = carDao.findCarInfoByModelId(scheme.getModelId());
		}
		
		if(null != fVideo) {
			if(null != sp) {
				fVideo.setSpName(sp.getBpName());
			}
			if(null != scheme) {
				fVideo.setScheme(scheme.getPlanDesc());
			}
			if(null != carInfo) {
				fVideo.setBrand(carInfo.getBrand());
				fVideo.setSeries(carInfo.getSeries());
				fVideo.setModel(carInfo.getModel());
				fVideo.setSeriesLogo(carInfo.getSeriesLogo());
			}
			fVideo.setUpdateAt(DateUtil.getDate());
			faceVideoDao.update(fVideo);
		} else {
			fVideo = new FaceVideo();
			if(null != sp) {
				fVideo.setSpName(sp.getBpName());
			}
			if(null != scheme) {
				fVideo.setScheme(scheme.getPlanDesc());
			}
			if(null != carInfo) {
				fVideo.setBrand(carInfo.getBrand());
				fVideo.setSeries(carInfo.getSeries());
				fVideo.setModel(carInfo.getModel());
				fVideo.setSeriesLogo(carInfo.getSeriesLogo());
			}
			fVideo.setCustId(Integer.valueOf(custId));
			fVideo.setCreateAt(DateUtil.getDate());
			fVideo.setUpdateAt(DateUtil.getDate());
			faceVideoDao.save(fVideo);
		}
	}

	
	@Override
	public String getRunningOrder(String custId) throws WXSSException {
		Order order = (Order) orderDao.findByCustId(Integer.valueOf(custId));
		if(order.getLoginChannel()==LOGIN_TYPE.MANUAL.CODE) {
			logger.info("start to search order info .....");
			Leasing009Action leasing009Action = SpringContextHolder.getBean(Leasing009Action.class);
			
			Configurate config = configService.getConfig();
			LEASING009Response leasing009Response = leasing009Action.doRequest(config, custId, null);
			
			if(!"SUCCESS".equals(leasing009Response.getResult_code())) {
				throw new WXSSException("ERROR", leasing009Response.getResult_desc());
			}
			return leasing009Response.getApplyno();
		} else {
			return null;
		}
	}
}

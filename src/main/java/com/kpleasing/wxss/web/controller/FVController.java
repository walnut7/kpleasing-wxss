package com.kpleasing.wxss.web.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.kpleasing.wxss.pojo.FVNewTask;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.HttpHelper;
import com.kpleasing.wxss.util.JsonUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/fv")
public class FVController {
	
	private static final Logger logger = Logger.getLogger(FVController.class);
	
	
	@RequestMapping(value = "callback", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SyncSPInfo(@RequestBody String requestBody) throws Exception {
		return requestBody;
	}
	
	
	/**
	 * 获取签名信息
	 * @param bizCode
	 * @param tm
	 * @param random
	 * @return
	 */
	@RequestMapping(value = "getSign", method = RequestMethod.POST)
	public @ResponseBody String saveCertInfoA(String bizCode, String tm, String random) {
		String key = "2s2LGZBOHJG9NFeL8B697sGvNvis8MZv";
		return Security.MD5Encode(bizCode+tm+key+random).toUpperCase();
	}
	
	
	public static void main123(String[] argc) {
		String key = "2s2LGZBOHJG9NFeL8B697sGvNvis8MZv";
		FVNewTask fvTask = new FVNewTask();
		fvTask.setAction("NEW_TASK");
		fvTask.setBizCode("B40058048");
		fvTask.setTm(DateUtil.getCurrentDate(DateUtil.yyyy_MM_ddHHmmss));
		fvTask.setRandom(StringUtil.getRandomVerifCode6());
		fvTask.setSign(fvTask.MD5Sign("2s2LGZBOHJG9NFeL8B697sGvNvis8MZv"));
		fvTask.setAppCode("A544650");
		fvTask.setBizId("1112");
		fvTask.setCustName("张三");
		fvTask.setCustMobile("13185807938");
		fvTask.setCustIdcardNbr("");
		fvTask.setBizInfo("333212");
		fvTask.setComment("坤鹏视屏面签");
		fvTask.setIsRecordOn("NO");
		fvTask.setStorageYear("30");
		fvTask.setIsScreenShotOn("NO");
		fvTask.setIsAiOn("NO");
		fvTask.setIsFaceCheckOn("NO");
		fvTask.setIsOcrOn("NO");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "NEW_TASK");
		map.put("bizCode", "B40058048");
		map.put("tm", DateUtil.getCurrentDate(DateUtil.yyyy_MM_ddHHmmss));
		map.put("random", StringUtil.getRandomVerifCode6());
		
		String a = map.get("bizCode")+map.get("tm")+key+map.get("random");
		logger.info("签名串："+a);
		String sign = Security.MD5Encode(a).toUpperCase();
		logger.info("签名值："+sign);
		
		map.put("sign", sign);
		map.put("appCode", "A544650");
		
		String resut = Post("https://console.jianmianqian.com/api", map);
		
		logger.info(resut);
	}
	
	
	
	public static String Post(String url, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		String param = "";
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			param = sb.toString();
			param = param.substring(0, sb.length() - 1);
		}
		logger.info(param);
		
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("contentType", "UTF-8");
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(param);
			osw.flush();
			osw.close();
		} catch (Exception e) {
			//logger.error(Tool.GetStackTrace(e));
			return null;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (Exception e) {
			//logger.error(Tool.GetStackTrace(e));
			return null;
		}
		return buffer.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "qrcode")
	public ModelAndView getQRCode(HttpServletRequest request, String custId) {
		logger.info("start login.......");
		ModelMap model = new ModelMap();
		
		String url = "https://console.jianmianqian.com/api";
		FVNewTask fvTask = new FVNewTask();
		fvTask.setAction("NEW_TASK");
		fvTask.setBizCode("B40058048");
		fvTask.setTm(DateUtil.getCurrentDate(DateUtil.yyyy_MM_ddHHmmss));
		fvTask.setRandom(StringUtil.getRandomVerifCode6());
		fvTask.setSign(fvTask.MD5Sign("2s2LGZBOHJG9NFeL8B697sGvNvis8MZv"));
		fvTask.setAppCode("A544650");
		fvTask.setBizId("1112");
		fvTask.setCustName("张三");
		fvTask.setCustMobile("13185807938");
		fvTask.setCustIdcardNbr("");
		fvTask.setBizInfo("333212");
		fvTask.setComment("坤鹏视屏面签");
		fvTask.setIsRecordOn("NO");
		fvTask.setStorageYear("30");
		fvTask.setIsScreenShotOn("NO");
		fvTask.setIsAiOn("NO");
		fvTask.setIsFaceCheckOn("NO");
		fvTask.setIsOcrOn("NO");
		
		// String content = JsonUtil.beanToJson(fvTask);
		// String content = JsonUtil.JSON_Bean2String(fvTask);
		
		StringBuilder sb = new StringBuilder();
		sb.append("action=NEW_TASK&bizCode=B40058048&tm=").append(fvTask.getTm())
		.append("&random=").append(fvTask.getRandom()).append("&sign=").append(fvTask.MD5Sign("2s2LGZBOHJG9NFeL8B697sGvNvis8MZv"))
		.append("&appCode=A544650");
		
		String content = sb.toString();
		
		logger.info(content);
		String result = HttpHelper.doHttpPost(url, content, "application/x-www-form-urlencoded");
		logger.info(result);
		
		Map<String, Object> m = parseJSON2Map(result);
		String qrUrl = "";
		if("SUCCESS".equals(m.get("result"))) {
			qrUrl = (String) ((Map<String, Object>)m.get("data")).get("qrUrl");
		}
		model.put("qr", qrUrl);
		return new ModelAndView("fvqr", model);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		FVController fv = new FVController();
		String result = "{\"result\":\"SUCCESS\",\"success\":true,\"comment\":\"T661384235\",\"data\":{\"taskId\":\"T661384235\",\"bizCode\":\"B40058048\",\"appCode\":\"A544650\",\"bizName\":\"信审视频审核\",\"name\":\"信审视频审核\",\"logoUrl\":\"https://console.jianmianqian.com/img/biz_logo.jpg\",\"groupId\":\"kp\",\"groupName\":\"坤鹏融资租赁\",\"startTime\":\"2018-07-17 10:25:04\",\"startUserId\":\"接口\",\"startUserName\":\"\",\"startMethod\":\"接口建单\",\"wxUserId\":\"\",\"wxUserName\":\"\",\"kfListId\":\"\",\"kfListName\":\"\",\"kfResultTime\":\"\",\"kfBizId\":\"\",\"kfCustName\":\"\",\"kfIdcardNbr\":\"\",\"kfMobile\":\"\",\"kfBizInfo\":\"\",\"kfComment\":\"\",\"endTime\":\"\",\"deleteTime\":\"\",\"endMsg\":\"\",\"qrUrl\":\"https://console.jianmianqian.com/DATA/TASK/20180717/T661384235/20180717102504_4HH7.JPG\",\"comment\":\"业务编号：#客户姓名：#客户手机号：\",\"files\":{}}}";
		Map<String, Object> m = parseJSON2Map(result);
		
		String qrUrl = "";
		if("SUCCESS".equals(m.get("result"))) {
			qrUrl = (String) ((Map<String, Object>)m.get("data")).get("qrUrl");
		}
		System.out.println(qrUrl);
		//String result = fv.getQRCode();
		
		String text="http://www.baidu.com";
		int width=100;
		int height=100;
		String format = "png";
		Hashtable<EncodeHintType, Comparable> hints = new Hashtable<EncodeHintType, Comparable>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			Path file=new File("D:/new.png").toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Json数据转换成Map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject objJson = JSONObject.fromObject(json);
		for (Object k : objJson.keySet()) {
			Object v = objJson.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject objJson2 = it.next();
					logger.info(objJson2.toString());
					list.add(parseJSON2Map(objJson2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
}

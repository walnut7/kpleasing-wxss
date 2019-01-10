package com.kpleasing.wxss.web.service;

import java.util.List;

import com.kpleasing.wxss.mongo.collections.SpdbInterfaceLogCollection;

public interface SpdbLogsService {

	public List<SpdbInterfaceLogCollection> findSpdbOperateLog(SpdbInterfaceLogCollection spdbInterfaceLogCollection);

}

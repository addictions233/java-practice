package com.one.domain.respository;

import com.one.domain.dp.SalesRep;

/**
 * 归属地
 */
public interface SalesRepRepository {

    /**
     * 通过手机的归属地获取销售组信息
     * @param areaCode 手机归属地编码
     * @param operatorCode 手机操作编码
     * @return 归属地信息
     */
    SalesRep findRep(String areaCode, String operatorCode);
}

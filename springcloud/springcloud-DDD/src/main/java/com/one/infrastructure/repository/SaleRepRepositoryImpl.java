package com.one.infrastructure.repository;

import com.one.domain.vo.SalesRep;
import com.one.domain.respository.SalesRepRepository;
import com.one.infrastructure.dao.SaleRepDao;

public class SaleRepRepositoryImpl implements SalesRepRepository {

    private SaleRepDao saleRepDao;

    /**
     * 通过手机的归属地获取销售组信息
     *
     * @param areaCode     手机归属地编码
     * @param operatorCode 手机操作编码
     * @return 归属地信息
     */
    @Override
    public SalesRep findRep(String areaCode, String operatorCode) {
        return saleRepDao.findByCode(areaCode, operatorCode);
    }
}

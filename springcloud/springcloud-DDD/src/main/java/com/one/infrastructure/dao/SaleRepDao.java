package com.one.infrastructure.dao;

import com.one.domain.vo.SalesRep;

public interface SaleRepDao {
    SalesRep findByCode(String areaCode, String operatorCode);
}

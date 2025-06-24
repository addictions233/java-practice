package com.one.infrastructure.dao;

import com.one.domain.dp.SalesRep;

public interface SaleRepDao {
    SalesRep findByCode(String areaCode, String operatorCode);
}

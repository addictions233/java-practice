package com.one.es.v7.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.one.es.v7.entity.ProductInfo;
import com.one.es.v7.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends ServiceImpl<ProductMapper, ProductInfo> {
}

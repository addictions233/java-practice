package com.one.test;

import com.one.bean.Country;
import com.one.service.CountryService;
import com.one.service.impl.CountryServiceImpl;

/**
 * @ClassName: CountryServiceTest
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/21
 */
public class CountryServiceTest {
    public static void main(String[] args) {
        CountryService countryService = new CountryServiceImpl();
        Country country = countryService.selectById(1);
        System.out.println(country);
    }

}

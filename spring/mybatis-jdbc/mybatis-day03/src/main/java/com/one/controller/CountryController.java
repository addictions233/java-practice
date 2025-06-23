package com.one.controller;

import com.one.bean.Country;
import com.one.service.CountryService;
import com.one.service.impl.CountryServiceImpl;

import java.util.List;

public class CountryController {
    public static void main(String[] args) {
        CountryService service = new CountryServiceImpl();
//        selectAll(service);
        Country country = service.selectById(1);
        System.out.println(country);
    }

    private static void selectAll(CountryService service) {
        List<Country> countries = service.selectAll();
        for (Country country : countries) {
            System.out.println(country);
        }
    }
}

package com.one.domain;

/**
 * @ClassName: Address
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/06
 */
public class Address {
    private String province;
    private String city;
    private String rode;

    public Address(String province, String city, String rode) {
        this.province = province;
        this.city = city;
        this.rode = rode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRode() {
        return rode;
    }

    public void setRode(String rode) {
        this.rode = rode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", rode='" + rode + '\'' +
                '}';
    }
}

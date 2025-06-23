package com.one.ThreadDemo2;

import java.util.Objects;

/**
 * @author one
 */
public class BaoZi {
    private String pier;
    private String xianer;
    private boolean flag = false;

    public BaoZi() {
    }

    public BaoZi(String pier, String xianer) {
        this.pier = pier;
        this.xianer = xianer;
    }

    public String getPier() {
        return pier;
    }

    public void setPier(String pier) {
        this.pier = pier;
    }

    public String getXianer() {
        return xianer;
    }

    public void setXianer(String xianer) {
        this.xianer = xianer;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaoZi baoZi = (BaoZi) o;
        return flag == baoZi.flag &&
                Objects.equals(pier, baoZi.pier) &&
                Objects.equals(xianer, baoZi.xianer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pier, xianer, flag);
    }

    @Override
    public String toString() {
        return "BaoZi{" +
                "pier='" + pier + '\'' +
                ", xianer='" + xianer + '\'' +
                ", flag=" + flag +
                '}';
    }


}

package com.one.poi.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName: PoiUtilsTest
 * @Description: TODO
 * @Author: one
 * @Date: 2021/10/27
 */
public class PoiUtilsTest {
    public static void main(String[] args) throws IOException {
        XWPFDocument document = new XWPFDocument();
        OutputStream outputStream = new FileOutputStream("C:\\Users\\one\\Desktop\\文档.docx");
        XWPFParagraph paragraph = document.createParagraph();
        String sectionTitle ="水果二";
        PoiUtils.setLevelTitle1(document,paragraph,sectionTitle);
        XWPFParagraph paragraph2 = document.createParagraph();
        String sectionTitle2 ="水果三";
        PoiUtils.setLevelTitle2(document,paragraph2,sectionTitle2);
        XWPFParagraph paragraph3 = document.createParagraph();
        String title2 = "苹果柠檬三";
        PoiUtils.setLevelTitle3(document,paragraph3,title2);
        XWPFParagraph paragraph4 = document.createParagraph();
        String title4 = "苹果柠檬四";
        PoiUtils.setLevelTitle3(document,paragraph4,title4);
        XWPFParagraph paragraph5 = document.createParagraph();
        String title5 = "苹果柠檬四";
        PoiUtils.setLevelTitle(document,paragraph5,title5,4);
        XWPFParagraph paragraph6 = document.createParagraph();
        String title6 = "苹果柠檬留";
        PoiUtils.setLevelTitle(document,paragraph6,title6,5);
        XWPFParagraph paragraph7 = document.createParagraph();
        String title7 = "苹果柠檬留";
        PoiUtils.setLevelTitle(document,paragraph7,title7,6);
        XWPFParagraph paragraph8 = document.createParagraph();
        String title8 = "苹果柠檬留";
        PoiUtils.setLevelTitle(document,paragraph8,title8,7);
        XWPFParagraph paragraph9 = document.createParagraph();
        String title9 = "苹果柠檬9";
        PoiUtils.setLevelTitle(document,paragraph9,title9,8);
        XWPFParagraph paragraph11 = document.createParagraph();
        String title11 = "苹果柠檬11";
        PoiUtils.setLevelTitle(document,paragraph11,title11,9);
        XWPFParagraph paragraph10 = document.createParagraph();
        String sectionTitle10 ="水果三";
        PoiUtils.setLevelTitle2(document,paragraph10,sectionTitle10);
        PoiUtils.init(document);

        document.write(outputStream);
        outputStream.close();
    }
}

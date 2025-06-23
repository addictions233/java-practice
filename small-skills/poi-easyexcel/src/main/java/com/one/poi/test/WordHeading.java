package com.one.poi.test;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.math.BigInteger;

/**
 * @ClassName: WordHeading
 * @Description: TODO
 * @Author: one
 * @Date: 2021/11/12
 */
public class WordHeading {
    public static void main(String[] args) throws IOException {
        XWPFDocument document = new XWPFDocument();
        OutputStream os = new FileOutputStream("H:\\测试文档.docx");
        XWPFStyles styles = document.createStyles();

        String heading1 = "My Heading 1";
        String heading2 = "My Heading 2";
        String heading3 = "My Heading 3";
        String heading4 = "My Heading 4";
        String heading5 = "My Heading 5";
        String heading6 = "My Heading 6";
        String heading7 = "My Heading 7";
        String heading8 = "My Heading 8";
        String heading9 = "My Heading 9";
        addCustomHeadingStyle(document, styles, heading1, 1);
        addCustomHeadingStyle(document, styles, heading2, 2);
        addCustomHeadingStyle(document, styles, heading3, 3);
        addCustomHeadingStyle(document, styles, heading4, 4);
        addCustomHeadingStyle(document, styles, heading5, 5);
        addCustomHeadingStyle(document, styles, heading6, 6);
        addCustomHeadingStyle(document, styles, heading7, 7);
        addCustomHeadingStyle(document, styles, heading8, 8);
        addCustomHeadingStyle(document, styles, heading9, 9);

        for (int i = 0; i < 100; i++) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle(heading1);
            XWPFRun run = paragraph.createRun();
            run.setText("Nice header!");

            XWPFParagraph para2 = document.createParagraph();
            para2.setStyle(heading2);
            XWPFRun run1 = para2.createRun();
            run1.setText("标题2");

            XWPFParagraph para3 = document.createParagraph();
            para3.setStyle(heading3);
            XWPFRun run2 = para3.createRun();
            run2.setText("标题3");

        }
        document.write(os);
        os.close();;

    }

    private static void addCustomHeadingStyle(XWPFDocument docxDocument, XWPFStyles styles, String strStyleId, int headingLevel) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull);

        // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        // is a null op if already defined
//        XWPFStyles styles = docxDocument.createStyles();

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }



    private static void addCustomHeadingStyle(XWPFDocument docxDocument, XWPFStyles styles, String strStyleId, int headingLevel, int pointSize, String hexColor) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);


        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull);

        // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        CTHpsMeasure size = CTHpsMeasure.Factory.newInstance();
        size.setVal(new BigInteger(String.valueOf(pointSize)));
        CTHpsMeasure size2 = CTHpsMeasure.Factory.newInstance();
        size2.setVal(new BigInteger("24"));

        CTFonts fonts = CTFonts.Factory.newInstance();
        fonts.setAscii("Loma" );

        CTRPr rpr = CTRPr.Factory.newInstance();
        rpr.setRFonts(fonts);
        rpr.setSz(size);
        rpr.setSzCs(size2);

        CTColor color=CTColor.Factory.newInstance();
        color.setVal(hexToBytes(hexColor));
        rpr.setColor(color);
        style.getCTStyle().setRPr(rpr);
        // is a null op if already defined

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }

    public static byte[] hexToBytes(String hexString) {
        HexBinaryAdapter adapter = new HexBinaryAdapter();
        byte[] bytes = adapter.unmarshal(hexString);
        return bytes;
    }
}

package com.one.poi.parser;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.XmlException;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ExcelParser {
    public static void parse () throws OpenXML4JException, IOException, XmlException {
        XSSFWorkbook workbook = new XSSFWorkbook(new File("C:\\Users\\one\\Desktop\\202212投保派单-增员&首次投保模板 (1)50W.xlsx"));
        System.out.println(workbook.getProperties().getCustomProperties().getProperty("template-version").getLpwstr());
        OPCPackage pkg = OPCPackage.open("C:\\Users\\one\\Desktop\\202212投保派单-增员&首次投保模板 (1)50W.xlsx", PackageAccess.READ);
        POIXMLProperties poixmlProperties = new POIXMLProperties(pkg);
        poixmlProperties.getCustomProperties().getProperty("aaa").getLpwstr();
        try {
            XSSFReader reader = new XSSFReader(pkg);
            SharedStringsTable sst = reader.getSharedStringsTable();
            StylesTable st = reader.getStylesTable();
            XMLReader parser = XMLReaderFactory.createXMLReader();
            // 处理公共属性：Sheet名，Sheet合并单元格
            parser.setContentHandler(new SheetHandler());
            /**
             * 返回一个迭代器，此迭代器会依次得到所有不同的sheet。 
             * 每个sheet的InputStream只有从Iterator获取时才会打开。 
             * 解析完每个sheet时关闭InputStream。
             * */
//            XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) reader.getSheetsData();
            InputStream workbookData = reader.getWorkbookData();
            InputSource workbook2 = new InputSource(workbookData);
            parser.parse(workbook2);
//            while (sheets.hasNext()) {
//                InputStream sheetstream = sheets.next();
//                InputSource sheetSource = new InputSource(sheetstream);
//                try {
//                    // 解析sheet: com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl:522
//                    parser.parse(sheetSource);
//                } finally {
//                    sheetstream.close();
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pkg.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws OpenXML4JException, IOException, XmlException {
        parse();
    }
}
package com.one.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: ApachepoiDemoTest
 * @Description: 单元测试
 * @Author: one
 * @Date: 2021/07/15
 */
@SpringBootTest
public class ApachepoiDemoTest {
    private static Map<String,String> template;
    static {
        template.put("姓名","name");
        template.put("年龄", "age");
        template.put("工资", "salary");
    }

    /**
     * 读取excel文件中的内容
     */
    @Test
    public void readerExcel() throws IOException, InvalidFormatException {
        File file = new File("F:\\test.xlsx");
        // XSSFWorkbook类是处理整个excel文件的对象
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        // 采用迭代器的方式遍历该exel文档中所有的sheet页
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            // 获取sheet页对应的sheet对象
            Sheet sheet = sheetIterator.next();
            HashMap<String,Integer> headerMap = new HashMap<>();
            HashMap<String,String> valueMap  = new HashMap<>();
            Row row1 = sheet.getRow(0);
            for (Cell cell : row1) {
                headerMap.put(cell.getStringCellValue(),cell.getColumnIndex());
            }
            System.out.println(headerMap);
            // 遍历sheet页,获取到每一行row
            for (Row row : sheet) {
                // 遍历每一个行,获取到每一个单元格cell
                for (Cell cell : row) {
                    // 获取到单元格中的文本格式的值
                    String cellValue = cell.getStringCellValue();
                    System.out.println(cellValue);
                }
            }
        }
    }
}

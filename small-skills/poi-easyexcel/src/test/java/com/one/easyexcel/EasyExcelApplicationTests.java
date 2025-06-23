package com.one.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.one.easyexcel.listener.DemoDataListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class EasyExcelApplicationTests {

	@Test
	void readExcel() {
		File excelFile = new File("D:\\aa.xlsx");
		EasyExcel.read(excelFile, new DemoDataListener()).extraRead(CellExtraTypeEnum.HYPERLINK).sheet().doRead();
	}

}

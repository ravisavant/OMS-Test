package com.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {

    public static List<Object[]> getTestData(String sheetName) {

        List<Object[]> data = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx");//sheet path

            Workbook book = WorkbookFactory.create(file);
            Sheet sheet = book.getSheet(sheetName);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) 
            {

                Row row = sheet.getRow(i);

                String searchType = row.getCell(0).toString().trim();
                String id = row.getCell(1).toString().replace(".0", "").trim();
                String expected = row.getCell(2).toString().trim();

                System.out.println("Running for: " + id);

                data.add(new Object[]{searchType, id, expected});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
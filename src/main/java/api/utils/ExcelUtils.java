package api.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtils(String excelPath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read Excel file: " + e.getMessage());
        }
    }

    public Map<String, String> getDataAsMap() {
        Map<String, String> dataMap = new HashMap<>();
        DataFormatter formatter = new DataFormatter();

        int rowCount = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < rowCount; i++) { // skip header
            Row row = sheet.getRow(i);

            if (row == null) continue;

            String key = formatter.formatCellValue(row.getCell(0)).trim();
            String value = formatter.formatCellValue(row.getCell(1)).trim();

            if (!key.isEmpty()) {
                dataMap.put(key, value);
            }
        }

        return dataMap;
    }
}

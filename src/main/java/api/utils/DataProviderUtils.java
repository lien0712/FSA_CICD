package api.utils;

import org.testng.annotations.DataProvider;

import java.util.Map;

public class DataProviderUtils {

    @DataProvider(name = "data")
    public Object[][] getDataSettings() {

        String path = "src/main/resources/Data.xlsx";

        ExcelUtils excel = new ExcelUtils(path, "Data");
        Map<String, String> dataMap = excel.getDataAsMap();

        return new Object[][] {
                { dataMap }
        };
    }
}

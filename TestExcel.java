package Utility;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.poi.ss.usermodel.Workbook;

public class TestExcel {
	
	
	@SuppressWarnings("deprecation")
	public static MultiMap<String, ?> excelData = new MultiValueMap();

	@SuppressWarnings("deprecation")
	public static void getTestData(String testDataPath, String sheetName) {

		try {
			FileInputStream fis =null;
					try {
						XlsReader excel = new XlsReader(testDataPath);
						fis = new FileInputStream(testDataPath);
						Workbook workbook = XlsReader.openWorkbook(fis);
						int colNum = XlsReader.getColumnCount(sheetName,workbook);
						System.out.println("Total Number of Columns: " + colNum);
						//Constants.logger.log(Level.INFO,"Total Number of Columns: " + colNum);

						int TD_Rowcount = excel.getRowCount(sheetName);
						for (int i = 1; i <= TD_Rowcount; i++) {

							for (int j = 1; j < colNum; j++) {
								String columnName = excel.getCellData(sheetName, j, 1);
								String testDataName = excel.getCellData(sheetName, i, "Scenario");
								String testDataValue = excel.getCellData(sheetName, i, columnName);
								excelData.put(testDataName, testDataValue);
							}
						}
					
					System.out.println("Adding TestData to multimap completed");
				//	Constants.logger.log(Level.INFO,"Adding TestData to multimap completed");
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					finally {
						XlsReader.closeFIS(fis);
					}

			
		} catch (Exception e) {
			e.printStackTrace();
			//Constants.logger.log(Level.SEVERE, "This message describes an Exception ", e);
		}
	}
	
	public static String getTestDataCellValue(String scenarioName, String colName) {
		String value = null;
	
		ArrayList<String> object = getTestDataRowValue(scenarioName);
		ArrayList<String> scenario = getTestDataRowValue("Scenario");
		
		int index = -1;
		for (int i = 0; i < scenario.size(); i++) {
			if (scenario.get(i).equals(colName)) {
				index = i;
				break;
			}
		}
		value = object.get(index);
	
		return value;
	}
		
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static ArrayList<String> getTestDataRowValue(String scenarioName) {
		ArrayList<String> object = null;
		try {
			object = (ArrayList<String>) excelData.get(scenarioName);
		} catch (Exception e) {
			System.out.println("There is no object by the name: " + scenarioName);
			//Constants.logger.log(Level.SEVERE, "This message describes an Exception ", e);
		}
		return object;
	}

}

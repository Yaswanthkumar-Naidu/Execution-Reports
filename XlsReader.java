package Utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsReader {

	public String path;
	public static FileInputStream fis = null;
	public static FileOutputStream fileOut = null;
	private static Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;

	public XlsReader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = openWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			//			fis.close();// how can we close here? then will we able to read anywhere?
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeFIS(fis);// how can we close here? then will we able to read anywhere? maybe for memory reason!
		}
	}

	public static Workbook openWorkbook(FileInputStream fis) throws IOException{
		return new XSSFWorkbook(fis);
		//return WorkbookFactory.create(fis);
	}

	public static int getColumnCount(String sheetName,Workbook wb) {

		return wb.getSheet(sheetName).getRow(0).getLastCellNum();
	}

	public static int getRowCount(String sheetName,Workbook wb) {

		return wb.getSheet(sheetName).getLastRowNum()+1;
	}

	public String getCellData(String sheetName, int rowNum, String colName) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);

			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
					break;
				}
			}
			if (col_Num == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);
			if (cell == null)
				return "";

			String CellData = null;
			switch (cell.getCellType()){
			case STRING:
				CellData = cell.getStringCellValue();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell))
				{
					CellData = String.valueOf(cell.getDateCellValue());
				}
				else
				{
					CellData = String.valueOf((long)cell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				CellData = Boolean.toString(cell.getBooleanCellValue());
				break;
			case BLANK:
				CellData = "";
				break;
			} 
			return CellData;

		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			String CellData = null;
			switch (cell.getCellType()){
			case STRING:
				CellData = cell.getStringCellValue();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell))
				{
					CellData = String.valueOf(cell.getDateCellValue());
				}
				else
				{
					CellData = String.valueOf((long)cell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				CellData = Boolean.toString(cell.getBooleanCellValue());
				break;
			case BLANK:
				CellData = "";
				break;
			} 
			return CellData;
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	// Get the row count from sheet
	public int getRowCount(String sheetName) {
		// Get the sheet name count
		//		System.out.println("Sheet Name ----" + sheetName);

		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;

			// System.out.println("number " + number);
			return number;
		}
	}

	public static void closeFIS(FileInputStream fis) {
		try{
			if(fis!=null)fis.close();
		}
		catch(Exception e) {			
		}

	}
}

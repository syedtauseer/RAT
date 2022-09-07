package generic;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class utility {
	public static String getProperty(String path, String key) {
		String v = " ";
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(path));
			v = p.getProperty(key);
		} catch (Exception e) {

		}
		return v;
	}

	public static String getXlData(String path, String sheetName, int row, int col) {
		String v = "";
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			v = wb.getSheet(sheetName).getRow(row).getCell(col).toString();
		} catch (Exception e) {

		}
		return v;
	}

	public static int getXlRowCount(String path, String sheetName) {
		int rowCount = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			rowCount = wb.getSheet(sheetName).getLastRowNum();
		} catch (Exception e) {

		}
		return rowCount;
	}

	public static int getXlCellCount(int row, String sheetName, String path) {
		int cellCount = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			cellCount = wb.getSheet(sheetName).getRow(row).getLastCellNum();
		} catch (Exception e) {

		}
		return cellCount;
	}

	public static Iterator<String[]> getDataFromExcel(String path, String sheetName) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			int rc = sheet.getLastRowNum();
			for (int i = 1; i <= rc; i++) {
				try {
					short cc = sheet.getRow(i).getLastCellNum();
					String[] cell = new String[cc];
					for (int j = 0; j < cc; j++) {
						try {
							String v = sheet.getRow(i).getCell(j).toString();
							cell[j] = v;
						} catch (Exception e) {
							cell[j] = " ";
						}
					}
					data.add(cell);
				} catch (Exception e) {

				}
				wb.close();
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data.iterator();
	}

}

package CargaDeHorasServiceNow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//Creamos una clase para leer de un archivo excel 

public class ReadExcelFile {

	public ReadExcelFile() {

	}

	// A. creamos un metodo que nos permita leer toda una hoja de Excel. Fila por fila y sus 
	// correspondientes celdas.
	// filepath: source del excel, sheetName: Nombre de la hoja del libro de excel
	public void readExcel(String filepath, String sheetName) throws IOException {
		//Creamos un objeto de tipo File
		File file = new File(filepath);

		// Creamos el inputStream donde se encuentra los datos del archivo que pasamos
		FileInputStream inputStream = new FileInputStream(file);

		// Creamos el objeto donde vamos a guardar el libro de excel. Con el inputStream el cual tiene todos los datos del archivo que cargamos
		XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream);

		// Creamos el objeto donde vamos a guardar la hoja con la que estamos trabajamos
		XSSFSheet newSheet = newWorkBook.getSheet(sheetName);

		// Guardamos cuantas filas de datos tiene este excel
		int rowCount = newSheet.getLastRowNum() - newSheet.getFirstRowNum();

		for (int i = 0; i <= rowCount; i++) {
			// creamos el objeto fila que va a ir leyendo fila por fila de la hoja de excel
			XSSFRow row = newSheet.getRow(i);
			// en cada fila iteramos en todas las celdas
			for (int j = 0; j < row.getLastCellNum(); j++) { // getLastCellNum() nos va a decir la cantidad de celdas
																// que tiene esa fila
				System.out.println(row.getCell(j).getStringCellValue() + "||");
			}

		}

	}

	// B. Creamos un metodo que nos permita leer el valor especifico de una celda  
	
	public String getCellValue(String filepath, String sheetName, int rowNumber, int cellNumber) throws IOException {
		//creamos un archivo
		File file = new File(filepath);
		
		//creamos un inputStream para poder leer los datos del archivo
		FileInputStream inputStream = new FileInputStream(file);
		
		//creamos el libro de excel, le pasamos el inputStream
		XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);
		
		//creamos la hoja y le pasamos el nombre de la hoja
		XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
		
		//vamos a leer una celda dentro de una row especifica, entonces creamos:
		XSSFRow row = newSheet.getRow(rowNumber); 
		
		//definimos la celda
		XSSFCell cell = row.getCell(cellNumber);
		
		//devolvemos la informacion de esta celda que se encuentra en la 
		//fila rowNumber y en la columna cellNumber
		return cell.getStringCellValue();
	}
	
	
	
	
	
	
	
	
	
	
	

}

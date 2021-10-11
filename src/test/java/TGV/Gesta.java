package TGV;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Gesta {

	// defino las fechas que voy a pasar origen - destino
	String fechaOrigen = "17-05-2021";
	String fechaDestino = "28-05-2021";

	//chequeoFechasParaLoop(fechaOrigen, fechaDestino);
	
	// defino locators
	private WebDriver driver;
	WebDriverWait wait;
	By userField = By.id("Usuario");
	By passField = By.id("Password");
	By botonAceptar = By.cssSelector("input.btn.btn-lg.btn-primary.btn-block");
	By calendButton = By.id("menu_misHoras");
	By fechaCalendCarga = By.id("inFechaCalendar");
	By lunes = By.xpath("//*[@id=\"Calendar\"]/div[2]/div/table/tbody/tr/td/div[1]/div/div[1]/table/tbody/tr/td[2]");
	By martes = By.xpath(" //*[@id=\"Calendar\"]/div[2]/div/table/tbody/tr/td/div[1]/div/div[2]/table/tbody/tr/td[3]");

	By miercoles = By
			.xpath("//*[@id=\"Calendar\"]/div[2]/div/table/tbody/tr/td/div[1]/div/div[2]/table/tbody/tr/td[4]");
	By jueves = By.xpath("//*[@id=\"Calendar\"]/div[2]/div/table/tbody/tr/td/div[1]/div/div[2]/table/tbody/tr/td[5]");
	By viernes = By.xpath("//*[@id=\"Calendar\"]/div[2]/div/table/tbody/tr/td/div[1]/div/div[2]/table/tbody/tr/td[6]");

	By fechaDesde = By.id("fechaCalendCarga");
	By fechaHasta = By.id("inFechaHasta");
	By proyecto = By.id("selProyecto");
	By tarea = By.id("selTarea");
	By tareasRecientes = By.id("selUltimaTarea");
	By botonAceptarHoras = By.id("btnAceptar");
	By comentarioText = By.id("inComentario");
	By botonSemSig = By.cssSelector("/html/body/div[7]/div[1]/div[1]/div/button[2]/span");

	@Before
	public void setup1() {
		System.setProperty("webdriver.chrome.driver", "..\\CargarHoras\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://gesta.tgv.com.ar/gesta");
		driver.manage().window().maximize();
	}

	@Test
	public void test1() throws InterruptedException {
		wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(userField)).sendKeys("lrlopez");
		driver.findElement(passField).sendKeys("Cambio11223355");

		driver.findElement(botonAceptar).click();
        Thread.sleep(3000);
		// localizo el boton de "Mis Horas" y lo clickeo
		wait.until(ExpectedConditions.visibilityOfElementLocated(calendButton)).click();
		// le doy un click al Lunes donde me encuentro

//       //le indico la fecha desde que tiene que cargar

		try {
     	 WebElement w1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inFechaCalendar")));
    	 w1.sendKeys(Keys.CONTROL, "a");
    	 w1.sendKeys(Keys.DELETE);
     	 w1.sendKeys(fechaOrigen , Keys.ENTER, Keys.TAB);
   		} catch(Exception e){
    	 System.out.println("entro en el catch de fecha desde");
     }
		
		// le indico la fecha destino
  //     wait.until(ExpectedConditions.visibilityOfElementLocated(fechaHasta)).sendKeys("fechaDestino");
		// le doy un click a proyecto y selecciono la primer opcion (Analisis funcional
		// Varios)

		for (int i = 0; i < 2; i++) {
			try {
				try {
					WebElement wLunes = wait.until(ExpectedConditions.presenceOfElementLocated(lunes));
					wLunes.click();
				} catch (Exception e) {
				}
				ingresarDatosFecha();

				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("Entro en el catch Lunes");
				WebElement w1 = driver.findElement(By.xpath("/html/body/div[7]/div[2]/div/table/tbody/tr/td/div[1]/div/div[2]/table/tbody/tr/td[2]"));
				w1.click();
			}
			Thread.sleep(2000);
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(martes)).click();
				Thread.sleep(2000);
				ingresarDatosFecha();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("Entro en el catch Martes");
				WebElement w1 = driver.findElement(By.xpath("/html/body/div[7]/div[2]/div/table/tbody/tr/td/div[1]/div/div[2]/table/tbody/tr/td[3]"));
				w1.click();				
				
			}
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(miercoles)).click();
				Thread.sleep(2000);
				ingresarDatosFecha();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("Entro en el catch Miercoles");
				WebElement w1 = driver.findElement(By.xpath("/html/body/div[7]/div[2]/div/table/tbody/tr/td/div[1]/div/div[1]/table/tbody/tr/td[4]"));
				w1.click();		
			}
			
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(jueves)).click();
				Thread.sleep(2000);
				ingresarDatosFecha();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("Entro en el catch Jueves");
				WebElement w1 = driver.findElement(By.xpath("/html/body/div[7]/div[2]/div/table/tbody/tr/td/div[1]/div/div[2]/table/tbody/tr/td[5]"));
				w1.click();		
			}
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(viernes)).click();
				Thread.sleep(2000);
				ingresarDatosFecha();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("Entro en el catch Viernes");
				WebElement w1 = driver.findElement(By.xpath("/html/body/div[7]/div[2]/div/table/tbody/tr/td/div[1]/div/div[1]/table/tbody/tr/td[6]"));
				w1.click();		
			}

			try {
				driver.findElement(botonSemSig).click();
			} catch (Exception e) {
				System.out.println("Entro en el catch del boton siguiente");
				WebElement w1 = driver.findElement(By.xpath("//*[@id=\"Calendar\"]/div[1]/div[1]/div/button[2]/span"));
				w1.click();		
			}
		}

	}

	public void ingresarDatosFecha() throws InterruptedException {
        Thread.sleep(2000);
        try {
			Select state = new Select(driver.findElement(By.xpath("//*[@id=\"selProyecto\"]")));
			state.selectByVisibleText("CMF0102806 - Análisis Funcional Varios");
		} catch (Exception e1) {
			System.out.println("Entro en el catch de seleccion del proyecto");
			System.out.println("Es factible que el dia sea feriado");
			
		}
  
        WebElement w1;
		//intenta buscar "Proyecto" dentro del popup de Nueva imputacion de horas
		try {
			w1 = wait.until(ExpectedConditions.presenceOfElementLocated(proyecto));
			w1.click();
			w1.sendKeys(Keys.DOWN);
		} catch (Exception e) {
			WebElement w2 = driver.findElement(By.xpath("/html/body/div[11]/div[2]/div/div[1]/div[1]/div[1]/div/select"));
			w2.click();
			w2.sendKeys(Keys.DOWN);
		}
		
		

		// le doy un click a tarea y selecciono
		try {
			WebElement w2 = wait.until(ExpectedConditions.presenceOfElementLocated(tarea));
			w2.click();
			w2.sendKeys(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER);
		} catch (Exception e) {
			WebElement w2 = driver.findElement(By.xpath("//*[@id=\"selTarea\"]"));
			w2.click();
			w2.sendKeys(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER);
		}
		
		
		
		// Le doy un click a "Tareas Recientes"
        WebElement w3 = wait.until(ExpectedConditions.presenceOfElementLocated(tareasRecientes));
		w3.click();
		w3.sendKeys(Keys.DOWN, Keys.ENTER);
		// Le doy un click a ACEPTAR

		driver.findElement(botonAceptarHoras).click();
        
        }

	@After
	public void salir() {
	//	driver.quit();
	}
	public static void chequeoFechasParaLoop(String fechaOrigen, String fechaDestino) {
		String orig = fechaOrigen.substring(0, 1);
		
		
	}

	
}

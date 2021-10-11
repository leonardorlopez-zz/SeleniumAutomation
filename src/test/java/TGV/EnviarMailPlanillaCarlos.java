package TGV;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.glass.events.KeyEvent;

public class EnviarMailPlanillaCarlos {

	// Este test envia la planilla mensual a Carlos Estebarena 1 vez al mes
	// Prepara el mail listo para enviar con la planilla adjunta

	WebDriver driver;
	WebDriverWait wait;
	ReadExcelFile readFile;
	WriteExcelFile writeFile;

	By mensajeNuevo = By.id("id__23");
	By mensajeNuevo1 = By.id("id__5");
	By mensajeNuevo2 = By.xpath("//*[@id='id__23']");

	By popUp = By.xpath("//*[@data-automationid='splitbuttonprimary']");
	By para = By.xpath(
			"/html/body/div[2]/div/div[2]/div[1]/div/div[3]/div[2]/div/div[3]/div[1]/div/div/div/div[1]/div[1]/div[1]/div[1]/div[1]/div/div/div/div/div[1]/div/div/input");
	By asunto = By.linkText("Agregar un asunto");

	@Before
	public void checkOWA() throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "..\\CargarHoras\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		// Inteligencia Lectura Excel
		readFile = new ReadExcelFile();
		String filepath = "C:\\Users\\lrlopez\\Desktop\\COMAFI\\APLICACIONES\\Cuores-fechas.xlsx";

//	    Month mesPrevio = LocalDate.now().getMonth().minus(1);
//		String mesAnterior = mesPrevio.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

		wait = new WebDriverWait(driver, 10);

		String usuario = readFile.getCellValue(filepath, "Hoja2", 1, 0);
		String password = readFile.getCellValue(filepath, "Hoja2", 1, 1);

		driver.get("https://outlook.live.com/mail/0/inbox?nlp=1");
		driver.manage().window().maximize();

		Thread.sleep(2000);
		// Ingresa el usuario
		try {
			driver.findElement(By.xpath("//*[@id=\"i0116\"]")).sendKeys(usuario);
		} catch (Exception e) {
			System.out.println("Entro en el catch del usuario");

		}

		// Dar click en boton Siguiente
		driver.findElement(By.id("idSIButton9")).click();

		Thread.sleep(3000);
		// Ingresar la password
		WebElement password1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118")));
		password1.sendKeys(password);

		// Apretar el boton de Iniciar sesion
		driver.findElement(By.id("idSIButton9")).click();
		Thread.sleep(2000);
		// Apretar el boton de NO
		driver.findElement(By.id("idBtn_Back")).click();

	}

	@Test
	public void enviarMail() throws InterruptedException, AWTException, IOException {
		Thread.sleep(2000);
		wait = new WebDriverWait(driver, 10);
		try {
			// darle click a "Mensaje Nuevo"

			WebElement mensNuevo = driver.findElement(mensajeNuevo1);
			mensNuevo.click();

		} catch (Exception e1) {
			try {
				System.out.println("Entro en el catch de Mensaje Nuevo");
				WebElement driverw = wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeNuevo));
				driverw.click();
			} catch (Exception e) {
				driver.findElement(mensajeNuevo2).click();
			}
		}

		// Le damos click al pop-up para que aparezca una ventana nueva
		try {
			WebElement popup1 = wait.until(ExpectedConditions.visibilityOfElementLocated(popUp));
			popup1.click();
		} catch (Exception e1) {
			driver.findElement(popUp).click();
		}
		// Completo el Subject y le concateno el mes actual
		Month mes = LocalDate.now().getMonth();
		String nombreMes = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
		String nombreMesMayuscula = nombreMes.toUpperCase();
		String texto = "Adjunto la planilla correspondiente al mes de ";

		// Ingresamos el mail destino y completamos los datos del mail
		try {
			wait = new WebDriverWait(driver, 10);
			WebElement userPara = wait.until(ExpectedConditions.presenceOfElementLocated(para));
			userPara.sendKeys("carlos.estebarena@comafi.com.ar ", Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB,
					"Planilla de Proyectos - " + nombreMesMayuscula, Keys.TAB, texto + nombreMesMayuscula);
		} catch (Exception e) {
			WebElement userPara = driver.findElement(By.xpath(
					"//*[@id=\"ReadingPaneContainerId\"]/div/div/div/div[1]/div[1]/div[1]/div[1]/div[1]/div/div/div/div/div[1]/div/div/input"));
			userPara.sendKeys("carlos.estebarena@comafi.com.ar ", Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB,
					"Planilla de Proyectos - " + nombreMesMayuscula, Keys.TAB, texto + nombreMesMayuscula);
		}

		// Actualizamos el excel con el mes

		writeFile = new WriteExcelFile();

		String nombreMesCorregido = pasarMayusculas(nombreMesMayuscula);

		String filepath = "C:\\Users\\lrlopez\\Desktop\\COMAFI\\APLICACIONES\\Asignacion_Tareas_en_Cliente.xlsx";
		writeFile.writeCellValue(filepath, nombreMesCorregido, 0, 1);

		// Subimos el excel
		// le doy un click a la barra de busqueda para refrescar
		WebElement w1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"/html/body/div[2]/div/div[2]/div[1]/div/div/div/div[3]/div[1]/div/span/div/div/div/div/div[1]")));
		w1.click();

		// Le doy un click a Adjuntar y le adjunto el excel
		try {
			List<WebElement> fonts = driver.findElements(By.tagName("span"));

			for(WebElement w5 : fonts) {
				System.out.println(w5.getText());
				String aux = "Adjuntar";
				if(w5.getText().equalsIgnoreCase(aux)) {
					w5.sendKeys(filepath);
				}
			}
		} catch (Exception e) {
			WebElement w6 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"/html/body/div[2]/div/div[2]/div[1]/div/div/div/div[3]/div[1]/div/span/div/div/div/div/div[1]")));
			w6.click();
			
			
		}

	}

	public static String pasarMayusculas(String aux) {

		String primerLetra = aux.substring(0, 1).toUpperCase();
		String restoPalabra = aux.substring(1).toLowerCase();
		return primerLetra + restoPalabra;
	}

}

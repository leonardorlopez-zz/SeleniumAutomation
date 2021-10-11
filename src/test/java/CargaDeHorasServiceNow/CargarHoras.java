package CargaDeHorasServiceNow;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class CargarHoras extends Thread {

	private WebDriver driver;
	private ReadExcelFile readFile;

	String[] cuores = new String[22];
	String[] fechas = new String[22];

	@Before
	public void setUp() throws IOException {
		readFile = new ReadExcelFile();
		String filepath = "C:\\Users\\lrlopez\\Desktop\\COMAFI\\APLICACIONES\\Cuores-fechas.xlsx";
		int aux = 1;
		for (int i = 0; i < 21; i++) {
			String cuore;
			String fecha;

			try {
				cuore = readFile.getCellValue(filepath, "Hoja1", aux, 0);
				fecha = readFile.getCellValue(filepath, "Hoja1", aux, 1);
			} catch (Exception e) {
				System.out.println("Salimos");
				break;
			}

			aux++;

			cuores[i] = cuore;
			fechas[i] = fecha;

		}

		DesiredCapabilities caps = new DesiredCapabilities();

		System.setProperty("webdriver.chrome.driver", "..\\CargarHoras\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver(); // abre el chrome driver, creamos nuestro objeto.
		driver.get("https://comafi.atlassian.net/secure/RapidBoard.jspa?rapidView=76&view=planning&issueLimit=100"); // vamos
		driver.manage().window().maximize(); // Maximiza la ventana

	}

	@Test
	public void pruebaUno() throws InterruptedException, AWTException {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		// Proceso de Login
		driver.findElement(By.xpath("//*[@id=\"microsoft-auth-button\"]/span[2]/span")).click();
		                             
		try {
			driver.findElement(By.id("i0116")).sendKeys("leonardoroberto.lopez@comafi.com.ar");
		} catch (Exception e1) {
			WebElement usuario = wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116")));
			usuario.sendKeys("leonardoroberto.lopez@comafi.com.ar");

		}

		driver.findElement(By.id("idSIButton9")).click();
		 String passwordIngresada = JOptionPane.showInputDialog(null, "Ingrese la password");

		
		driver.findElement(By.id("i0118")).sendKeys(passwordIngresada);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("idSIButton9")).click();

		if (driver.findElement(By.id("idBtn_Back")) != null) {
			driver.findElement(By.id("idBtn_Back")).click();
		}

		// recorro todos los cuores con un for
		for (int i = 0; i < cuores.length; i++) {

			// caso de que lea en el excel un CQ
			if (cuores[i].substring(0, 2).equals("CQ") || cuores[i].substring(0, 5).equals("CUORE")
					|| cuores[i].substring(0, 5).equals("cuore")) {

				Thread.sleep(2000);

				try {

					driver.findElement(By.cssSelector(
							"#jira-frontend > div.sc-fBuWsC.iYOvbe > div > div.sc-jhAzac.ULlsU > div > header > div > div > div > div > div > div > div > div:nth-child(1) > div > input"))
							.sendKeys(cuores[i], Keys.ENTER);

				} catch (Exception e1) {
					System.out.println("Entro en el catch del search");
					try {

						WebElement search = wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.cssSelector("data-test-id='search-dialog-input'")));
						search.sendKeys(cuores[i], Keys.ENTER);

					} catch (Exception e) {
						driver.findElement(By.xpath("//*[@placeholder='Search']")).sendKeys(cuores[i], Keys.ENTER);

					}

				}

				// desplegable para ver Time Tracking (Six More)
				try {
					WebElement sixMore = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
							"/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div/button")));
					sixMore.click();

				} catch (Exception e) {
					System.out.println("Entro en el catch de sixMore");
					WebElement sixMore = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
							"data-test-id=issue.views.issue-base.context.context-items.expander.show-more-button.container")));
					sixMore.click();

				}
				// Click en Time Tracking
				try {

					if (cuores[i].substring(0, 2).equals("CQ") || cuores[i].substring(0, 2).equals("cq")) {
						WebElement timeTr = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
								"/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div/div/div/div/div/div")));
						timeTr.click();
					} else {
						WebElement timeTr = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
								"/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/div[8]/div[2]/div/div/div/div[1]")));
						timeTr.click();
						
						//Cuore - Le hace un click al Time Tracking
						WebElement timeTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[1]/div[5]/div/div/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div/div/div/small/span/span")));
                        timeTab.click();						

					}

				} catch (Exception e) {
					try {
						try {
							System.out.println("Entro en el catch de Time Tracking");
							WebElement timeTr = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
									"/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/div[13]/div[2]/div/div/div[2]/div/div/div/div/div/div/div/div/div[1]")));
							timeTr.click();
						} catch (Exception e1) {
							try {
								driver.findElement(By.xpath(
										"/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div/div/div/div/div"))
										.click();
							} catch (Exception e2) {
								WebElement timeTr = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
										"/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/div[8]/div[2]/div/div/div/div[2]")));
								timeTr.click();
							}
						}

					} catch (Exception e1) {
						System.out.println("Entro en el segundo catch de time tracking");
						driver.findElement(By.xpath("//*[contains(text(), 'No time logged')]")).click();
					}

				}

				// Enviar 1d en Time Spent
				try {

					WebElement timeSpent = wait.until(ExpectedConditions
							.presenceOfElementLocated(By.xpath("//input[@placeholder='2w 4d 6h 45m']")));
					timeSpent.sendKeys("1d");
					timeSpent.sendKeys(Keys.TAB, Keys.TAB, fechas[i], Keys.ENTER, Keys.TAB, Keys.TAB, Keys.TAB,
							Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.ENTER);

				} catch (Exception e) {
					try {
						System.out.println("Entro en el catch de carga de dias y horas2");

						WebElement timeSpent = driver.findElement(By.cssSelector(
								"#jira > div.atlaskit-portal-container > div:nth-child(5) > div > div:nth-child(3) > div.css-krjp51.e1pwmxs01 > div > div > div > div > div > div > div > div:nth-child(1) > div > div > div > div > div > div > input"));
						timeSpent.sendKeys("1d");
						timeSpent.sendKeys(Keys.TAB, Keys.TAB, fechas[i], Keys.ENTER, Keys.TAB, Keys.TAB, Keys.TAB,
								Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.ENTER);
					} catch (Exception e1) {
						System.out.println("Entro en el segundo catch de carga de dias y horas3");
						WebElement timeSpent = driver.findElement(By.xpath(
								"/html/body/div[1]/div[1]/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div/div/div/div/div/div/div/div"));
						timeSpent.click();
					}

				}

			} // caso de que lea en el excel un cuore
//			else {
//				// search donde poner los cuore
//
//				WebElement search = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
//						"//*[@id=\"helpPanelContainer\"]/div/div[1]/div[1]/header/div/div/div/div/div/div/div/div[1]/div/input")));
//
//				try {
//					search.click();
//					search.sendKeys(cuores[i]);
//					search.sendKeys(Keys.ENTER);
//				} catch (Exception e2) {
//					System.out.println("Entro en el catch del search cuore");
//					Robot r = new Robot();
//
//					r.keyPress(KeyEvent.VK_ENTER);
//					r.keyRelease(KeyEvent.VK_ENTER);
//				}
//
//				// time Tracking (dentro ya del cuore)
//
//				try {
//					WebElement timetr1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
//							"/html/body/div[1]/div/div/div/div[3]/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/div[8]/div[2]/div/div/div/div[2]")));
//					timetr1.click();
//
//				} catch (Exception e1) {
//					System.out.println("Entro en el catch de Time Tracking2");
//					WebElement timetr = wait.until(
//							ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Time tracking']")));
//					timetr.click();
//				}
//
//				// Entra en TimeSpent 2w 4d 6h 45m
//
//				try {
//					WebElement timeSpent1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
//							"/html/body/div[1]/div/div/div/div[3]/div[1]/div/div[2]/div[2]/div/div[1]/div[5]/div/div/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div/div/div[2]/small/span/span")));
//					timeSpent1.click();
//
//				} catch (Exception e) {
//					System.out.println("Entro en el catch de ... days logged" + "");
//
//					WebElement timeSpent = wait
//							.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("logged")));
//					timeSpent.click();
//
//				}
//
//				WebElement timeSpent;
//				try {
//					timeSpent = wait.until(ExpectedConditions.elementToBeClickable(
//							By.xpath("//input[contains(@class, 'InputElement-sc-1o6bj35-0 bfCuIo')]")));
//					timeSpent.sendKeys("1d");
//					timeSpent.sendKeys(Keys.TAB, Keys.TAB, fechas[i], Keys.ENTER, Keys.TAB, Keys.TAB, Keys.TAB,
//							Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.ENTER);
//
//				} catch (Exception e) {
//					System.out.println("Entro al catch de Date Started");
//					timeSpent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
//							"//*[@id=\"jira\"]/div[13]/div[11]/div/div[3]/div[2]/div/div/div/div/div/div/div[2]/div[1]/div/div/div/div/div/div/input")));
//					timeSpent.sendKeys("1d");
//					timeSpent.sendKeys(Keys.TAB, Keys.TAB, fechas[i], Keys.ENTER, Keys.TAB, Keys.TAB, Keys.TAB,
//							Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.ENTER);
//
//				}
//
//			} // fin del else (Cuores)
		} // fin del FOR

	}// fin del metodo test

	@After
	public void verResultados() {
		// driver.quit();
	}

}

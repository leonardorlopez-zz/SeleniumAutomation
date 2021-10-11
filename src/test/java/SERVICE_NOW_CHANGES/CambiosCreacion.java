package SERVICE_NOW_CHANGES;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import org.apache.poi.sl.usermodel.Placeholder;
import org.jdesktop.swingx.JXDatePicker;
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

import com.sun.glass.events.KeyEvent;

import TGV.ReadExcelFile;
import javafx.scene.shape.Line;

public class CambiosCreacion {
	// Atributos Globales
	WebDriver driver;
	WebDriverWait wait;

	// Locators
	By userName = By.name("loginfmt");
	By botonUser = By.id("idSIButton9");
	By passwordLocator = By.name("passwd");
	By iniciarSesionButton = By.id("idSIButton9");
	By botonNO = By.id("idBtn_Back");
	By crearNuevo = By.xpath(
			"/html/body/div[5]/div/div/nav/div/div[3]/div/div/concourse-application-tree/ul/li[6]/ul/li[1]/div/div/a/div/div");
	By linkNormal = By.cssSelector("body > form > div:nth-child(15) > a");
	By categoria = By.id("change_request.category");
	
	By ambiente = By.id("change_request.u_environment");
	By elemConf = By.id("sys_display.change_request.cmdb_ci");
	String filepath = "C:\\Users\\lrlopez\\Desktop\\COMAFI\\APLICACIONES\\Cuores-fechas.xlsx";
	ReadExcelFile readFile;
	String sheetName = "Hoja2";
	By tituloTipoCambio = By.className("wizard-row-indent");
    By botonEnviar = By.id("sysverb_insert_bottom");
    //Almacena el nro de cambio
    static String nroCambio="";
	By nroDeCambioLocator = By.id("sys_readonly.change_request.number");
    By nroDeReq = By.id("sys_display.change_request.u_ticket_de_cambio_qa");
	//Lupa de Search
    By searchLupa = By.id("sysparm_search");
    //Busca la solapa Tareas de Cambio
    By tareasDeCambioLocator = By.xpath("/html/body/div[2]/div[1]/span[5]/span/span[2]");
    By botonNuevaTarea = By.id("sysverb_new");
       
    //Le agrega una Descripcion corta a la tarea
    By descripcionCortaTarea = By.id("change_task.short_description");
    //Descripcion de la tarea
    By descripcionLargaTarea = By.id("change_task.description");
    //Orden de la tarea
    By ordenDeTarea = By.id("element.change_task.order");
    //Boton Enviar Tarea
    By botonEnviarTarea = By.id("sysverb_insert");
    //Boton Solicitar Aprobacion
    By botonSolicitarAprobacion = By.id("state_model_request_assess_approval");
    
	@Before
	public void setUp() throws IOException {

		// Apertura Navegador y acceso
		System.setProperty("webdriver.chrome.driver", "..\\CargarHoras\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://puccomafi.service-now.com/");

		driver.manage().window().maximize();

		// Inicializacion Objetos
		wait = new WebDriverWait(driver, 10);
		ReadExcelFile reader = new ReadExcelFile();

		// Lectura Credenciales
		String usuario = reader.getCellValue(filepath, sheetName, 1, 0);

		// Login
		WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(userName));
		user.sendKeys(usuario);

		// Oprime SI
		WebElement botonSI = wait.until(ExpectedConditions.visibilityOfElementLocated(botonUser));
		botonSI.click();

		// Le pido al usuario que ingrese la password y la ingreso
		readFile = new ReadExcelFile();
		String password = readFile.getCellValue(filepath, sheetName, 1, 1);
		WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
		pass.sendKeys(password);

		// Oprimo el boton de Iniciar Sesion
		WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(iniciarSesionButton));
		button.click();

		// Oprimo el boton de NO
		WebElement botonNo = wait.until(ExpectedConditions.visibilityOfElementLocated(botonNO));
		botonNo.click();

	}

	@Test
	public void creacionCambios() throws InterruptedException {
		// Hacemos click a Crear Nuevo
		try {
			wait = new WebDriverWait(driver, 10);
			WebElement crearNuevo1 = wait.until(ExpectedConditions.visibilityOfElementLocated(crearNuevo));
			crearNuevo1.click();
		} catch (Exception e3) {
			System.out.println("Entro en el catch de Crear Nuevo");
		}

		// Click en normal
		driver.get(
				"https://puccomafi.service-now.com/change_request.do?WIZARD:action=follow&sys_action=&sys_id=-1&sys_target=change_request&sysparm_query=type%3dnormal&sysparm_target=&wiz_action=sysverb_new&wiz_collection=&wiz_collectionID=&wiz_collection_key=&wiz_collection_related_field=&wiz_referring_url=&wiz_view=");

		SwingCreacionCambios.display1();

		// Lo que hacemos aca es entrar a un loop para dar tiempo para completar el
		// JPanel  una vez que se apreta el boton de ACEPTAR variable pasa a ser true y sale de
		// este loop para continuar con el programa
		while (SwingCreacionCambios.variable == false) {
			Thread.sleep(2000);
		}
      
		// Indicamos la categoria
		try {
			WebElement cat1 = wait.until(ExpectedConditions.visibilityOfElementLocated(categoria));
			cat1.sendKeys("Implementaciones");
			
		
		} catch (Exception e) {
			try {
				System.out.println("Entro al catch de Categoria");
				WebElement cat1 = wait.until(ExpectedConditions.elementToBeClickable(categoria));
				cat1.sendKeys("Implementaciones");
			} catch (Exception e1) {
				System.out.println("Entro en el 2do catch");
				Select categoria1 = new Select(driver.findElement(categoria));
				categoria1.selectByVisibleText("Implementaciones");
			}
		}

  		// Subcategoria
		WebElement subcategoria;
		try {
			try {
				Select subCategoria = new Select(driver.findElement(By.id("change_request.u_subcategory_l2")));
				String subcategoriaCombo = SwingCreacionCambios.comboSubCat.getSelectedItem().toString();
				subCategoria.selectByValue(subcategoriaCombo);
			} catch (Exception e) {
				subcategoria = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.u_subcategory_l2")));
				String subcategoriaCombo = SwingCreacionCambios.comboSubCat.getSelectedItem().toString();
				subcategoria.sendKeys(subcategoriaCombo + Keys.ENTER + Keys.TAB);
			}
		} catch (Exception e) {
			System.out.println("Entro en el catch de Subcategoria");

		}

		// Hacemos un click en el body para que tome la Subcategoria
		driver.findElement(By.id("element.change_request.category")).click();

		// Servicio de Negocio
		WebElement servicioDeNegocio = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.id("sys_display.change_request.business_service")));
		String servicioDeNegocioCombo = SwingCreacionCambios.comboServNeg.getSelectedItem().toString();
		servicioDeNegocio.sendKeys(servicioDeNegocioCombo);

		// Ambiente
		WebElement ambiente = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.u_environment")));
		String ambienteCombo = SwingCreacionCambios.ambientes.getSelectedItem().toString();
		ambiente.sendKeys(ambienteCombo);

		// Elemento de Configuracion
		WebElement elementoDeConf = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("sys_display.change_request.cmdb_ci")));
		String elementoConfCombo = SwingCreacionCambios.elementoDeConfiguracion.getSelectedItem().toString();
		elementoDeConf.sendKeys(elementoConfCombo);
		// Le hace un click a la lupa
		WebElement lupa = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("lookup.change_request.cmdb_ci")));
		lupa.click();

		// Reconoce la ventana actual
		String MainWindow = driver.getWindowHandle();

		// To handle all new opened window.
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();

		while (i1.hasNext()) {
			String ChildWindow = i1.next();

			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				// Switching to Child window
				driver.switchTo().window(ChildWindow);
				driver.findElement(By.className("glide_ref_item_link")).click();
			}
		}
		// Switching to Parent window i.e Main Window.
		driver.switchTo().window(MainWindow);

		// Tipo de Impacto
		try {
			WebElement tipoDeImp = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.u_impact_type")));
			String tipoDeImpactoCombo = SwingCreacionCambios.tipoDeImpacto.getSelectedItem().toString();
			tipoDeImp.sendKeys(tipoDeImpactoCombo);
		} catch (Exception e1) {
			WebElement tipoDeImp = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("change_request.u_impact_type")));
			String tipoDeImpactoCombo = SwingCreacionCambios.tipoDeImpacto.getSelectedItem().toString();
			tipoDeImp.sendKeys(tipoDeImpactoCombo);

		}

		// Descripcion Corta
		WebElement descripcionCort = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.short_description")));
		String descrCortaCombo = SwingCreacionCambios.descripcionCorta.getText().toString();
		descripcionCort.sendKeys(descrCortaCombo);

		// Descripcion Larga
		WebElement descripcionCoLarga = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.description")));
		String descrLargaCombo = SwingCreacionCambios.descripcionLarga.getText().toString();
		descripcionCoLarga.sendKeys(descrLargaCombo);

		// Justificacion
		WebElement justif = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.justification")));
		String justCombo = SwingCreacionCambios.justificacion.getText().toString();
		justif.sendKeys(justCombo);

		// Plan De Implementacion
		WebElement planImplementacion = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.implementation_plan")));
		planImplementacion.sendKeys("N/A");

		// Analisis de Riesgo e Impacto
		WebElement riesgoImpacto = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.risk_impact_analysis")));
		riesgoImpacto.sendKeys("N/A");

		// Plan de Retorno
		WebElement planRetorno = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.backout_plan")));
		String planRetornoCombo = SwingCreacionCambios.planDeRetorno.getText().toString();
		planRetorno.sendKeys(planRetornoCombo);

		// Plan de CargaDeHorasServiceNow
		WebElement planPruebas = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.test_plan")));
		planPruebas.sendKeys("N/A");

		// Requerimiento
		if (SwingCreacionCambios.ambientes.equals("Producción")) {
			//Si no fue cargado un cambio de QA permite el ingreso
			WebElement requer = wait
					.until(ExpectedConditions.presenceOfElementLocated(nroDeReq));
			String requerimientoCombo = SwingCreacionCambios.requerimiento.getText().toString();
			requer.sendKeys(requerimientoCombo);
		}

		// Clickear la solapa "Programa"
		WebElement programa = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("/html/body/div[2]/form/div[1]/span[2]/span[1]/span[2]")));
		programa.click();

		// FECHA DE INICIO PLANIFICADA
		String auxFechaInicioPlan = "2021-02-" + SwingCreacionCambios.auxiliar + " "+SwingCreacionCambios.auxiliarHoraDeInicioPlanificada + ":00:00";
		WebElement fechaInicioPlan = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.start_date")));
		fechaInicioPlan.sendKeys(auxFechaInicioPlan);

		// FECHA LIMITE PLANIFICADA - le pone 5 horas despues de la Fecha de Inicio
		// Planificada

		int horaLimite = Integer.parseInt(SwingCreacionCambios.auxiliarHoraDeInicioPlanificada) + 5;
		String horaLimiteSumada = String.valueOf(horaLimite);
		if(horaLimiteSumada.length()==1) {
			horaLimiteSumada = "0"+horaLimiteSumada;
		}
		
		String auxFechaLimitePlan = "2021-02-" + SwingCreacionCambios.auxiliar +  " " +horaLimiteSumada+ ":00:00";
		WebElement fechaLimitePlan = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.end_date")));
		fechaLimitePlan.sendKeys(auxFechaLimitePlan);
		
		//Ingresamos la Subcategoria aqui por que se borra
		// Subcategoria
		WebElement subcategoria1;
		try {
				subcategoria1 = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.id("change_request.u_subcategory_l2")));
				String subcategoriaCombo = SwingCreacionCambios.comboSubCat.getSelectedItem().toString();
				subcategoria1.sendKeys(subcategoriaCombo + Keys.ENTER + Keys.TAB);
			
		} catch (Exception e) {
			System.out.println("Entro en el catch de Subcategoria");

		}
		
		//Almacena el Nro de Cambio 
		WebElement NroDeCambio = wait.until(ExpectedConditions.presenceOfElementLocated(nroDeCambioLocator));
		nroCambio = NroDeCambio.getText().toString();

		
		//Oprimir el Boton Enviar
		WebElement botonEnviar1 = wait.until(ExpectedConditions.presenceOfElementLocated(botonEnviar));
		botonEnviar1.click();
        
		
         //Le envia el nro de cambio al search de Busqueda
  		WebElement w1 = wait.until(ExpectedConditions.presenceOfElementLocated(searchLupa));
  		w1.sendKeys(nroCambio,  Keys.ENTER);

  		//Le hace click a la solapa Tareas de cambio
  		WebElement tareasDeCambio = wait.until(ExpectedConditions.presenceOfElementLocated(tareasDeCambioLocator));
  		tareasDeCambio.click();
  		
  		//Oprimimos el boton "Nuevo" para generar una nueva tarea.
		WebElement botonNuevaTar = wait.until(ExpectedConditions.presenceOfElementLocated(botonNuevaTarea));
		botonNuevaTar.click();
		
		//Le ingresa la descripcion Corta a la tarea
		WebElement descrCortTarea = wait.until(ExpectedConditions.presenceOfElementLocated(descripcionCortaTarea));
		descrCortTarea.sendKeys(descrCortaCombo);
			
		//Le ingresa la descripcion Larga o Descripcion a secas
		WebElement descrLargTarea = wait.until(ExpectedConditions.presenceOfElementLocated(descripcionLargaTarea));
		descrLargTarea.sendKeys(descrLargaCombo);

		//chequea cuantas tareas se quieren hacer
		String cantOrdenTareas = SwingCreacionCambios.cantidadTareas.getText();
		if(cantOrdenTareas=="1") {
		//Ingresamos el orden de la tarea
		WebElement ordTarea = wait.until(ExpectedConditions.presenceOfElementLocated(ordenDeTarea));
		ordTarea.sendKeys("1");
		}
		
		//Oprime el Boton de enviar
		WebElement botEnvTarea = wait.until(ExpectedConditions.presenceOfElementLocated(botonEnviarTarea));
		botEnvTarea.click();
		
		//Solicitar Aprobacion
		WebElement botSoliApro = wait.until(ExpectedConditions.presenceOfElementLocated(botonSolicitarAprobacion));
		botSoliApro.click();
		
		System.out.println("llegamos hasta aqui");

	}

}

package SERVICE_NOW_CHANGES;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SwingCreacionCambios extends JFrame {

	static JComboBox comboBox;
	static JComboBox comboSubCat;
	static JComboBox comboServNeg;
	static JComboBox ambientes;
	static JComboBox elementoDeConfiguracion;
	static JComboBox tipoDeImpacto;
	static JTextField descripcionCorta;
	static JFrame frame;
	static boolean variable;
	static JTextField descripcionLarga;
	static JTextField justificacion;
	static JTextField planDeRetorno;
	static JTextField requerimiento;
	static JTextField diaFechaInicioPlanificada;
    static String auxiliar;
    static JTextField horaInicioPlanificada;
    static String auxiliarHoraDeInicioPlanificada;
    static JTextField cantidadTareas;
    
    
    public SwingCreacionCambios() {
    	
    }
    
	public static void display1() {
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(950, 350));
		frame.setLayout(new GridLayout(2, 1));
		JPanel mainPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		frame.add(mainPanel);
		frame.add(buttonsPanel, BorderLayout.SOUTH);

		String[] options = { "Implementaciones" };

		// Categoria - Por ahora lo dejo afuera porque siempre ingresamos lo mismo.
//	    comboBox = new JComboBox(options);
//	    comboBox.setRenderer(new MyComboBoxRenderer("CATEGORIA"));
//	    comboBox.setSelectedIndex(-1); 
//	    mainPanel.add(comboBox);

		// Subcategoria
		String[] options1 = { "CONTROLM", "HARVEST" };
		comboSubCat = new JComboBox(options1);
		comboSubCat.setRenderer(new MyComboBoxRenderer("SUBCATEGORIA"));
		mainPanel.add(comboSubCat);

		// Servicio de Negocio
		String[] options2 = { "SIDELS-LEASI", "SIDELS-LEASI - QA" };
		comboServNeg = new JComboBox(options2);
		comboServNeg.setRenderer(new MyComboBoxRenderer("SERVICIO DE NEGOCIO"));
		comboServNeg.setSelectedIndex(-1);
		comboServNeg.addActionListener(e -> {
			if (comboServNeg.getSelectedItem().toString().equals("SIDELS-LEASI - QA")) {
				ambientes.setSelectedItem("QA"); // para que se seleccione el valor
				ambientes.setVisible(false); // para que no se muestre

				elementoDeConfiguracion.setSelectedItem("QAPRG03"); // se sobreentiende que sera ese equipo
				elementoDeConfiguracion.setVisible(false);

			} else {
				ambientes.setVisible(true); // se muestra visible
				elementoDeConfiguracion.setVisible(true);
			}
		});

		mainPanel.add(comboServNeg);

		// Requerimiento
		requerimiento = new JTextField("Ingrese nro de Requerimiento", 20);
		requerimiento.setVisible(true);
		mainPanel.add(requerimiento);

		// Ambientes
		String[] options3 = { "Contingencia", "Producción", "QA" };
		ambientes = new JComboBox(options3);
		ambientes.setRenderer(new MyComboBoxRenderer("AMBIENTES"));
		ambientes.setSelectedIndex(-1);
		ambientes.addActionListener(e -> {
			if (!ambientes.getSelectedItem().toString().equals("Producción")) {
				requerimiento.setVisible(false);
			} else {
				requerimiento.setVisible(true);
			}
		});
		mainPanel.add(ambientes);

		// Elemento de Configuracion
		String[] options4 = { "QAPRG03", "BCPRG05" };
		elementoDeConfiguracion = new JComboBox(options4);
		elementoDeConfiguracion.setRenderer(new MyComboBoxRenderer("ELEMENTO DE CONFIGURACION"));
		elementoDeConfiguracion.setSelectedIndex(-1);
		mainPanel.add(elementoDeConfiguracion);

		// Tipo de Impacto
		String[] options5 = { "BATCH con impacto en Control-M", "BATCH sin impacto en Control-M", "BATCH Y ONLINE",
				"ONLINE" };
		tipoDeImpacto = new JComboBox(options5);
		tipoDeImpacto.setRenderer(new MyComboBoxRenderer("TIPO DE IMPACTO"));
		tipoDeImpacto.setSelectedIndex(-1);
		mainPanel.add(tipoDeImpacto);

		// Descripcion Corta
		descripcionCorta = new JTextField("Ingrese la descripcion Corta aqui", 80);
		mainPanel.add(descripcionCorta);

		// Descripcion Larga
		descripcionLarga = new JTextField("Ingrese la descripcion Larga aqui", 80);
		mainPanel.add(descripcionLarga);

		// Justificacion
		justificacion = new JTextField("Ingrese la justificacion aqui", 80);
		mainPanel.add(justificacion);

		// Plan de Retorno
		planDeRetorno = new JTextField("Plan de Retorno", 80);
		mainPanel.add(planDeRetorno);

		// Requerimiento
		mainPanel.add(requerimiento);

		// Salto de Linea
		JLabel saltoDeLinea = new JLabel();
		saltoDeLinea.setText("");
		
		// Indicar dia en Fecha Inicio Planificada
		JLabel auxFechaInicioPlanificada = new JLabel("Fecha de Inicio Planificada - DIA");
		auxFechaInicioPlanificada.setText("Fecha de Inicio Planificada - DIA");
		mainPanel.add(auxFechaInicioPlanificada);
		// Se agrega el dia
		diaFechaInicioPlanificada = new JTextField(2);
    	diaFechaInicioPlanificada.setDocument(new JTextFieldLimit(2));
		mainPanel.add(diaFechaInicioPlanificada);

		// Indicar hora de Inicio Planificada
		JLabel auxHoraDeInicioPlanificada = new JLabel("Ingrese solo la Hora de Inicio Planificada");
		auxHoraDeInicioPlanificada.setText("Ingrese solo la Hora de Inicio Planificada");
		mainPanel.add(auxHoraDeInicioPlanificada);
		
    	//Hora de Inicio Planificada
    	horaInicioPlanificada = new JTextField(2);
    	horaInicioPlanificada.setDocument(new JTextFieldLimit(2));
    	mainPanel.add(horaInicioPlanificada);
    	
    	// Salto de Linea
		JLabel saltoDeLinea1 = new JLabel();
		saltoDeLinea1.setText("");

    	
    	// Indicar cuantas tareas se haran (para determinar el orden de cada tarea)
		JLabel orden = new JLabel("Cantidad de Tareas a realizar");
		orden.setText("Cantidad de Tareas a realizar (1, 2 o 3");
		mainPanel.add(orden);
    			
    	//Cuantas tareas se haran - Caso 1 tarea
    	cantidadTareas = new JTextField(1);
    	cantidadTareas.setDocument(new JTextFieldLimit(1));
    	mainPanel.add(cantidadTareas);
    	
    	
    	JButton continueButton = new JButton("CONTINUAR");
		continueButton.addActionListener(e -> {
			frame.setVisible(false);
			variable = true;
            auxiliar = diaFechaInicioPlanificada.getText();
          //si el usuario agrego un digito para el dia le agregamos un cero a la izquierda
			if(auxiliar.length()==1) {
				auxiliar = "0"+ auxiliar;
			}
			auxiliarHoraDeInicioPlanificada = horaInicioPlanificada.getText();
		    //si el usuario agrego 1 digito para la hora le agregamos un cero a la izquierda
			if(auxiliarHoraDeInicioPlanificada.length()==1) {
		    	auxiliarHoraDeInicioPlanificada = "0"+auxiliarHoraDeInicioPlanificada;
		    }
		});
		buttonsPanel.add(continueButton);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusableWindowState(true);
		frame.setState(NORMAL);
		frame.setTitle("Creacion de Cambios");

	}

	public static void clickLinkByHref(WebDriver driver) {
		List<WebElement> e1 = driver.findElements(By.tagName("href"));

		Iterator<WebElement> it = e1.iterator();
		System.out.println("Entro en clickLinkByHref");
		while (it.hasNext()) {
			WebElement e2 = it.next();
//	        if(e2.getAttribute("href").contains("wizard_view.do?sys_action=sysverb_wizard_ans")) {
			if (e2.getAttribute("href").matches("wizard_view.do?sys_action=sysverb_wizard_ans")) {
				e2.click();
				System.out.println("Lo encontre");
				break;
			}
		}
	}
	
		
}

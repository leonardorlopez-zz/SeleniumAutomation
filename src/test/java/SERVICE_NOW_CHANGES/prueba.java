package SERVICE_NOW_CHANGES;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

public class prueba {

	public static void main(String[] args) {
		display1();

	}

	public static void display1() {
		JTextField requerimiento;
		
		JFrame frame = new JFrame();
	    frame.setPreferredSize(new Dimension(950, 350));
	    frame.setLayout(new GridLayout(2, 1));
	    JPanel mainPanel = new JPanel();
	    JPanel buttonsPanel = new JPanel();
	    frame.add(mainPanel);
	    frame.add(buttonsPanel, BorderLayout.SOUTH);

	    //Requerimiento
	    requerimiento = new JTextField("Ingrese nro de Requerimiento", 20);
	    requerimiento.setVisible(true);
	    mainPanel.add(requerimiento);
	  
	    //Ambientes
	    String[] options3 = { "Contingencia", "Producción", "QA"};
	    JComboBox ambientes = new JComboBox(options3);
	    ambientes.setRenderer(new MyComboBoxRenderer("AMBIENTES"));
	    ambientes.setSelectedIndex(-1); 
	    ambientes.addActionListener(e->{
	       if(!ambientes.getSelectedItem().toString().equals("Producción")) {
	    	requerimiento.setVisible(false);
	       }else {
	    	   requerimiento.setVisible(true);
	       }
	    });
	    mainPanel.add(ambientes);
	    
	   
	  //Requerimiento
	    mainPanel.add(requerimiento); 
	    
	    JButton continueButton = new JButton("CONTINUAR");
	    continueButton.addActionListener(e-> {
	    	frame.setVisible(false);
	    
	    });
	    buttonsPanel.add(continueButton);

	    frame.pack();
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    
	
	}	
	
	public static void fechas() {
		//2021-02-05 10:57:27
		JCalendar calendario = new JCalendar();
		
		
		
		
		
	}
}

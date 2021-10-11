
package PruebasParaTesting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXDatePicker;
import javafx.scene.control.Control.*;

public class Practica {

	public static void main(String[] args) {

//		Calendar c1 = GregorianCalendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//		System.out.println(c1.get(Calendar.DATE));
//		System.out.println(c1.get(Calendar.DAY_OF_WEEK));
//		c1.set(Calendar.DAY_OF_WEEK, 1); // incrementa dias
//		System.out.println(c1.get(Calendar.DAY_OF_WEEK));
//		System.out.println(sdf.format(c1.getTime())); // muestra en formato deseado

		int[] vectorDias = new int[22];
		
		// Obtenemos el dia del mes
		Calendar c2 = GregorianCalendar.getInstance();
		int diaDelMes = c2.get(Calendar.DATE);
		// Obtenemos el dia en la semana 
		int diaDeLaSemana = c2.get(Calendar.DAY_OF_WEEK);
		    int aux=1;
		while(diaDelMes!=1) {
			diaDelMes = diaDelMes - 1;
			if(diaDeLaSemana==0) {
				diaDeLaSemana=7;
			}
			diaDeLaSemana = c2.get(Calendar.DAY_OF_WEEK)-aux;
			aux++;
			System.out.println(diaDelMes);
			System.out.println(diaDeLaSemana);

		}
System.out.println();		
		
		
		for (int i = 0; i < vectorDias.length; i++) {
			
			if (diaDeLaSemana == 7 || diaDeLaSemana == 1) {
				
			} else {
				//no es fin de semana
				vectorDias[i] = diaDelMes;
			}
            
			diaDelMes = diaDelMes - 1;
			System.out.println(diaDelMes);
		}

		for (int j = 0; j < vectorDias.length; j++) {
			System.out.println(vectorDias[j]);
		}

	}
}

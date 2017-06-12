/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Proceso;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Equipo
 */
public class graficoGasto extends javax.swing.JFrame{
    private Principal Vp;
    private Proceso pr;
    private int gasto[] = new int[12];
    
    JFreeChart grafica;
    DefaultCategoryDataset datos = new DefaultCategoryDataset();

    public graficoGasto(Principal vp, Proceso pr) {
        this.pr = pr;
        edu();
        this.Vp = vp;
        initComponent();
       
    }

    private void initComponent() {
        this.addWindowListener(new WindowAdapter() {
            public void WindowClosing(WindowEvent e){
                Vp.setVisible(true);
                graficoGasto.this.dispose();
            }
        });
        
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        final String x = "gasto";
        
        
        data.addValue(gasto[0], x, "enero");
        data.addValue(gasto[1], x, "febrero");
        data.addValue(gasto[2], x, "marzo");
        data.addValue(gasto[3], x, "abril");
        data.addValue(gasto[4], x, "mayo");
        data.addValue(gasto[5], x, "junio");
        data.addValue(gasto[6], x, "julio");
        data.addValue(gasto[7], x, "agosto");
        data.addValue(gasto[8], x, "septiembre");
        data.addValue(gasto[9], x, "ocrubre");
        data.addValue(gasto[10], x, "noviembre");
        data.addValue(gasto[11], x, "diciembre");
        
        
        JFreeChart grafica = ChartFactory.createBarChart("Grafica gastos del año", "mes", "Dinero", data, PlotOrientation.VERTICAL, true, true, false);
        
        ChartPanel contenedor = new ChartPanel(grafica);
        
        JFrame ventana = new JFrame("Grafic expense");
        ventana.add(contenedor);
        ventana.setSize(900, 600);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
               
    }
    
    public void leer (String mes, int m){
        System.out.println("mes: "+ mes);
        for (int i = 0; i < pr.cantidadRegistro() ; i++) {
            System.out.println("algo: "+pr.obtenerRegistro(i).getgFecha().split("-")[1]);
            if(mes.equals(pr.obtenerRegistro(i).getgFecha().split("-")[1]) ) { 
                gasto[m] = Integer.valueOf(pr.obtenerRegistro(i).getgGasto())+ gasto[m]; 
                System.out.println("gasto: "+ gasto[m] );
            }
        }
        
    }
    
    public void edu (){
        
        for (int i = 0; i < 12; i++) {
            String mes = null;
            if (String.valueOf(i+1).length() == 1) {
                mes = "0"+String.valueOf(i+1);
            }else{
                mes = String.valueOf(i+1);
            }
            leer(mes, i);
            
        }
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Proceso;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
    private JPanel Panelgrafico;
    private JButton volver;
    private Principal Vp;
    private Proceso pr;
    private int gasto[] = new int[12];
    
    JFreeChart grafica;
    DefaultCategoryDataset datos = new DefaultCategoryDataset();

    public graficoGasto(Principal vp, Proceso pr) {
        this.pr = pr;
        conteoMes();
        this.Vp = vp;
        initComponent();
       
    }

    private void initComponent() {
//        this.addWindowListener(new WindowAdapter() {
//            public void WindowClosing(WindowEvent e){
//                Vp.setVisible(true);
//                graficoGasto.this.dispose();
//            }
//        });

        this.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        this.setTitle("Grafic");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        this.Panelgrafico = new JPanel();
        this.Panelgrafico.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this.Panelgrafico);
        this.Panelgrafico.setLayout(null);

        this.volver = new JButton("Volver");
        this.volver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                volver();
            }
        });
        this.volver.setBounds(430, 570, 89, 23);
        Panelgrafico.add(this.volver);
        
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        final String x = "gasto";
        
        
        data.addValue(gasto[0], x, "ene");
        data.addValue(gasto[1], x, "feb");
        data.addValue(gasto[2], x, "mar");
        data.addValue(gasto[3], x, "abr");
        data.addValue(gasto[4], x, "may");
        data.addValue(gasto[5], x, "jun");
        data.addValue(gasto[6], x, "jul");
        data.addValue(gasto[7], x, "ago");
        data.addValue(gasto[8], x, "sep");
        data.addValue(gasto[9], x, "oct");
        data.addValue(gasto[10], x, "nov");
        data.addValue(gasto[11], x, "dic");
        
        
        JFreeChart grafica = ChartFactory.createBarChart("Grafica gastos", "mes", "Dinero", data, PlotOrientation.VERTICAL, true, true, false);
        
        ChartPanel contenedor = new ChartPanel(grafica);
        
        contenedor.setBounds(15, 10, 900, 550);
        this.Panelgrafico.add(contenedor);
        
        this.setSize(950, 650);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
//        JFrame ventana = new JFrame("Grafic expense");
//        ventana.add(contenedor);
//        ventana.setSize(900, 600);
//        ventana.setVisible(true);
//        ventana.setLocationRelativeTo(null);
               
    }
    
    public void sumaGastoMes (String mes, int m){
        for (int i = 0; i < pr.cantidadRegistro() ; i++) {
            if(mes.equals(pr.obtenerRegistro(i).getgFecha().split("-")[1]) ) { 
                gasto[m] = Integer.valueOf(pr.obtenerRegistro(i).getgGasto())+ gasto[m]; 
            }
        }
        
    }
    
    public void conteoMes (){
        
        for (int i = 0; i < 12; i++) {
            String mes = null;
            if (String.valueOf(i+1).length() == 1) {
                mes = "0"+String.valueOf(i+1);
            }else{
                mes = String.valueOf(i+1);
            }
            sumaGastoMes(mes, i);
            
        }
    }
    
    public void volver(){
        this.dispose();
        Vp.setVisible(true);
        
    }
    
    
    
}

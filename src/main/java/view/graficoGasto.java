package view;

import data.Proceso;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class graficoGasto extends JFrame {

    private JPanel Panelgrafico;
    private JButton btnVolver;
    private Principal Vp;
    private Proceso pr;
    private int gasto[] = new int[12];
    private JMenuBar jmbMenu;
    private JMenu jmVolver;

    JFreeChart grafica;
    DefaultCategoryDataset datos = new DefaultCategoryDataset();

    public graficoGasto(Principal vp, Proceso pr) {
        this.pr = pr;
        conteoMes();
        this.Vp = vp;
        initComponent();

    }

    private void initComponent() {

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("Grafic");

        this.Panelgrafico = new JPanel();
        this.Panelgrafico.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this.Panelgrafico);
        this.Panelgrafico.setLayout(null);

        jmbMenu = new JMenuBar();
        jmVolver = new JMenu();
        jmVolver.setToolTipText("volver");
        jmVolver.setIcon(new ImageIcon("imagenes\\back2.png"));
        jmVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                volver();
            }
        });
        jmbMenu.add(jmVolver);
        setJMenuBar(jmbMenu);

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

        JFreeChart jfcGrafica = ChartFactory.createBarChart("Grafica gastos", "mes", "Dinero", data, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel contenedor = new ChartPanel(jfcGrafica);

        contenedor.setBounds(15, 10, 900, 560);
        this.Panelgrafico.add(contenedor);

        this.setSize(940, 660);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void sumaGastoMes(String mes, int m) {
        for (int i = 0; i < pr.cantidadRegistro(); i++) {
            if (mes.equals(pr.obtenerRegistro(i).getgFecha().split("-")[1])) {
                gasto[m] = Integer.valueOf(pr.obtenerRegistro(i).getgGasto()) + gasto[m];
            }
        }

    }

    public void conteoMes() {

        for (int i = 0; i < 12; i++) {
            String mes = null;
            if (String.valueOf(i + 1).length() == 1) {
                mes = "0" + String.valueOf(i + 1);
            } else {
                mes = String.valueOf(i + 1);
            }
            sumaGastoMes(mes, i);

        }
    }

    public void volver() {
        this.dispose();
        Vp.setVisible(true);

    }

}

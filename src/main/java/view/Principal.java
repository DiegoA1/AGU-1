package view;

import data.Gasto;
import data.Proceso;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Principal extends JFrame {//AgregarQUARTZ 

    private Gasto g;
    private Proceso pr;
    private File f;
    private JPanel panelPrincipal;
    private JLabel lblGastoTotal;
    private JTextField txtGastoTotal;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnGraph;
    private JScrollPane tablaContenedora;
    private JTable tabla;
    private JMenu menuAyuda;
    private JMenu menuSalir;
    private JMenuBar menuBarra;

    public Principal() {
        initComponents();
        pr = new Proceso();
        try {
            leerArchivo();
            setTabla(agregarLista());
            totalGastado();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void initComponents() {

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Home");

        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelPrincipal);
        panelPrincipal.setLayout(null);

        lblGastoTotal = new JLabel("Gasto Total:");
        lblGastoTotal.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblGastoTotal.setBounds(10, 40, 230, 20);
        panelPrincipal.add(this.lblGastoTotal);

        this.txtGastoTotal = new JTextField();
        this.txtGastoTotal.setText("0");
        this.txtGastoTotal.setToolTipText("Gasto hechos");
        this.txtGastoTotal.setBounds(170, 10, 350, 80);
        this.txtGastoTotal.setEditable(false);
        this.txtGastoTotal.setFont(new java.awt.Font("Tahoma", Font.BOLD, 48));
        this.panelPrincipal.add(this.txtGastoTotal);

        btnAdd = new JButton("");
        btnAdd.setToolTipText("Agregar un gasto");
        btnAdd.setIcon(new ImageIcon("imagenes\\add.png"));
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                agregarGasto();
            }
        });
        btnAdd.setBounds(548, 40, 50, 50);
        panelPrincipal.add(btnAdd);

        btnDelete = new JButton("");
        btnDelete.setToolTipText("Borrar un gasto");
        btnDelete.setIcon(new ImageIcon("imagenes\\delete.png"));
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                borrarGasto();
            }
        });
        btnDelete.setBounds(618, 40, 50, 50);
        panelPrincipal.add(btnDelete);

        btnClear = new JButton("");
        btnClear.setToolTipText("Limpiar la tabla");
        btnClear.setIcon(new ImageIcon("imagenes\\clear.png"));
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                limpiarTabla();
            }
        });
        btnClear.setBounds(688, 40, 50, 50);
        panelPrincipal.add(btnClear);

        btnGraph = new JButton("");
        btnGraph.setToolTipText("Grafico");
        btnGraph.setIcon(new ImageIcon("imagenes\\graph.png"));
        btnGraph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graficoGasto();
            }
        });
        btnGraph.setBounds(758, 40, 50, 50);
        panelPrincipal.add(btnGraph);

        menuBarra = new JMenuBar();
        menuAyuda = new JMenu("Ayuda");
        menuSalir = new JMenu("Salir");

        menuAyuda.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Cualquier consulta contactese con:\n"
                        + "\n"
                        + "Cristobal Carrion, Correo c.carrion01@ufromail.cl, Pais Chile\n"
                        + "Diego Agüero, Correo d.aguero02@ufromail.cl, Pais Chile");
            }
        });
        menuBarra.add(menuAyuda);

        menuSalir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        menuBarra.add(menuSalir);
        setJMenuBar(menuBarra);

        tablaContenedora = new JScrollPane();
        tabla = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Gasto", "Descripcion", "Tipo de pago", "Categoria", "Fecha"
                }
        ));

        tablaContenedora.setViewportView(tabla);
        tablaContenedora.setBounds(10, 100, 800, 400);
        panelPrincipal.add(tablaContenedora);

        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGraph.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.setSize(835, 570);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void leerArchivo() {
        f = new File("gasto.txt");
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String linea = null;
            while ((linea = br.readLine()) != null) {
                String[] c = linea.split("%");
                g = new Gasto(c[0], c[1], c[2], c[3], c[4]);
                pr.agregarRegistro(g);
            }
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hola apreta en el icono verde para\n"
                    + "ingresar tu primer producto");
        }
    }

    public void setTabla(String[][] tablaGastos) {
        this.tabla.setModel(new DefaultTableModel(
                tablaGastos,
                new String[]{
                    "Gasto", "Descripcion", "Tipo de pago", "Categoria", "Fecha"
                }
        ));
        tablaContenedora.setViewportView(tabla);
        tablaContenedora.setBounds(10, 100, 800, 400);
        panelPrincipal.add(tablaContenedora);
    }

    public String[][] agregarLista() {
        String MatrizAgregar[][] = new String[pr.cantidadRegistro()][5];
        for (int i = 0; i < pr.cantidadRegistro(); i++) {
            g = pr.obtenerRegistro(i);
            MatrizAgregar[i][0] = g.getgGasto();
            MatrizAgregar[i][1] = g.getgDescripcion();
            MatrizAgregar[i][2] = g.getgTipoDePago();
            MatrizAgregar[i][3] = g.getgCategoria();
            MatrizAgregar[i][4] = g.getgFecha();
        }
        return MatrizAgregar;
    }

    public void agregarGasto() {
        this.dispose();
        AgregarGasto ag = new AgregarGasto();
    }

    public void totalGastado() {
        if (pr.cantidadRegistro() >= 0) {
            int cont = 0;
            for (int i = 0; i < pr.cantidadRegistro(); i++) {
                g = pr.obtenerRegistro(i);
                int a = Integer.parseInt(String.valueOf(g.getgGasto()));
                cont = cont + a;
            }
            txtGastoTotal.setText(String.valueOf(cont));
        } else {
            txtGastoTotal.setText("0");
        }
    }

    public void borrarGasto() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            pr.eliminarRegistro(fila);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila primero");
        }
        borrarGastoTxt();
        setTabla(agregarLista());
        totalGastado();

    }

    public void borrarGastoTxt() {
        f.delete();
        f = new File("gasto.txt");
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(f, false);
            pw = new PrintWriter(fw);
            for (int i = 0; i < pr.cantidadRegistro(); i++) {
                g = pr.obtenerRegistro(i);
                pw.println(String.valueOf(g.getgGasto() + "%" + g.getgDescripcion() + "%" + g.getgTipoDePago() + "%" + g.getgCategoria() + "%" + g.getgFecha()));
            }
            pw.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void limpiarTabla() {
        int cant = pr.cantidadRegistro();
        for (int i = 0; i < cant; i++) {
            pr.eliminarRegistro(0);
        }
        f.delete();
        setTabla(agregarLista());
        totalGastado();
    }

    public void graficoGasto() {//Falta por hacer//<-----------------------------------------------
        this.dispose();
        graficoGasto gG = new graficoGasto(this, pr);

    }
}

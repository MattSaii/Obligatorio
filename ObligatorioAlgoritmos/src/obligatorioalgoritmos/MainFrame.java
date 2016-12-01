package obligatorioalgoritmos;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import maps.java.Elevation;
import maps.java.Geocoding;
import maps.java.MapsJava;
import maps.java.Places;
import maps.java.Route;
import maps.java.StaticMaps;
import maps.java.StreetView;
import maps.java.ShowMaps;
import obligatorioalgoritmos.ISistema;

/**
 *
 * @author Luis Marcos
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        capturarEventos();
    }

    private EventsStatusBar ObjStatusBar;
    private static ISistema Sistema = new Sistema();

    private Geocoding ObjGeocoding = new Geocoding();
    private Elevation ObjElevation = new Elevation();
    private ShowMaps ObjShowMaps = new ShowMaps();
    private Route ObjRoute = new Route();
    private StreetView ObjStreetView = new StreetView();
    private StaticMaps ObjStaticMaps = new StaticMaps();
    private Places ObjPlaces = new Places();

    private void cargarComboBoxColores() {
        empresaColorCombo.removeAllItems();
        final DefaultComboBoxModel modeloColores = new DefaultComboBoxModel();
        modeloColores.addElement(new ItemComboBox("Amarillo", 0));
        modeloColores.addElement(new ItemComboBox("Azul", 1));
        modeloColores.addElement(new ItemComboBox("Negro", 2));
        modeloColores.addElement(new ItemComboBox("Marron", 3));
        modeloColores.addElement(new ItemComboBox("Verde", 4));
        modeloColores.addElement(new ItemComboBox("Blanco", 5));
        modeloColores.addElement(new ItemComboBox("Rojo", 6));
        modeloColores.addElement(new ItemComboBox("Violeta", 7));
        modeloColores.addElement(new ItemComboBox("Naranja", 8));

        empresaColorCombo.setModel(modeloColores);
    }

    private void cargarComboBoxPuntos() {
        comboOrigen.removeAllItems();
        comboDestino.removeAllItems();
        final DefaultComboBoxModel modeloOrigen = new DefaultComboBoxModel();
        final DefaultComboBoxModel modeloDestino = new DefaultComboBoxModel();
        Punto[] puntos = Sistema.getCantPuntos();

        for (int i = 0; i < puntos.length; i++) {
            String s = new String();
            if (puntos[i] instanceof Ciudad) {
                s = new StringBuilder("Ciudad :" + Sistema.darCiudad(puntos[i].getX(), puntos[i].getY()).getNombre()).toString();
                modeloOrigen.addElement(new ItemComboBox(s, i));
                modeloDestino.addElement(new ItemComboBox(s, i));
            }
            if (puntos[i] instanceof DataCenter) {
                s = new StringBuilder("DC :" + Sistema.darDataCenter(puntos[i].getX(), puntos[i].getY()).getNombre()).toString();
                modeloOrigen.addElement(new ItemComboBox(s, i));
                modeloDestino.addElement(new ItemComboBox(s, i));
            } else {

            }

        }
        comboOrigen.setModel(modeloOrigen);
        comboDestino.setModel(modeloDestino);
    }

    private void cargarComboBoxEmpresa() {
        comboDC.removeAllItems();
        final DefaultComboBoxModel modeloEmpresas = new DefaultComboBoxModel();
        int i = 0;
        String cbe = Sistema.listadoComboBoxEmpresa();
        String[] split = cbe.split("-");
        String s = "";

        for (String j : split) {
            modeloEmpresas.addElement(new ItemComboBox(j, i));
            i++;
        }

        comboDC.setModel(modeloEmpresas);
    }

    private void cargarComboBoxDC() {
        comboDataCenter.removeAllItems();
        final DefaultComboBoxModel modeloDC = new DefaultComboBoxModel();
        Punto[] puntos = Sistema.getCantPuntos();

        for (int i = 0; i < puntos.length; i++) {
            String s = new String();
            if (puntos[i] instanceof DataCenter) {
                s = new StringBuilder("DC :" + Sistema.darDataCenter(puntos[i].getX(), puntos[i].getY()).getNombre()).toString();
                modeloDC.addElement(new ItemComboBox(s, i));
            }

        }
        comboDataCenter.setModel(modeloDC);
    }

    private void capturarEventos() {
        //ObjStatusBar=new EventsStatusBar(this.jPanel5);
        recorrerComponentes(jTabbedPane1.getComponents());
        recorrerComponentes(jPanel1.getComponents());
        recorrerComponentes(jPanel2.getComponents());
        recorrerComponentes(jPanel3.getComponents());
        recorrerComponentes(jPanel4.getComponents());
        recorrerComponentes(jPanel6.getComponents());
        recorrerComponentes(jPanel7.getComponents());
        //recorrerComponentes(jPanel8.getComponents());
        //recorrerComponentes(jPanel9.getComponents());
    }

    double redondeoDosDecimales(double d) {
        return Math.rint(d * 1000) / 1000;
    }

    private void recorrerComponentes(Component[] componentes) {
        for (int i = 0; i < componentes.length; i++) {
            componentes[i].addMouseListener(ObjStatusBar);
        }
    }

    private void comprobarStatus(JLabel label) {
        label.setText(MapsJava.getLastRequestStatus());
    }

    private void cargarJList(ArrayList<String> arrayList, JList jlist) {
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < arrayList.size(); i++) {
            listModel.add(i, arrayList.get(i));
        }
        jlist.setModel(listModel);
    }

    private void rellenarPeticiones() {
        String[][] peticiones = MapsJava.getStockRequest();
        String[] columnas = new String[6];
        columnas[0] = "Número";
        columnas[1] = "Hora";
        columnas[2] = "Status";
        columnas[3] = "URL";
        columnas[4] = "Información";
        columnas[5] = "Excepción";
        TableModel tableModel = new DefaultTableModel(peticiones, columnas);
        //this.jTable_Peticiones.setModel(tableModel);
    }

    private void mostrarMapa(String direccion) throws IOException, URISyntaxException {
        String direccionMapa = ObjShowMaps.getURLMap(direccion);
        Desktop.getDesktop().browse(new URI(direccionMapa));
    }

    private void mostrarMapa(Double latitud, Double longitud) throws URISyntaxException, IOException {
        String direccionMapa = ObjShowMaps.getURLMap(latitud, longitud);
        Desktop.getDesktop().browse(new URI(direccionMapa));
    }

    private void CodiGeografica() throws UnsupportedEncodingException, MalformedURLException {
        if (!this.JText_CD_Direc.getText().isEmpty()) {
            JText_CD_DireEnc.setText("");
            Point2D.Double resultado = ObjGeocoding.getCoordinates(this.JText_CD_Direc.getText());
            JText_CD_Lati.setText(String.valueOf(resultado.x));
            JText_CD_Long.setText(String.valueOf(resultado.y));
            JText_CD_DireEnc.setText(String.valueOf(ObjGeocoding.getAddressFound()));
        }
    }

    private void CodiGeografica2() throws UnsupportedEncodingException, MalformedURLException {
        if (!this.jTextField1.getText().isEmpty()) {
            JText_CD_DireEnc.setText("");
            Point2D.Double resultado = ObjGeocoding.getCoordinates(this.JText_CD_Direc.getText());
            JText_CD_Lati1.setText(String.valueOf(resultado.x));
            JText_CD_Long1.setText(String.valueOf(resultado.y));
            jTextField1.setText(String.valueOf(ObjGeocoding.getAddressFound()));

        }
    }

    private void elevacionCD(JTextField txtLati, JTextField txtLong) throws MalformedURLException {
        if (!txtLati.getText().isEmpty() && !txtLong.getText().isEmpty()) {
            double resultado = ObjElevation.getElevation(Double.valueOf(txtLati.getText()),
                    Double.valueOf(txtLong.getText()));

        }
    }

    private void elevacionCI(JTextField txtLati, JTextField txtLong) throws MalformedURLException {
        if (!txtLati.getText().isEmpty() && !txtLong.getText().isEmpty()) {
            double resultado = ObjElevation.getElevation(Double.valueOf(txtLati.getText()),
                    Double.valueOf(txtLong.getText()));

        }
    }

////    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooser1 = new javax.swing.JColorChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSistema = new javax.swing.JTextField();
        IniciarSistema = new javax.swing.JButton();
        JLabel_Clave = new javax.swing.JLabel();
        destruirSistema = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        consolaSistema = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        JText_CD_Direc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        JText_CD_Lati = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        JText_CD_Long = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        JText_CD_Buscar = new javax.swing.JButton();
        JLabel_CD_Status = new javax.swing.JLabel();
        JText_CD_DireEnc = new javax.swing.JTextField();
        agregarCiudad = new javax.swing.JButton();
        eliminarCiudad = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        consolaCiudad = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        JLabel_CI_Status = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDir = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPais = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtMail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        agregarEmpresa = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        consolaEmpresa = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        empresaColorCombo = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        buscarCiudadDC = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        JText_CD_Lati1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtNombreDC = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        JText_CD_Long1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtCostoDC = new javax.swing.JTextField();
        registrarDC = new javax.swing.JButton();
        txtCapaciDC = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        consolaDC = new javax.swing.JTextArea();
        comboDC = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        JLabel_Ruta_Status = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        comboOrigen = new javax.swing.JComboBox();
        comboDestino = new javax.swing.JComboBox();
        elimTramo = new javax.swing.JButton();
        bottonTramo = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        consolaTramo = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jButton_Peticiones = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        comboDataCenter = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        txtER = new javax.swing.JTextField();
        botonFuncion = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        consolaMapa = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Mapas");
        setAlwaysOnTop(true);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(353, 401));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel4.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Registrar Sistema");

        jLabel9.setText("Cantidad de Puntos:");

        IniciarSistema.setText("Inicializar Sistema");
        IniciarSistema.setToolTipText("");
        IniciarSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IniciarSistemaActionPerformed(evt);
            }
        });

        destruirSistema.setText("Destruir Sistema");
        destruirSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destruirSistemaActionPerformed(evt);
            }
        });

        consolaSistema.setColumns(20);
        consolaSistema.setRows(5);
        jScrollPane1.setViewportView(consolaSistema);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(9, 9, 9)
                                .addComponent(txtSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(destruirSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IniciarSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JLabel_Clave)
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IniciarSistema))
                .addGap(18, 18, 18)
                .addComponent(destruirSistema)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(JLabel_Clave))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jLabel8.getAccessibleContext().setAccessibleName("region");
        jLabel9.getAccessibleContext().setAccessibleName("idioma");
        txtSistema.getAccessibleContext().setAccessibleName("region");
        IniciarSistema.getAccessibleContext().setAccessibleName("infoIdioma");

        jTabbedPane1.addTab("Sistema", jPanel4);
        jPanel4.getAccessibleContext().setAccessibleName("Principal");

        jLabel1.setText("Nombre");

        JText_CD_Direc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JText_CD_DirecActionPerformed(evt);
            }
        });

        jLabel2.setText("Latitud");

        JText_CD_Lati.setEditable(false);

        jLabel3.setText("Longitud");

        JText_CD_Long.setEditable(false);

        jLabel4.setText("Ciudad Encontrada");

        JText_CD_Buscar.setBackground(new java.awt.Color(153, 153, 255));
        JText_CD_Buscar.setText("Buscar");
        JText_CD_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JText_CD_BuscarActionPerformed(evt);
            }
        });

        JLabel_CD_Status.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        JLabel_CD_Status.setName(""); // NOI18N

        JText_CD_DireEnc.setEditable(false);

        agregarCiudad.setText("Agregar Ciudad");
        agregarCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarCiudadActionPerformed(evt);
            }
        });

        eliminarCiudad.setText("Eliminar Ciudad");
        eliminarCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarCiudadActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Registrar Ciudad");

        consolaCiudad.setColumns(20);
        consolaCiudad.setRows(5);
        jScrollPane4.setViewportView(consolaCiudad);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JText_CD_DireEnc)
                    .addComponent(JText_CD_Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JText_CD_Direc)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(JText_CD_Long, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel4))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JLabel_CD_Status, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JText_CD_Lati, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(agregarCiudad)
                                    .addComponent(eliminarCiudad)))
                            .addComponent(jLabel10)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 181, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JText_CD_Direc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JText_CD_Buscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JLabel_CD_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JText_CD_Lati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(agregarCiudad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JText_CD_Long, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarCiudad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JText_CD_DireEnc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.getAccessibleContext().setAccessibleName("CDdireccion");
        JText_CD_Direc.getAccessibleContext().setAccessibleName("CDdireccion");
        jLabel2.getAccessibleContext().setAccessibleName("CD_latitud");
        JText_CD_Lati.getAccessibleContext().setAccessibleName("CD_latitud");
        jLabel3.getAccessibleContext().setAccessibleName("CD_longitud");
        JText_CD_Long.getAccessibleContext().setAccessibleName("CD_longitud");
        JText_CD_Long.getAccessibleContext().setAccessibleDescription("");
        jLabel4.getAccessibleContext().setAccessibleName("CD_dirEncontr");
        JText_CD_Buscar.getAccessibleContext().setAccessibleName("CDdireccionBuscar");
        JLabel_CD_Status.getAccessibleContext().setAccessibleName("status");
        JText_CD_DireEnc.getAccessibleContext().setAccessibleName("CD_dirEncontr");

        jTabbedPane1.addTab("Ciudades", jPanel1);
        jPanel1.getAccessibleContext().setAccessibleName("Principal");

        JLabel_CI_Status.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        JLabel_CI_Status.setName(""); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Registrar Empresa");

        jLabel5.setText("Nombre:");

        jLabel6.setText("Dirección:");

        jLabel7.setText("Pais:");

        jLabel12.setText("Email:");

        txtMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMailActionPerformed(evt);
            }
        });

        jLabel13.setText("Color:");

        agregarEmpresa.setText("Agregar Empresa");
        agregarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarEmpresaActionPerformed(evt);
            }
        });

        consolaEmpresa.setColumns(20);
        consolaEmpresa.setRows(5);
        jScrollPane7.setViewportView(consolaEmpresa);

        jButton1.setText("Listar Empresa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        empresaColorCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Amarillo", "Rojo", "Azul", "Verde" }));
        empresaColorCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresaColorComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(JLabel_CI_Status)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel11))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPais, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(txtDir, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(txtMail, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(empresaColorCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(agregarEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))))
                        .addGap(0, 54, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(agregarEmpresa))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(JLabel_CI_Status))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(empresaColorCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        JLabel_CI_Status.getAccessibleContext().setAccessibleName("status");
        JLabel_CI_Status.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Empresa", jPanel2);
        jPanel2.getAccessibleContext().setAccessibleName("Principal");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Registrar Data Center");

        jLabel15.setText("Nombre");

        buscarCiudadDC.setText("Buscar");
        buscarCiudadDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCiudadDCActionPerformed(evt);
            }
        });

        jLabel16.setText("Latitud");

        JText_CD_Lati1.setEditable(false);
        JText_CD_Lati1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JText_CD_Lati1ActionPerformed(evt);
            }
        });

        jLabel17.setText("Longitud");

        jLabel18.setText("Nombre");

        jLabel23.setText("Empresa");

        jLabel24.setText("Capacidad de CPU por Hora");

        JText_CD_Long1.setEditable(false);

        jLabel27.setText("Costo CPU por Hora");

        registrarDC.setText("Agregar Data Center");
        registrarDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarDCActionPerformed(evt);
            }
        });

        consolaDC.setColumns(20);
        consolaDC.setRows(5);
        jScrollPane8.setViewportView(consolaDC);

        comboDC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDCActionPerformed(evt);
            }
        });

        jButton2.setText("Eliminar Data Center");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(buscarCiudadDC, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addGap(115, 115, 115))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtCapaciDC, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(JText_CD_Long1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(JText_CD_Lati1))
                                    .addGap(55, 55, 55)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(registrarDC, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel23)
                            .addComponent(jLabel27)
                            .addComponent(txtCostoDC)
                            .addComponent(comboDC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreDC)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)))
                    .addComponent(jScrollPane8))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarCiudadDC))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JText_CD_Lati1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JText_CD_Long1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCostoDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCapaciDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registrarDC)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Data Center", jPanel7);
        jPanel7.getAccessibleContext().setAccessibleName("Principal");

        jLabel19.setText("Dirección de origen");

        jLabel20.setText("Dirección de destino");

        JLabel_Ruta_Status.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setText("Registrar Tramo");

        jLabel29.setText("Peso");

        comboOrigen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboDestino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDestinoActionPerformed(evt);
            }
        });

        elimTramo.setText("Eliminar Tramo");
        elimTramo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elimTramoActionPerformed(evt);
            }
        });

        bottonTramo.setText("Registrar Tramo");
        bottonTramo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bottonTramoActionPerformed(evt);
            }
        });

        consolaTramo.setColumns(20);
        consolaTramo.setRows(5);
        jScrollPane3.setViewportView(consolaTramo);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(bottonTramo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(elimTramo)
                .addGap(84, 84, 84))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19)
                    .addComponent(JLabel_Ruta_Status)
                    .addComponent(jLabel28)
                    .addComponent(jLabel20)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboOrigen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboDestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(JLabel_Ruta_Status)
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bottonTramo)
                    .addComponent(elimTramo))
                .addGap(46, 46, 46)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jLabel19.getAccessibleContext().setAccessibleName("direccionOrigen");
        jLabel20.getAccessibleContext().setAccessibleName("direccionDestino");
        txtPeso.getAccessibleContext().setAccessibleName("hito");
        JLabel_Ruta_Status.getAccessibleContext().setAccessibleName("status");

        jTabbedPane1.addTab("Tramo", jPanel6);
        jPanel6.getAccessibleContext().setAccessibleName("Principal");

        jButton_Peticiones.setBackground(new java.awt.Color(153, 153, 255));
        jButton_Peticiones.setText("Mostrar Mapa");
        jButton_Peticiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PeticionesActionPerformed(evt);
            }
        });

        jLabel21.setText("Lista de DataCenter");

        comboDataCenter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel22.setText("Esfuerzo requerido");

        txtER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtERActionPerformed(evt);
            }
        });

        botonFuncion.setText("Enviar");
        botonFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonFuncionActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setText("Mapa");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        consolaMapa.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_Peticiones, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addComponent(botonFuncion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtER, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(comboDataCenter, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel25))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(consolaMapa))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(14, 14, 14)
                .addComponent(jButton_Peticiones)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(comboDataCenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(txtER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(botonFuncion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(consolaMapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton_Peticiones.getAccessibleContext().setAccessibleName("mostrPeticiones");

        jTabbedPane1.addTab("Mapa", jPanel3);
        jPanel3.getAccessibleContext().setAccessibleName("Principal");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Principal");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.JText_CD_Direc.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void JText_CD_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JText_CD_BuscarActionPerformed
        try {
            this.CodiGeografica();
            this.comprobarStatus(JLabel_CD_Status);
            //this.elevacionCD(this.JText_CD_Lati,this.JText_CD_Long);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_JText_CD_BuscarActionPerformed

    private void jButton_PeticionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PeticionesActionPerformed
        Sistema.mapaEstado();
    }//GEN-LAST:event_jButton_PeticionesActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged

    }//GEN-LAST:event_jTabbedPane1StateChanged

//####################################################################################
//###############################CODIGO###############################################
//####################################################################################

    private void IniciarSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IniciarSistemaActionPerformed
        consolaSistema.setText(Sistema.inicializarSistema(Integer.parseInt(txtSistema.getText())).resultado.toString());
        cargarComboBoxColores();
    }//GEN-LAST:event_IniciarSistemaActionPerformed

    private void seleccionarReferencia() {
//       if(jTable_Pl_places.getRowCount()>0){
//          this.JText_Pl_Referencia.setText((String)jTable_Pl_places.getValueAt(jTable_Pl_places.getSelectedRow(),5));
//        }
    }
    private void destruirSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destruirSistemaActionPerformed

        consolaSistema.setText(Sistema.destruirSistema().resultado.toString());

    }//GEN-LAST:event_destruirSistemaActionPerformed

    private void txtMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMailActionPerformed

    private void JText_CD_DirecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JText_CD_DirecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JText_CD_DirecActionPerformed

    private void agregarCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarCiudadActionPerformed
        consolaCiudad.setText(Sistema.registrarCiudad(JText_CD_Direc.getText(), Double.parseDouble(JText_CD_Lati.getText()), Double.parseDouble(JText_CD_Long.getText())).resultado.toString());
        cargarComboBoxPuntos();

    }//GEN-LAST:event_agregarCiudadActionPerformed

    private void eliminarCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarCiudadActionPerformed
        consolaCiudad.setText(Sistema.eliminarPunto(Double.parseDouble(JText_CD_Lati.getText()), Double.parseDouble(JText_CD_Long.getText())).resultado.toString());
        cargarComboBoxPuntos();
    }//GEN-LAST:event_eliminarCiudadActionPerformed

    private void agregarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarEmpresaActionPerformed
        consolaEmpresa.setText(Sistema.registrarEmpresa(txtNombre.getText(), txtDir.getText(), txtPais.getText(), txtMail.getText(), empresaColorCombo.getSelectedItem().toString()).resultado.toString());
        cargarComboBoxEmpresa();
    }//GEN-LAST:event_agregarEmpresaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        consolaEmpresa.setText(Sistema.magico().toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void JText_CD_Lati1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JText_CD_Lati1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JText_CD_Lati1ActionPerformed

    private void buscarCiudadDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCiudadDCActionPerformed
        try {
            this.CodiGeografica2();
            this.comprobarStatus(JLabel_CD_Status);
            //this.elevacionCD(this.JText_CD_Lati,this.JText_CD_Long);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_buscarCiudadDCActionPerformed

    private void registrarDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarDCActionPerformed
        consolaDC.setText(Sistema.registrarDC(txtNombreDC.getText(), Double.parseDouble(JText_CD_Lati1.getText()), Double.parseDouble(JText_CD_Long1.getText()), comboDC.getSelectedItem().toString(), Integer.parseInt(txtCapaciDC.getText()), Integer.parseInt(txtCostoDC.getText())).resultado.toString());
        cargarComboBoxPuntos();
        cargarComboBoxDC();
    }//GEN-LAST:event_registrarDCActionPerformed

    private void empresaColorComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresaColorComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_empresaColorComboActionPerformed

    private void comboDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDCActionPerformed

    }//GEN-LAST:event_comboDCActionPerformed

    private void comboDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDestinoActionPerformed

    private void elimTramoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elimTramoActionPerformed
        Object obj1 = comboOrigen.getSelectedItem();
        int value1 = ((ItemComboBox) obj1).getValue();
        Object obj2 = comboDestino.getSelectedItem();
        int value2 = ((ItemComboBox) obj2).getValue();
        consolaTramo.setText(Sistema.eliminarTramo(Sistema.PuntoXposicion(value1).getX(), Sistema.PuntoXposicion(value1).getY(), Sistema.PuntoXposicion(value2).getX(), Sistema.PuntoXposicion(value2).getY()).resultado.toString());

    }//GEN-LAST:event_elimTramoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        consolaDC.setText(Sistema.eliminarPunto(Double.parseDouble(JText_CD_Lati1.getText()), Double.parseDouble(JText_CD_Long1.getText())).resultado.toString());
        cargarComboBoxPuntos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bottonTramoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bottonTramoActionPerformed
        Object origen = comboOrigen.getSelectedItem();
        int datoOrigen = ((ItemComboBox) origen).getValue();
        Object destino = comboDestino.getSelectedItem();
        int datoDestino = ((ItemComboBox) destino).getValue();
        consolaTramo.setText(Sistema.registrarTramo(datoOrigen, datoDestino, Integer.parseInt(txtPeso.getText())).resultado.toString());
    }//GEN-LAST:event_bottonTramoActionPerformed

    private void botonFuncionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFuncionActionPerformed
       Object obj1 = comboDataCenter.getSelectedItem();
        int value1 = ((ItemComboBox) obj1).getValue();
        jTextArea1.setText(Sistema.procesarInformacion(Sistema.PuntoXposicion(value1).getX(),Sistema.PuntoXposicion(value1).getY(), Integer.parseInt(txtER.getText())).valorString);
    }//GEN-LAST:event_botonFuncionActionPerformed

    private void txtERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtERActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtERActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton IniciarSistema;
    private javax.swing.JLabel JLabel_CD_Status;
    private javax.swing.JLabel JLabel_CI_Status;
    private javax.swing.JLabel JLabel_Clave;
    private javax.swing.JLabel JLabel_Ruta_Status;
    private javax.swing.JButton JText_CD_Buscar;
    private javax.swing.JTextField JText_CD_DireEnc;
    private javax.swing.JTextField JText_CD_Direc;
    private javax.swing.JTextField JText_CD_Lati;
    private javax.swing.JTextField JText_CD_Lati1;
    private javax.swing.JTextField JText_CD_Long;
    private javax.swing.JTextField JText_CD_Long1;
    private javax.swing.JButton agregarCiudad;
    private javax.swing.JButton agregarEmpresa;
    private javax.swing.JButton botonFuncion;
    private javax.swing.JButton bottonTramo;
    private javax.swing.JButton buscarCiudadDC;
    private javax.swing.JComboBox comboDC;
    private javax.swing.JComboBox comboDataCenter;
    private javax.swing.JComboBox comboDestino;
    private javax.swing.JComboBox comboOrigen;
    private javax.swing.JTextArea consolaCiudad;
    private javax.swing.JTextArea consolaDC;
    private javax.swing.JTextArea consolaEmpresa;
    private javax.swing.JScrollPane consolaMapa;
    private javax.swing.JTextArea consolaSistema;
    private javax.swing.JTextArea consolaTramo;
    private javax.swing.JButton destruirSistema;
    private javax.swing.JButton elimTramo;
    private javax.swing.JButton eliminarCiudad;
    private javax.swing.JComboBox empresaColorCombo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton_Peticiones;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton registrarDC;
    private javax.swing.JTextField txtCapaciDC;
    private javax.swing.JTextField txtCostoDC;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtER;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreDC;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtSistema;
    // End of variables declaration//GEN-END:variables
}

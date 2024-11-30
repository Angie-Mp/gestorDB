package vistas;

import clases.operaciones;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class ventana_tabla extends javax.swing.JFrame implements KeyListener {

    private final String tabla;
    private final operaciones op;
    private DefaultTableModel modelo, model_col;
    ventana_principal ven_bd;
    private boolean inserta, agrega;
    private final Vector<String> otras_tablas;
    private JPopupMenu menu;
    private Vector<String> col;//columnas de la tabla

    public ventana_tabla(operaciones x2, ventana_principal vbd, String t, Vector t2) {
        op = x2;
        ven_bd = vbd;
        tabla = t;
        inserta = agrega = false;
        otras_tablas = t2;
        initComponents();
        cargar();

        setEventoMouseClicked(jTable1);
        crear_col.addKeyListener(this);
        borrar_col.addKeyListener(this);
        crear_pk.addKeyListener(this);
        crear_pk.addKeyListener(this);
        recargar.addKeyListener(this);
        salir.addKeyListener(this);
        registrar.addKeyListener(this);
        delete.addKeyListener(this);
        borrar_todo.addKeyListener(this);
        jButton1.addKeyListener(this);

//        setResizable(false);
        setTitle(tabla);
        setLocationRelativeTo(null);
    }

    public void cargar() {
        try {
            agrega = true;
            modelo = new DefaultTableModel();
            model_col = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            ResultSet rs = op.getConexion().GetColumnas(tabla);
            col = new Vector<>();
            Vector<String> aux;
            model_col.addColumn("Field");
            model_col.addColumn("Type");
            model_col.addColumn("Null");
            model_col.addColumn("Key");
            model_col.addColumn("Extra");
            jcombobox_columnas.removeAllItems();
            columnas_borrar.removeAllItems();
            columnas_pk.removeAllItems();
            columna_fk.removeAllItems();
            atributo_referencia.removeAllItems();
            tabla_referencia.removeAllItems();
            jcombobox_columnas.addItem("---------------");
            columnas_borrar.addItem("---------------");
            columnas_pk.addItem("---------------");
            columna_fk.addItem("---------------");
            atributo_referencia.addItem("---------------");
            tabla_referencia.addItem("---------------");

            while (rs.next()) {
                aux = new Vector<>();
                String field = rs.getString("Field");
                aux.add(field);
                aux.add(rs.getString("type"));
                aux.add(rs.getString("null"));
                aux.add(rs.getString("key"));
                aux.add(rs.getString("extra"));

                model_col.addRow(aux);
                col.add(field);
                modelo.addColumn(field);
                jcombobox_columnas.addItem(field);
                columnas_borrar.addItem(field);
                columnas_pk.addItem(field);
                columna_fk.addItem(field);
            }

            jTable1.setModel(modelo);
            tabla_columnas.setModel(model_col);
            op.llenarJtable(tabla, modelo, col);
            for (int i = 0; i < otras_tablas.size(); i++) {
                tabla_referencia.addItem(otras_tablas.get(i));
            }
            agrega = false;

            menu = new JPopupMenu();
            JMenuItem menuitem = new JMenuItem("borrar fila");
            menuitem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    inserta = true;
                    
                    int fila = jTable1.getSelectedRow();
                    Vector<String> datos = new Vector<>();
                    for (int i = 0; i < col.size(); i++) {
                        datos.add(jTable1.getValueAt(fila, i).toString());
                    }

                    try {
                        op.getConexion().BorrarFila(datos, col, tabla);
                        modelo.removeRow(fila);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al borrar fila", "Error", 0);
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                    }
                    inserta = false;
                }
            });
            menu.add(menuitem);
            jTable1.setComponentPopupMenu(menu);
            EventoJtable(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos", "Error", 0);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        }
    }

    private void EventoJtable(DefaultTableModel m) {
        m.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent tme) {
                if (inserta) {
                    return;
                }
                
                try {
                    int fila = tme.getFirstRow(), columna = tme.getColumn();
                    Vector<String> datos = new Vector<>();
                    for (int i = 0; i < modelo.getColumnCount(); i++) {
                        datos.add(modelo.getValueAt(fila, i).toString());
                    }
                    
                    op.getConexion().ActualizarFila(tabla, col, datos, modelo.getColumnName(columna), 
                            modelo.getValueAt(fila, columna).toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar los datos", "Error", 0);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un\nerror inesperado", "Error", 0);
                }
            }
        });
    }

    private void setEventoMouseClicked(javax.swing.JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    return;
                }
                java.awt.Point point = e.getPoint();
                int currentRow = tbl.rowAtPoint(point);
                tbl.setRowSelectionInterval(currentRow, currentRow);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        recargar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_columnas = new javax.swing.JTable();
        crear_col = new javax.swing.JButton();
        nombre_col = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tipo_datos = new javax.swing.JComboBox();
        boton_default = new javax.swing.JRadioButton();
        valor_default = new javax.swing.JTextField();
        no_nulo = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        longitud = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        columnas_borrar = new javax.swing.JComboBox();
        borrar_col = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        columnas_pk = new javax.swing.JComboBox();
        crear_pk = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        atributo_referencia = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        tabla_referencia = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        columna_fk = new javax.swing.JComboBox();
        crear_fk = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        registrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        registro = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jcombobox_columnas = new javax.swing.JComboBox();
        delete = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        borrar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        borrar_todo = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/DATOS ERROR.png"))); // NOI18N
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        recargar.setBackground(new java.awt.Color(101, 190, 106));
        recargar.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        recargar.setText("Actualizar");
        recargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recargarActionPerformed(evt);
            }
        });
        getContentPane().add(recargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, -1, -1));

        salir.setBackground(new java.awt.Color(255, 51, 51));
        salir.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        getContentPane().add(salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 460, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Consola");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 460, -1, -1));

        jPanel2.setBackground(new java.awt.Color(74, 83, 145));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_columnas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabla_columnas);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(298, 6, 566, 383));

        crear_col.setBackground(new java.awt.Color(156, 188, 250));
        crear_col.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        crear_col.setText("Crear Columna");
        crear_col.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_colActionPerformed(evt);
            }
        });
        jPanel2.add(crear_col, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 114, -1, -1));
        jPanel2.add(nombre_col, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 6, 126, -1));

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nombre");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jLabel6.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tipo");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 37, -1, -1));

        tipo_datos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-------------", "SMALLINT", "INT", "BIGINT", "FLOAT", "DOUBLE", "DECIMAL", "CHAR", "VARCHAR", "TEXT", "DATE", "TIME", "YEAR", "DATETIME", "TIMESTAMP", "BYNARY", "BLOB", "LONGBLOB" }));
        jPanel2.add(tipo_datos, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 34, -1, -1));

        boton_default.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        boton_default.setForeground(new java.awt.Color(255, 255, 255));
        boton_default.setText("Default");
        jPanel2.add(boton_default, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 75, -1, -1));
        jPanel2.add(valor_default, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 74, 100, -1));

        no_nulo.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        no_nulo.setForeground(new java.awt.Color(255, 255, 255));
        no_nulo.setText("Not null");
        jPanel2.add(no_nulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 75, -1, -1));

        jLabel7.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Longitud");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 37, -1, -1));
        jPanel2.add(longitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 34, 50, -1));

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 147, 280, 10));

        columnas_borrar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------" }));
        jPanel2.add(columnas_borrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 163, -1, -1));

        borrar_col.setBackground(new java.awt.Color(229, 22, 22));
        borrar_col.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        borrar_col.setForeground(new java.awt.Color(255, 255, 255));
        borrar_col.setText("Borrar columna");
        borrar_col.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar_colActionPerformed(evt);
            }
        });
        jPanel2.add(borrar_col, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 165, -1, -1));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 198, 280, 10));

        columnas_pk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------" }));
        jPanel2.add(columnas_pk, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 214, -1, -1));

        crear_pk.setBackground(new java.awt.Color(204, 226, 93));
        crear_pk.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        crear_pk.setText("Crear llave primaria");
        crear_pk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_pkActionPerformed(evt);
            }
        });
        jPanel2.add(crear_pk, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 216, -1, -1));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 249, 280, 10));

        jLabel8.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Atributo de referencia");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 305, -1, -1));

        atributo_referencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------" }));
        jPanel2.add(atributo_referencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 305, -1, -1));

        jLabel9.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tabla de referencia");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 265, -1, -1));

        tabla_referencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------" }));
        tabla_referencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabla_referenciaActionPerformed(evt);
            }
        });
        jPanel2.add(tabla_referencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 265, -1, -1));

        jLabel10.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Atributo llave");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 342, -1, -1));

        columna_fk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------" }));
        jPanel2.add(columna_fk, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 339, -1, -1));

        crear_fk.setBackground(new java.awt.Color(226, 155, 93));
        crear_fk.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        crear_fk.setText("Crear llave foranea");
        crear_fk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_fkActionPerformed(evt);
            }
        });
        jPanel2.add(crear_fk, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 379, -1, -1));

        jTabbedPane1.addTab("Editar Tabla", jPanel2);

        jPanel1.setBackground(new java.awt.Color(74, 83, 145));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Agregar nueva tupla");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 20, -1, -1));

        registrar.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        registrar.setText("registrar");
        registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarActionPerformed(evt);
            }
        });
        jPanel1.add(registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 122, -1, -1));

        jLabel2.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ingrese los datos ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 54, -1, -1));

        registro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registroActionPerformed(evt);
            }
        });
        jPanel1.add(registro, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 82, 231, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 164, 256, 10));

        jcombobox_columnas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------" }));
        jPanel1.add(jcombobox_columnas, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 192, 150, -1));

        delete.setBackground(new java.awt.Color(255, 0, 0));
        delete.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("Borrar");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 277, -1, -1));

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Filtrar por");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 195, -1, -1));
        jPanel1.add(borrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 237, 150, -1));

        jLabel4.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ingresar datos");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 240, 94, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 6, 584, 381));

        borrar_todo.setBackground(new java.awt.Color(255, 0, 0));
        borrar_todo.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        borrar_todo.setForeground(new java.awt.Color(255, 255, 255));
        borrar_todo.setText("BORRAR TODO");
        borrar_todo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar_todoActionPerformed(evt);
            }
        });
        jPanel1.add(borrar_todo, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 347, -1, -1));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 319, 256, 10));

        jLabel12.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("separados por comas");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 54, -1, -1));

        jTabbedPane1.addTab("Datos", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 870, 450));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar tabla.png"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registroActionPerformed

    }//GEN-LAST:event_registroActionPerformed

    private void registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarActionPerformed
        inserta = true;
        String h[] = registro.getText().split(",");
        try {
            op.getConexion().agregarRegistro(tabla, h);
            modelo.addRow(h);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar\nverifique los datos", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
        inserta = false;
    }//GEN-LAST:event_registrarActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        if (jcombobox_columnas.getSelectedIndex() == 0) {
            return;
        }
        inserta = true;

        try {
            String h = jcombobox_columnas.getSelectedItem().toString();
            op.getConexion().BorrarFila(borrar.getText(), h, tabla);

            for (int i = 0; i < modelo.getColumnCount(); i++) {
                if (h.equals(modelo.getColumnName(i))) {
                    for (int j = 0; j < modelo.getRowCount(); j++) {
                        if (modelo.getValueAt(j, i).toString().equals(borrar.getText())) {
                            modelo.removeRow(j);
                            j--;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar\nverifique los datos", "Error", 0);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un\nerror inesperado", "Error", 0);
        }
        inserta = false;
    }//GEN-LAST:event_deleteActionPerformed

    private void borrar_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar_todoActionPerformed
        int opcion = JOptionPane.showConfirmDialog(null, "Seguro que desea borrar todos\nlos datos de la tabla");
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        inserta = true;
        try {
            op.getConexion().BorrarAllDatosTabla(tabla);
            while (modelo.getRowCount() > 0) {
                modelo.removeRow(0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al borrar todo", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
        inserta = false;
    }//GEN-LAST:event_borrar_todoActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        ven_bd.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_salirActionPerformed

    private void crear_colActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_colActionPerformed
        if (tipo_datos.getSelectedIndex() == 0 || nombre_col.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error verifique los datos", "Error", 0);
            return;
        }
        setCursor(Cursor.WAIT_CURSOR);

        String lon = null, Def = null;
        if (!longitud.getText().isEmpty()) {
            lon = longitud.getText();
        }
        if (boton_default.isSelected()) {
            Def = valor_default.getText();
        }
        
        try {
            op.getConexion().AgregarColumna(tabla, tipo_datos.getSelectedItem().toString(), 
                    nombre_col.getText(), lon, Def, no_nulo.isSelected());
            cargar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error verifique los datos", "Error", 0);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado", "Error", 0);
        }
        setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_crear_colActionPerformed

    private void borrar_colActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar_colActionPerformed
        if (columnas_borrar.getSelectedIndex() == 0) {
            return;
        }

        setCursor(Cursor.WAIT_CURSOR);
        try {
            op.getConexion().BorrarColumna(tabla, columnas_borrar.getSelectedItem().toString());
            cargar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar", "Error", 0);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado", "Error", 0);
        }
        setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_borrar_colActionPerformed

    private void crear_pkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_pkActionPerformed
        if (columnas_pk.getSelectedIndex() == 0) {
            return;
        }

        setCursor(Cursor.WAIT_CURSOR);
        try {
            op.getConexion().CrearLlavePrimaria(tabla, columnas_pk.getSelectedItem().toString());
            cargar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear\nllave primaria", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado", "Error", 0);
        }
        setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_crear_pkActionPerformed

    private void tabla_referenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabla_referenciaActionPerformed
        if (agrega) {
            return;
        }
        if (tabla_referencia.getSelectedIndex() == 0) {
            atributo_referencia.removeAllItems();
            atributo_referencia.addItem("---------------");
            return;
        }
        setCursor(Cursor.WAIT_CURSOR);
        try {
            ResultSet res = op.getConexion().GetColumnas(tabla_referencia.getSelectedItem().toString());
            atributo_referencia.removeAllItems();
            atributo_referencia.addItem("---------------");
            while (res.next()) {
                atributo_referencia.addItem(res.getString("Field"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
        setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_tabla_referenciaActionPerformed

    private void crear_fkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_fkActionPerformed
        if (atributo_referencia.getSelectedIndex() == 0 || atributo_referencia.getSelectedIndex() == 0
                || tabla_referencia.getSelectedIndex() == 0) {
            return;
        }
        setCursor(Cursor.WAIT_CURSOR);
        try {
            op.getConexion().CrearLlaveForanea(tabla, columna_fk.getSelectedItem().toString(),
                    tabla_referencia.getSelectedItem().toString(), atributo_referencia.getSelectedItem().toString());
            cargar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear\nllave foranea", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
        setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_crear_fkActionPerformed

    private void recargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recargarActionPerformed
        setCursor(Cursor.WAIT_CURSOR);
        cargar();
        setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_recargarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        op.mostrarConsola();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(ventana_tabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventana_tabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventana_tabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventana_tabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox atributo_referencia;
    private javax.swing.JTextField borrar;
    private javax.swing.JButton borrar_col;
    private javax.swing.JButton borrar_todo;
    private javax.swing.JRadioButton boton_default;
    private javax.swing.JComboBox columna_fk;
    private javax.swing.JComboBox columnas_borrar;
    private javax.swing.JComboBox columnas_pk;
    private javax.swing.JButton crear_col;
    private javax.swing.JButton crear_fk;
    private javax.swing.JButton crear_pk;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox jcombobox_columnas;
    private javax.swing.JTextField longitud;
    private javax.swing.JRadioButton no_nulo;
    private javax.swing.JTextField nombre_col;
    private javax.swing.JButton recargar;
    private javax.swing.JButton registrar;
    private javax.swing.JTextField registro;
    private javax.swing.JButton salir;
    private javax.swing.JTable tabla_columnas;
    private javax.swing.JComboBox tabla_referencia;
    private javax.swing.JComboBox tipo_datos;
    private javax.swing.JTextField valor_default;
    // End of variables declaration//GEN-END:variables

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getComponent() == crear_col) {
                crear_colActionPerformed(null);
                return;
            }
            if (e.getComponent() == borrar_col) {
                borrar_colActionPerformed(null);
                return;
            }
            if (e.getComponent() == crear_pk) {
                crear_pkActionPerformed(null);
                return;
            }
            if (e.getComponent() == crear_fk) {
                crear_pkActionPerformed(null);
                return;
            }
            if (e.getComponent() == recargar) {
                recargarActionPerformed(null);
                return;
            }
            if (e.getComponent() == salir) {
                salirActionPerformed(null);
                return;
            }
            if (e.getComponent() == registrar) {
                registrarActionPerformed(null);
                return;
            }
            if (e.getComponent() == delete) {
                deleteActionPerformed(null);
                return;
            }
            if (e.getComponent() == jButton1) {
                jButton1ActionPerformed(null);
                return;
            }
            if (e.getComponent() == borrar_todo) {
                borrar_todoActionPerformed(null);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}

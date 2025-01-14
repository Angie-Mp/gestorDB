package vistas;

import clases.conexion;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class ventana_principal extends javax.swing.JFrame implements KeyListener {

    inicio ini;
    ventana_tabla vbd;
    private final operaciones op;
    private DefaultTreeModel modelo_arbol;
    private DefaultMutableTreeNode raiz;
    private DefaultTableModel model_bd;
    private JPopupMenu menu2, menu1, menu3;
    private Vector<DefaultMutableTreeNode> nodos;

    public ventana_principal(operaciones x3, inicio ini) {
        this.ini = ini;
        this.op = x3;
        initComponents();
        cargar();

        menu1 = new JPopupMenu();
        menu2 = new JPopupMenu();
        menu3 = new JPopupMenu();
        JMenuItem crearBD = new JMenuItem("Crear Base de Datos");
        crearBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String h = JOptionPane.showInputDialog(null, "Inserte nombre");
                if (h == null) {
                    return;
                }
                try {
                    op.getConexion().CrearDataBase(h);
                    cargar();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear\nla base de datos", "Error", 0);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                }
            }
        });
        JMenuItem agregar = new JMenuItem("crear tabla");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreePath path = jTree1.getSelectionPath();
                if (path.getPathCount() == 2) {
                    String h = JOptionPane.showInputDialog(null, "nombre de la tabla");
                    if (h == null) {
                        return;
                    }

                    try {
                        String h2 = path.getPathComponent(1).toString();
                        op.getConexion().SelectDataBase(h2);
                        op.getConexion().CrearTabla(h);
                        for (DefaultMutableTreeNode nodo : nodos) {
                            if (nodo.toString().equals(h2)) {
                                modelo_arbol.insertNodeInto(new DefaultMutableTreeNode(h), nodo, 0);
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al crear tabla", "Error", 0);
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                    }
                }
            }
        });
        JMenuItem borrar = new JMenuItem("borrar tabla");
        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {

                TreePath path = jTree1.getSelectionPath();
                if (path.getPathCount() == 3) {
                    if (JOptionPane.showConfirmDialog(null, "Seguro que quiere\nborrar la tabla")
                            != JOptionPane.YES_OPTION) {
                        return;
                    }
                    try {
                        System.out.println("de la base de datos " + path.getPathComponent(1).toString());
                        System.out.println("se borra la tabla " + path.getPathComponent(2).toString());
                        op.getConexion().SelectDataBase(path.getPathComponent(1).toString());
                        op.getConexion().BorrarTabla(path.getPathComponent(2).toString());
                        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (path.getLastPathComponent());
                        modelo_arbol.removeNodeFromParent(currentNode);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al borrar tabla", "Error", 0);
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un\nerror inesperado", "Error", 0);
                    }
                }
            }
        });
        JMenuItem borrarBD = new JMenuItem("borrar Base de Datos");
        borrarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreePath path = jTree1.getSelectionPath();
                String bd = path.getLastPathComponent().toString();
                int q = JOptionPane.showConfirmDialog(null, "Seguro que la desea borrar");

                if (q == JOptionPane.YES_OPTION) {
                    int q2 = JOptionPane.showConfirmDialog(null, "Segurisimo que desea borrar\nla base de datos\n" + bd);
                    if (q2 == JOptionPane.YES_OPTION) {
                        try {
                            op.getConexion().BorrarDataBase(bd);
                            cargar();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al borrar\nbase de datos", "Error", 0);
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                        }
                    }
                }
            }
        });
        menu1.add(crearBD);
        menu2.add(agregar);
        menu2.add(borrarBD);
        menu3.add(borrar);

        eventojtree();
        jButton1.addKeyListener(this);
        jButton2.addKeyListener(this);
        jTree1.addKeyListener(this);

        setTitle("Bases de datos");
        setLocationRelativeTo(null);
    }

    public void cargar() {
        try {
            raiz = new DefaultMutableTreeNode("Bases de Datos");
            modelo_arbol = new DefaultTreeModel(raiz);
            jTree1.setModel(modelo_arbol);

            nodos = new Vector<>();
            ResultSet res = op.getConexion().GetDataBases();
            String h;
            int pos = 0, pos2;

            while (res.next()) {
                h = res.getString(1);
                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(h);
                modelo_arbol.insertNodeInto(nuevo, raiz, pos);
                nodos.add(nuevo);
                pos++;
            }

            for (int i = 0; i < nodos.size(); i++) {
                try {
                    op.getConexion().SelectDataBase(nodos.get(i).toString());
                    ResultSet res2 = op.getConexion().GetTables();
                    pos2 = 0;
                    System.out.println("tabla " + nodos.get(i).toString());
                    while (res2.next()) {
                        String h2 = res2.getString("Tables_in_" + nodos.get(i).toString());
                        modelo_arbol.insertNodeInto(new DefaultMutableTreeNode(h2), nodos.get(i), pos2);
                        pos2++;
                    }
                } catch (Exception e) {
                }
            }

            
            
            model_bd = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model_bd.addColumn("Bases de datos");
            model_bd.addColumn("Longitud en MB");
            model_bd.addColumn("Número de tablas");
            ResultSet res2 = op.getConexion().TamanioDataBases();
            while (res2.next()) {
                Vector<String> aux = new Vector<>();
                aux.add(res2.getString(1));
                aux.add(res2.getString(2));
                aux.add(res2.getString(3));
                model_bd.addRow(aux);
            }
            Tabla_BD.setModel(model_bd);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos", "Error", 0);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        }
    }

    private void eventojtree() {
        jTree1.addMouseListener(new java.awt.event.MouseListener() {
            public void mouseClicked(MouseEvent me) {
                TreePath tp = jTree1.getPathForLocation(me.getX(), me.getY());
                if (tp == null) {
                    return;
                }

                if (me.getButton() == MouseEvent.BUTTON3) {
                    switch (tp.getPathCount()) {
                        case 1:
                            menu1.show(me.getComponent(), me.getX(), me.getY());
                            break;
                        case 2:
                            menu2.show(me.getComponent(), me.getX(), me.getY());
                            break;
                        case 3:
                            menu3.show(me.getComponent(), me.getX(), me.getY());
                            break;
                    }
                    return;
                }

                if (me.getClickCount() == 1 || me.getClickCount() > 2) {
                    return;
                }

                if (tp.getPathCount() == 3) {
                    llamar_ventana_tabla(tp);
                }
            }

            public void mousePressed(MouseEvent me) {
            }

            public void mouseReleased(MouseEvent me) {
            }

            public void mouseEntered(MouseEvent me) {
            }

            public void mouseExited(MouseEvent me) {
            }
        });
    }

    private void llamar_ventana_tabla(TreePath treepath) {
        try {
            String q = treepath.getPathComponent(1).toString();
            Vector<String> aux = op.getTablesDataBase(q);
            vbd = new ventana_tabla(op, ventana_principal.this, treepath.getLastPathComponent().toString(),
                    aux);
            setVisible(false);
            vbd.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
    }

    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla_BD = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setBackground(new java.awt.Color(255, 51, 51));
        jButton2.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jButton2.setText("SALIR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 440, -1, -1));

        jTree1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jTree1.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jTree1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, 270, 460));

        Tabla_BD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(Tabla_BD);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 69, 419, 350));

        jLabel2.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("INFORMACIÓN");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, 50));

        jButton1.setBackground(new java.awt.Color(102, 255, 102));
        jButton1.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jButton1.setText("RECARGAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 440, -1, -1));

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("CONSOLA");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 440, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bases de datos.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
        op.getConexion().desconectar();
        ini.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setCursor(Cursor.WAIT_CURSOR);
        cargar();
        setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        op.mostrarConsola();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(ventana_principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventana_principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventana_principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventana_principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new ventana_bd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_BD;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getComponent() == jButton1) {
                jButton1ActionPerformed(null);
                return;
            }
            if (e.getComponent() == jButton2) {
                jButton2ActionPerformed(null);
                return;
            }
            if (e.getComponent() == jTree1) {
                TreePath p = jTree1.getSelectionPath();
                if (p.getPathCount() == 3) {
                    llamar_ventana_tabla(p);
                } else {
                    if (jTree1.isExpanded(p)) {
                        jTree1.collapsePath(p);
                    } else {
                        jTree1.expandPath(p);
                    }
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_F2) {
            op.mostrarConsola();
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}

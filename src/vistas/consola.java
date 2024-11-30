package vistas;

import clases.conexion;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class consola extends javax.swing.JFrame implements KeyListener {

    private DefaultListModel<String> modelo_lista;
    private conexion x4;
    private JPopupMenu menu, menu2, menu3;

    public consola(conexion q) {
        x4 = q;
        initComponents();
        modelo_lista = new DefaultListModel<>();
        jList1.setModel(modelo_lista);
        menu = new JPopupMenu();
        menu2 = new JPopupMenu();
        menu3 = new JPopupMenu();

        JMenuItem item = new JMenuItem("Limpiar consola");
        item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                limpiar();
//                System.out.println("index seleccionado: " + jList1.getSelectedIndex());
            }
        });
        JMenuItem item2 = new JMenuItem("copiar");
        item2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent ae) {
//                System.out.println("index seleccionado: " + jList1.getSelectedIndex());
                if (jList1.getSelectedIndex() != -1) {
                    String texto = modelo_lista.get(jList1.getSelectedIndex());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection ss = new StringSelection(texto.substring(texto.indexOf(" ") + 1));
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, ss);
                }
            }
        });

        JMenuItem item3 = new JMenuItem("pegar");
        item3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                pegar(consulta);
            }
        });
        JMenuItem item4 = new JMenuItem("pegar");
        item4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                pegar(update);
            }
        });
        menu.add(item2);
        menu.add(item);
        jList1.setComponentPopupMenu(menu);

        menu2.add(item3);
        consulta.setComponentPopupMenu(menu2);

        menu3.add(item4);
        update.setComponentPopupMenu(menu3);

        setTitle("consola");
    }

    private void pegar(JTextField jtex) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trans = clipboard.getContents(null);

        if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                jtex.setText("" + trans.getTransferData(DataFlavor.stringFlavor));
            } catch (Exception ex) {
            }
        }
    }

    public void agregar(String c) {
        modelo_lista.addElement((modelo_lista.getSize() + 1) + "|  " + c);
    }

    public void limpiar() {
        modelo_lista.clear();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        consulta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        update = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 400, 110));

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Comandos ejecutados:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel2.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ejecutar consultas");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 20));
        getContentPane().add(consulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 220, -1));

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ejecutar update");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 20));
        getContentPane().add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 220, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, -1, -1));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("OK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CONSOLA.png"))); // NOI18N
        jLabel4.setToolTipText("");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            ResultSet res = x4.EjecutarConsulta(consulta.getText());
            while (res.next()) {
                int con = 0;
                String h = " | ";
                try {
                    while (true) {
                        con++;
                        h += res.getString(con) + "   | ";
                    }
                } catch (Exception e) {
                }
                agregar(h);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el comando", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String comando = update.getText();
        if (comando.length() > 4) {
            if (comando.trim().substring(0, 4).equalsIgnoreCase("use ")) {
                JOptionPane.showMessageDialog(null, "comando no permitido\npara evitar conflictos", "Error", 0);
                return;
            }
        }
        try {
            x4.EjecutarUpdate(comando);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el comando", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new consola().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField consulta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField update;
    // End of variables declaration//GEN-END:variables

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getComponent() == jButton1) {
                jButton1ActionPerformed(null);
            }
            if (e.getComponent() == jButton2) {
                jButton2ActionPerformed(null);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}

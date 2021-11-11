/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import control.ControlJSON;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import modelo.Gramatica;
import modelo.Produccion;

/**
 *
 * @author Juan David
 */
public class AnalizadorLL1 extends javax.swing.JFrame {

    public ArrayList<Gramatica> gramaticasRealizadas;
    public ArrayList<Gramatica> gramaticasQueCumplen;
    private String rutaArchivo;

    /**
     * Creates new form Principal
     */
    public AnalizadorLL1() {
        initComponents();
        gramaticasRealizadas = new ArrayList<>();
        gramaticasQueCumplen = new ArrayList<>();
        TPAnalizador.setVisible(false);

    }

    public String primerosGramatica(Gramatica gramatica) {
        String union = "";

        for (Produccion produccion : gramatica.getConjuntoProduccion()) {
            ArrayList<String> primeros = new ArrayList<>();
            primeros = gramatica.primerosProduccion(produccion, primeros);

            union += "\n" + "Prim(" + produccion.getIzquierda() + "):" + primeros;

        }
        return union;
    }

    public String siguientesGramatica(Gramatica gramatica) {
        String union = "";

        for (Produccion produccion : gramatica.getConjuntoProduccion()) {
            ArrayList<String> siguientes = new ArrayList<>();
            siguientes = gramatica.siguientesProduccion(produccion, siguientes);

            union += "\n" + "Sig(" + produccion.getIzquierda() + "):" + siguientes;

        }
        return union;
    }

    public String conjuntoPrediccionGramatica(Gramatica gramatica) {
        String union = "";
        int contadorFalsos = 0;
        String salida="";

        for (int j = 0; j < gramatica.getConjuntoProduccion().size(); j++) {
            Produccion produccion = gramatica.getConjuntoProduccion().get(j);

            boolean resultado = false;
            ArrayList<String> conjuntoPrediccion = new ArrayList<>();
            conjuntoPrediccion = gramatica.conjuntoPrediccionProduccion(produccion);

            ArrayList<String> prediccion = new ArrayList<>();

            resultado = verificarConjuntosPrediccion(conjuntoPrediccion, prediccion, j, resultado);
            if(resultado){
                salida="0";
            }else{
                salida="≠ 0";
            }
            union += "\n" + conjuntoPrediccion + " --> " + salida;
            if (resultado == false) {
                contadorFalsos++;
            }

        }
        if (contadorFalsos > 0) {
            lblEsLL1.setText("La gramática no pertenece a LL(1)");
            gramaticasRealizadas.add(gramatica);
        } else {
            lblEsLL1.setText("La gramática pertenece a LL(1)");
            gramaticasQueCumplen.add(gramatica);
            gramaticasRealizadas.add(gramatica);
        }
        return union;
    }

    private boolean verificarConjuntosPrediccion(ArrayList<String> conjuntoPrediccion, ArrayList<String> prediccion, int j, boolean resultado) {
        String[] temp;
        int cont;
        for (int i = 0; i < conjuntoPrediccion.size(); i++) {
            
            String conjunto = conjuntoPrediccion.get(i);
            
            conjunto = conjunto.replace("[", "");
            conjunto = conjunto.replace("]", "");
            conjunto = conjunto.replace(" ", "");
            temp = conjunto.split(",");
            
            for (String string : temp) {
                prediccion.add(string);
                
            }
            
        }
        for (String string : prediccion) {
            
            cont = 0;
            
            for (String string1 : prediccion) {
                
                if (string.equals(string1)) {
                    cont++;
                }
            }
            if (cont == 1) {
                resultado = true;
            } else {
                resultado = false;
                
                break;
            }
        }
        return resultado;
    }

    public Gramatica cargarConjuntoProduccionJSON() {

        ControlJSON controlJSON = new ControlJSON();
        String unir = "";
        Gramatica gra = controlJSON.leerGramatica(rutaArchivo);

        for (Produccion produccion : gra.getConjuntoProduccion()) {
            unir += "\n" + produccion;
        }
        txtConjuntoProduccion.setText(unir);

        return gra;
    }

    public void cargarVerificacionJSON() {
        ControlJSON controlJSON = new ControlJSON();

        Gramatica gramatica = controlJSON.leerGramatica(rutaArchivo);

        String primeros = primerosGramatica(gramatica);
        txtPrimeros.setText(primeros);

        String siguientes = siguientesGramatica(gramatica);
        txtSiguientes.setText(siguientes);

        String conjuntoPrediccion = conjuntoPrediccionGramatica(gramatica);
        txtConjuntoPrediccion.setText(conjuntoPrediccion);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TPAnalizador = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPrimeros = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSiguientes = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtConjuntoPrediccion = new javax.swing.JTextArea();
        txtSeleccionarJSON = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        TPConjunto = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtConjuntoProduccion = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        lblEsLL1 = new javax.swing.JLabel();
        btnGramaticasRealizadas = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(785, 590));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Estructura de Lenguajes");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/universidad-caldas.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 162, 162));

        txtPrimeros.setEditable(false);
        txtPrimeros.setBackground(new java.awt.Color(0, 0, 0));
        txtPrimeros.setColumns(20);
        txtPrimeros.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 18)); // NOI18N
        txtPrimeros.setForeground(new java.awt.Color(255, 255, 255));
        txtPrimeros.setRows(5);
        jScrollPane1.setViewportView(txtPrimeros);

        TPAnalizador.addTab("Primeros", jScrollPane1);

        txtSiguientes.setBackground(new java.awt.Color(0, 0, 0));
        txtSiguientes.setColumns(20);
        txtSiguientes.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 18)); // NOI18N
        txtSiguientes.setForeground(new java.awt.Color(255, 255, 255));
        txtSiguientes.setRows(5);
        jScrollPane2.setViewportView(txtSiguientes);

        TPAnalizador.addTab("Siguientes", jScrollPane2);

        txtConjuntoPrediccion.setBackground(new java.awt.Color(0, 0, 0));
        txtConjuntoPrediccion.setColumns(20);
        txtConjuntoPrediccion.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 18)); // NOI18N
        txtConjuntoPrediccion.setForeground(new java.awt.Color(255, 255, 255));
        txtConjuntoPrediccion.setRows(5);
        jScrollPane3.setViewportView(txtConjuntoPrediccion);

        TPAnalizador.addTab("Conjunto Prediccion", jScrollPane3);

        getContentPane().add(TPAnalizador, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 330, 220));
        TPAnalizador.getAccessibleContext().setAccessibleName("Primeros,\nSiguientes,\nConjunto Predicción,");

        txtSeleccionarJSON.setText("Seleccionar JSON");
        txtSeleccionarJSON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSeleccionarJSONActionPerformed(evt);
            }
        });
        getContentPane().add(txtSeleccionarJSON, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel2.setText("ANALIZADOR LL1");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        txtConjuntoProduccion.setEditable(false);
        txtConjuntoProduccion.setBackground(new java.awt.Color(0, 0, 0));
        txtConjuntoProduccion.setColumns(20);
        txtConjuntoProduccion.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 18)); // NOI18N
        txtConjuntoProduccion.setForeground(new java.awt.Color(255, 255, 255));
        txtConjuntoProduccion.setRows(5);
        jScrollPane4.setViewportView(txtConjuntoProduccion);

        TPConjunto.addTab("Conjunto producción", jScrollPane4);

        getContentPane().add(TPConjunto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 270, 220));

        jButton1.setText("Verificar LL1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 440, 120, -1));

        lblEsLL1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        getContentPane().add(lblEsLL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 440, 330, 30));

        btnGramaticasRealizadas.setText("Gramáticas Realizadas");
        btnGramaticasRealizadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGramaticasRealizadasActionPerformed(evt);
            }
        });
        getContentPane().add(btnGramaticasRealizadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jToggleButton1.setText("Gramáticas LL(1)");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSeleccionarJSONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSeleccionarJSONActionPerformed
        JFileChooser chooser = new JFileChooser();
   
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        
        Component parent = null;
        int returnVal = chooser.showSaveDialog(parent);

        
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            String nombreCarpeta = chooser.getSelectedFile().getParent();
            String nombreArchivo = chooser.getSelectedFile().getName();
            this.rutaArchivo = nombreCarpeta + "\\" + nombreArchivo;
            Gramatica gra = cargarConjuntoProduccionJSON();
            
        }


    }//GEN-LAST:event_txtSeleccionarJSONActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TPAnalizador.setVisible(true);
        cargarVerificacionJSON();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnGramaticasRealizadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGramaticasRealizadasActionPerformed
        ListarGramaticas listar=new ListarGramaticas(this);
        listar.setVisible(true);
        
    }//GEN-LAST:event_btnGramaticasRealizadasActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
       ListarGramaticasLL1 listar=new ListarGramaticasLL1(this);
        listar.setVisible(true);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AnalizadorLL1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnalizadorLL1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnalizadorLL1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnalizadorLL1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnalizadorLL1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TPAnalizador;
    private javax.swing.JTabbedPane TPConjunto;
    private javax.swing.JToggleButton btnGramaticasRealizadas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblEsLL1;
    private javax.swing.JTextArea txtConjuntoPrediccion;
    private javax.swing.JTextArea txtConjuntoProduccion;
    private javax.swing.JTextArea txtPrimeros;
    private javax.swing.JButton txtSeleccionarJSON;
    private javax.swing.JTextArea txtSiguientes;
    // End of variables declaration//GEN-END:variables

}

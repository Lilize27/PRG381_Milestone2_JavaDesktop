/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.CounselorController;
import model.Counselor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author Cosmo
 */
public class CounselorPanelDesign extends javax.swing.JFrame {
    private DefaultTableModel tableModel;
    private CounselorController controller = new CounselorController();
    
         /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(CounselorPanelDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
        new CounselorPanelDesign().setVisible(true);
        });
    }
     /**
     * Creates new form CounselorPanelDesign
     */
    public CounselorPanelDesign() {
        
        initComponents();
        customizeUI(); 
        initTable();
        loadCounselors();
        setupListeners();
    }
  private void customizeUI() {
    
    jPanel1.setBackground(new java.awt.Color(232, 245, 233)); // light green

    
    jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32));
    jLabel1.setForeground(new java.awt.Color(27, 94, 32)); // deep green

    
    jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
    jLabel2.setForeground(new java.awt.Color(56, 142, 60)); // medium green

   
    jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    jLabel4.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    jLabel5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    jLabel3.setForeground(new java.awt.Color(27, 94, 32)); // deep green
    jLabel4.setForeground(new java.awt.Color(27, 94, 32));
    jLabel5.setForeground(new java.awt.Color(27, 94, 32));

    
    txtName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    txtName.setBackground(java.awt.Color.WHITE);
    txtName.setForeground(new java.awt.Color(30, 30, 30));

    
    comboSpecialization.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    comboSpecialization.setBackground(java.awt.Color.WHITE);
    comboSpecialization.setForeground(new java.awt.Color(30, 30, 30));

   
    chkAvailable.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    chkAvailable.setForeground(new java.awt.Color(27, 94, 32)); 
   
    jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    jTable1.setRowHeight(20);
    jTable1.setGridColor(new java.awt.Color(200, 230, 201)); 
    jTable1.setSelectionBackground(new java.awt.Color(165, 214, 167)); 
    jTable1.setSelectionForeground(java.awt.Color.BLACK);
    jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
    jTable1.getTableHeader().setBackground(new java.awt.Color(76, 175, 80)); 
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);

    
    styleButton(btnAdd);
    styleButton(btnUpdate);
    styleButton(btnDelete);
    styleButton(btnRefresh);
}

  private void styleButton(javax.swing.JButton button) {
    button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
    button.setBackground(new java.awt.Color(76, 175, 80)); 
    button.setForeground(java.awt.Color.WHITE);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
}
    
    
    private void initTable() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Specialization", "Available"}, 0);
        jTable1.setModel(tableModel);
    }
    
    private void loadCounselors() {
        tableModel.setRowCount(0);
        ArrayList<Counselor> list = controller.getAllCounselors();
        for (Counselor c : list) {
            tableModel.addRow(new Object[]{
                c.getId(), c.getName(), c.getSpecialization(), c.getAvailability()
            });
        }
    }
    
    private void clearForm() {
        txtName.setText("");
        comboSpecialization.setSelectedIndex(0);
        chkAvailable.setSelected(false);
    }
    
    private void setupListeners() {
        btnAdd.addActionListener(e -> {
            String name = txtName.getText().trim();
            String spec = (String) comboSpecialization.getSelectedItem();
            boolean available = chkAvailable.isSelected();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter name.");
                return;
            }

            Counselor c = new Counselor(0, name, spec, available);
            controller.addCounselor(c);
            loadCounselors();
            clearForm();
        });
        
       btnUpdate.addActionListener(e -> {
            int row = jTable1.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row to update.");
                return;
            }

            int id = (int) tableModel.getValueAt(row, 0);
            String name = txtName.getText().trim();
            String spec = (String) comboSpecialization.getSelectedItem();
            boolean available = chkAvailable.isSelected();

            Counselor c = new Counselor(id, name, spec, available);
            controller.updateCounselor(c);
            loadCounselors();
            clearForm();
        });

        btnDelete.addActionListener(e -> {
            int row = jTable1.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row to delete.");
                return;
            }

            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this counselor?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.deleteCounselor(id);
                loadCounselors();
                clearForm();
            }
        });

        btnRefresh.addActionListener(e -> loadCounselors());

        jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    txtName.setText(tableModel.getValueAt(row, 1).toString());
                    comboSpecialization.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                    chkAvailable.setSelected(Boolean.parseBoolean(tableModel.getValueAt(row, 3).toString()));
                }
            }
        });
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        chkAvailable = new javax.swing.JCheckBox();
        comboSpecialization = new javax.swing.JComboBox<>();
        txtName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Counselor");

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Add new Counselor");

        jLabel3.setText("Counselor");

        jLabel4.setText("Availability");

        jLabel5.setText("Specialization ");

        btnAdd.setText("Add");

        chkAvailable.setText("Available");

        comboSpecialization.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Career Counseling", "Academic Support", "Mental Health", "Stress and Anxiety", "Peer Relationship Support", "Conflict Resolution" }));

        txtName.setText("Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkAvailable))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboSpecialization, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtName)))
                        .addGap(1, 1, 1)))
                .addGap(11, 11, 11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboSpecialization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(chkAvailable))
                .addGap(18, 18, 18)
                .addComponent(btnAdd)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnUpdate.setText("Update Counselor");

        btnDelete.setText("Delete Counselor");

        btnRefresh.setText("Refresh Table");

        jButton1.setLabel("Appointment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setLabel("Dashboard");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setLabel("Feedback");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(41, 41, 41)
                                .addComponent(jButton1)
                                .addGap(41, 41, 41)
                                .addComponent(jButton3))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnUpdate)
                        .addGap(29, 29, 29)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnDelete)))
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton1))))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnRefresh)
                    .addComponent(btnDelete))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel1.getAccessibleContext().setAccessibleName("Counselors");
        jButton2.getAccessibleContext().setAccessibleName("Dashboard");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       DashboardDesign app = new DashboardDesign();
        app.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AppointmentPanelDesign app = new AppointmentPanelDesign();
        app.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       FeedbackPanelDesign app = new FeedbackPanelDesign();
        app.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

      
    

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox chkAvailable;
    private javax.swing.JComboBox<String> comboSpecialization;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.FeedbackController;
import controller.AppointmentController;
import model.Feedback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author Cosmo
 */
public class FeedbackPanelDesign extends javax.swing.JFrame {
    private FeedbackController controller = new FeedbackController();
    private DefaultTableModel tableModel;

    public FeedbackPanelDesign() {
        initComponents();
        applyWellnessTheme();
         loadStudentsIntoComboBox();
        setupTable();
        loadFeedback();
        setupListeners();
    }
    
    private void applyWellnessTheme() {
    
    getContentPane().setBackground(new java.awt.Color(232, 245, 233)); // soft light green

    
    jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 25));
    jLabel1.setForeground(new java.awt.Color(27, 94, 32)); // deep green
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

   
    jPanel1.setBackground(new java.awt.Color(200, 230, 201)); // light green card

    
    jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
    jLabel2.setForeground(new java.awt.Color(56, 142, 60)); // medium green

    
    jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    jLabel4.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    jLabel5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    jLabel3.setForeground(new java.awt.Color(27, 94, 32)); // deep green
    jLabel4.setForeground(new java.awt.Color(27, 94, 32));
    jLabel5.setForeground(new java.awt.Color(27, 94, 32));

    
    cmdStudent.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    cmdStudent.setBackground(java.awt.Color.WHITE);
    cmdStudent.setForeground(new java.awt.Color(30, 30, 30));

   
    spnRating.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

   
    txtComments.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    txtComments.setBackground(java.awt.Color.WHITE);
    txtComments.setForeground(new java.awt.Color(30, 30, 30));

    
    tblFeedback.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    tblFeedback.setRowHeight(20);
    tblFeedback.setGridColor(new java.awt.Color(200, 230, 201)); // light green grid
    tblFeedback.setSelectionBackground(new java.awt.Color(165, 214, 167)); // soft green selection
    tblFeedback.setSelectionForeground(java.awt.Color.BLACK);
    tblFeedback.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
    tblFeedback.getTableHeader().setBackground(new java.awt.Color(76, 175, 80)); // standard green
    tblFeedback.getTableHeader().setForeground(java.awt.Color.WHITE);

    
    styleGreenButton(btnAdd);
    styleGreenButton(btnUpdate);
    styleGreenButton(btnDelete);
    styleGreenButton(btnRefresh);
    styleGreenButton(jButton1);
    styleGreenButton(jButton2);
    styleGreenButton(jButton3);
}
    
    
    private void styleGreenButton(javax.swing.JButton button) {
    button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
    button.setBackground(new java.awt.Color(76, 175, 80)); 
    button.setForeground(java.awt.Color.WHITE);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
}

    private void setupTable() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Student", "Rating", "Comments"}, 0);
        tblFeedback.setModel(tableModel);
    }
    private void loadStudentsIntoComboBox() {
    AppointmentController appointmentController = new AppointmentController();
    ArrayList<String> students = appointmentController.getAllStudentNames();
    
    cmdStudent.removeAllItems();
    for (String student : students) {
        cmdStudent.addItem(student);
    }
}

    private void loadFeedback() {
        tableModel.setRowCount(0);
        ArrayList<Feedback> list = controller.getAllFeedback();
        for (Feedback f : list) {
            tableModel.addRow(new Object[]{
                f.getId(), f.getStudent(), f.getRating(), f.getComments()
            });
        }
    }

    private void clearForm() {
        cmdStudent.setSelectedIndex(0);
        spnRating.setValue(1);
        txtComments.setText("");
        tblFeedback.clearSelection();
    }

    private void setupListeners() {
        btnAdd.addActionListener(e -> {
    String student = (String) cmdStudent.getSelectedItem();
    int rating = (int) spnRating.getValue();
    String comments = txtComments.getText().trim();

    if (student == null || student.isEmpty() || comments.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields must be filled in.");
        return;
    }

    
    Feedback f = new Feedback(0,student, rating, comments);

   
    controller.addFeedback(f);

   
    loadFeedback();

    clearForm();
});

        btnUpdate.addActionListener(e -> {
            int row = tblFeedback.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to update.");
                return;
            }

            int id = (int) tableModel.getValueAt(row, 0);
            String student = (String) cmdStudent.getSelectedItem();
            int rating = (int) spnRating.getValue();
            String comments = txtComments.getText().trim();

            Feedback feedback = new Feedback(id, student, rating, comments);
            controller.updateFeedback(feedback);
            loadFeedback();
            clearForm();
        });

        btnDelete.addActionListener(e -> {
            int row = tblFeedback.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
                return;
            }

            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this feedback?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.deleteFeedback(id);
                loadFeedback();
                clearForm();
            }
        });

        btnRefresh.addActionListener(e -> loadFeedback());

        tblFeedback.getSelectionModel().addListSelectionListener(e -> {
            int row = tblFeedback.getSelectedRow();
            if (row != -1) {
                cmdStudent.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                spnRating.setValue((int) tableModel.getValueAt(row, 2));
                txtComments.setText(tableModel.getValueAt(row, 3).toString());
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
        cmdStudent = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spnRating = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFeedback = new javax.swing.JTable();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 700));
        setSize(new java.awt.Dimension(800, 800));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Feedback");

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(700, 700));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 700));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Student feedback");

        cmdStudent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Student Name");

        jLabel4.setText("Rating");

        spnRating.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5, 1));

        jLabel5.setText("Comments");

        btnAdd.setText("Add Feedback");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtComments.setColumns(20);
        txtComments.setRows(5);
        jScrollPane1.setViewportView(txtComments);

        tblFeedback.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblFeedback);

        btnUpdate.setText("Update Feedback");

        btnDelete.setText("Delete Feedback");

        btnRefresh.setText("Refresh Table");

        jButton1.setText("Dashboard");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Counselors");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Appointments");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdStudent, 0, 220, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnRating)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 66, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(cmdStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(136, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    DashboardDesign app = new DashboardDesign();
    app.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    CounselorPanelDesign app = new CounselorPanelDesign();
    app.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    CounselorPanelDesign app = new CounselorPanelDesign();
    app.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

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
            java.util.logging.Logger.getLogger(FeedbackPanelDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FeedbackPanelDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FeedbackPanelDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FeedbackPanelDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FeedbackPanelDesign().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmdStudent;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spnRating;
    private javax.swing.JTable tblFeedback;
    private javax.swing.JTextArea txtComments;
    // End of variables declaration//GEN-END:variables
}

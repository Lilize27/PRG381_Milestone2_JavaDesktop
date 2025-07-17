package view;

import controller.AppointmentController;
import model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AppointmentPanelDesign extends JFrame {
    private JComboBox<String> cbCounsellor;
    private JTextField tfStudent, tfDate, tfTime;
    private JTable table;
    private DefaultTableModel model;
    private AppointmentController controller = new AppointmentController();

   public AppointmentPanelDesign() {
    setTitle("Appointment Manager");
    setSize(700, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10, 10));

    getContentPane().setBackground(new java.awt.Color(232, 245, 233)); // soft light green

JPanel formPanel = new JPanel(new GridBagLayout());
formPanel.setBackground(new java.awt.Color(232, 245, 233)); // match background

GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 5, 5, 5);
gbc.anchor = GridBagConstraints.WEST;


gbc.gridx = 0; gbc.gridy = 0;
JLabel lblStudent = new JLabel("Student:");
lblStudent.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
lblStudent.setForeground(new java.awt.Color(27, 94, 32)); // deep green
formPanel.add(lblStudent, gbc);

gbc.gridx = 1;
tfStudent = new JTextField(20);
formPanel.add(tfStudent, gbc);


gbc.gridx = 0; gbc.gridy = 1;
JLabel lblCounsellor = new JLabel("Counsellor:");
lblCounsellor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
lblCounsellor.setForeground(new java.awt.Color(27, 94, 32));
formPanel.add(lblCounsellor, gbc);

gbc.gridx = 1;
cbCounsellor = new JComboBox<>();
cbCounsellor.setPreferredSize(new Dimension(200, 25));
formPanel.add(cbCounsellor, gbc);


gbc.gridx = 0; gbc.gridy = 2;
JLabel lblDate = new JLabel("Date (yyyy-mm-dd):");
lblDate.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
lblDate.setForeground(new java.awt.Color(27, 94, 32));
formPanel.add(lblDate, gbc);

gbc.gridx = 1;
tfDate = new JTextField(15);
formPanel.add(tfDate, gbc);


gbc.gridx = 0; gbc.gridy = 3;
JLabel lblTime = new JLabel("Time (HH:MM):");
lblTime.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
lblTime.setForeground(new java.awt.Color(27, 94, 32));
formPanel.add(lblTime, gbc);

gbc.gridx = 1;
tfTime = new JTextField(10);
formPanel.add(tfTime, gbc);


gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
gbc.anchor = GridBagConstraints.CENTER;
JPanel buttonPanel = new JPanel(new FlowLayout());
buttonPanel.setBackground(new java.awt.Color(232, 245, 233)); // match background

JButton btnBook = createGreenButton("Book");
JButton btnUpdate = createGreenButton("Update");
JButton btnCancel = createGreenButton("Cancel");
buttonPanel.add(btnBook);
buttonPanel.add(btnUpdate);
buttonPanel.add(btnCancel);
formPanel.add(buttonPanel, gbc);

add(formPanel, BorderLayout.NORTH);


model = new DefaultTableModel(new String[]{"Student", "Counsellor", "Date", "Time", "Status"}, 0);
table = new JTable(model);
table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
table.setRowHeight(25);
JScrollPane pane = new JScrollPane(table);
add(pane, BorderLayout.CENTER);


JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
navPanel.setBackground(new java.awt.Color(232, 245, 233));

JButton btnDashboard = createGreenButton("Dashboard");
JButton btnCounsellor = createGreenButton("Counsellors");
JButton btnFeedback = createGreenButton("Feedback");
navPanel.add(btnDashboard);
navPanel.add(btnCounsellor);
navPanel.add(btnFeedback);
add(navPanel, BorderLayout.SOUTH);

btnBook.addActionListener(e -> bookAppointment());
btnCancel.addActionListener(e -> cancelAppointment());
btnUpdate.addActionListener(e -> updateAppointment());


btnDashboard.addActionListener(e -> {
    DashboardDesign app = new DashboardDesign();
    app.setVisible(true);
    this.dispose();
});

btnCounsellor.addActionListener(e -> {
    CounselorPanelDesign app = new CounselorPanelDesign();
    app.setVisible(true);
    this.dispose();
});

btnFeedback.addActionListener(e -> {
    FeedbackPanelDesign app = new FeedbackPanelDesign();
    app.setVisible(true);
    this.dispose();
});
loadCounsellors();
refreshTable();
}
   
   private JButton createGreenButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
    button.setBackground(new java.awt.Color(76, 175, 80)); // standard wellness green
    button.setForeground(java.awt.Color.WHITE);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    button.setPreferredSize(new Dimension(120, 35));
    return button;
}

    private void loadCounsellors() {
        for (String c : controller.getCounsellors()) {
            cbCounsellor.addItem(c);
        }
    }

    private void refreshTable() {
    model.setRowCount(0);
    for (Appointment app : controller.getAppointments()) {
        model.addRow(new Object[]{
            app.getID(), 
            app.getStudentName(),
            app.getCounsellorName(),
            app.getDate(),
            app.getTime(),
            app.getStatus()
        });
    }
}

    private void bookAppointment() {
        
        String student = tfStudent.getText().trim();
        String counsellor = (String) cbCounsellor.getSelectedItem();
        String date = tfDate.getText().trim();
        String time = tfTime.getText().trim();

        if (student.isEmpty() || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        Appointment app = new Appointment(0,student, counsellor, date, time, "Scheduled");
        if (controller.addAppointment(app)) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Appointment booked.");
        }
    }

    private void cancelAppointment() {
    int row = table.getSelectedRow();
    if (row >= 0) {
        int id = (int) model.getValueAt(row, 0); 
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this Appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
               if (controller.deleteAppointment(id)) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Appointment cancelled.");
               }
            }
        
    }
}

    private void updateAppointment() {
    int row = table.getSelectedRow();
    if (row >= 0) {
        int id = (int) model.getValueAt(row, 0); 
        String student = (String) model.getValueAt(row, 1); 
        String counsellor = (String) cbCounsellor.getSelectedItem();
        String date = (String) model.getValueAt(row, 3);
        String time = tfTime.getText().trim();

        Appointment updated = new Appointment(id, student, counsellor, date, time, "Rescheduled");
        if (controller.updateAppointment(updated)) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Appointment updated.");
        }
    }
}

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        DashboardDesign app = new DashboardDesign();
        app.setVisible(true);
        this.dispose();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        CounselorPanelDesign app = new CounselorPanelDesign();
        app.setVisible(true);
        this.dispose();
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        FeedbackPanelDesign app = new FeedbackPanelDesign();
        app.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        new AppointmentPanelDesign().setVisible(true);
    }
}

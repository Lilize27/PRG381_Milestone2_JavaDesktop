/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Appointment {
    private int id;
    private String studentName;
    private String counsellorName;
    private String date;
    private String time;
    private String status;

    public Appointment(int id,String studentName, String counsellorName, String date, String time, String status) {
        this.id=id;
        this.studentName = studentName;
        this.counsellorName = counsellorName;
        this.date = date;
        this.time = time;
        this.status = status;
    }
public int getID(){
    return id;
}
    public String getStudentName() {
        return studentName;
    }

    public String getCounsellorName() {
        return counsellorName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
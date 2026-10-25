package main.java.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int id, idDoctor;
    private String nationalIdPatient, reason, status;
    private LocalDate dateAppointment;
    private LocalTime time;

    public Appointment() {}

    public Appointment(int id, int idDoctor, String nationalIdPatient, LocalDate dateAppointment, LocalTime time, String reason, String status) {
        this.id = id;
        this.idDoctor = idDoctor;
        this.nationalIdPatient = nationalIdPatient;
        this.dateAppointment = dateAppointment;
        this.time = time;
        this.reason = reason;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdDoctor() { return idDoctor; }
    public void setDoctorId(int idDoctor) { this.idDoctor = idDoctor; }
    public String getNationalIdPatient() { return nationalIdPatient; }
    public void setNationalIdPatient(String nationalIdPatient) { this.nationalIdPatient = nationalIdPatient; }
    public LocalDate getDateAppointment() { return dateAppointment; }
    public void setDateAppointment(LocalDate dateAppointment) { this.dateAppointment = dateAppointment; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
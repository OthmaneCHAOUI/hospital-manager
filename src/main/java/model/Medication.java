package model;

import java.time.LocalDate;

public class Medication {

    /**
     * ============================================
     * Attributes
     * ============================================
     */
    
    private int id, idDoctor, duration;
    private String nationalIdPatient, name, description;
    private LocalDate startDate;

    /**
     * ============================================
     * Constructors
     * ============================================
     */
    
    public Medication() {
    }

    public Medication(int id, int idDoctor, String nationalIdPatient, String name, LocalDate startDate, int duration,
            String description) {
        this.id = id;
        this.idDoctor = idDoctor;
        this.nationalIdPatient = nationalIdPatient;
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
        this.description = description;
    }

    public Medication(String name, LocalDate startDate, int duration, String description) {
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
        this.description = description;
    }

    public Medication(int id, String name, LocalDate startDate, int duration, String description) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
        this.description = description;
    }

    /**
     * ============================================
     * Getters and Setters
     * ============================================
     */
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setDoctorId(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNationalIdPatient() {
        return nationalIdPatient;
    }

    public void setNationalIdPatient(String nationalIdPatient) {
        this.nationalIdPatient = nationalIdPatient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
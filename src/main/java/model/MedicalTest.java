package main.java.model;

import java.time.LocalDate;

public class MedicalTest {
    private int id, idDoctor;
    private String nationalIdPatient, testType, result, doctorType, status;
    private LocalDate testDate;

    public MedicalTest() {
    }

    public MedicalTest(int id, int idDoctor, String nationalIdPatient, String testType, String result,
            LocalDate testDate, String doctorType, String status) {
        this.id = id;
        this.idDoctor = idDoctor;
        this.nationalIdPatient = nationalIdPatient;
        this.testType = testType;
        this.result = result;
        this.testDate = testDate;
        this.doctorType = doctorType;
        this.status = status;
    }

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

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
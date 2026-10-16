package model;

import java.time.LocalDate;

public class DoctorPatient {
    private int id, idDoctor;
    private String name, firstName, nationalId, city, phone, address, gender;
    private LocalDate birthDate;

    public DoctorPatient() {}

    public DoctorPatient(int id, int idDoctor, String name, String firstName, LocalDate birthDate, String nationalId, String city, String phone, String address, String gender) {
        this.id = id;
        this.idDoctor = idDoctor;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.nationalId = nationalId;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }

    public DoctorPatient(String name, String firstName, LocalDate birthDate,String city, String phone, String address, String gender) {
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }

    public DoctorPatient(String name, String firstName, LocalDate birthDate, String nationalId, String city, String phone, String address, String gender) {
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.nationalId = nationalId;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdDoctor() { return idDoctor; }
    public void setDoctorId(int idDoctor) { this.idDoctor = idDoctor; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public LocalDate getDateNaissance() { return birthDate; }
    public void setDateNaissance(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}
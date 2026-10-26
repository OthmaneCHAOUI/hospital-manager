package model;

import java.time.LocalDate;

/**
 * Represents a patient record and the personal information tied to it.
 */
public class Patient {
    private int id;
    private String name, firstName, nationalId, password, city, phone, address, gender, accountCreated, email;
    private LocalDate birthDate;

    public Patient() {
    }

    public Patient(String nationalId, String password) {
        this.nationalId = nationalId;
        this.password = password;
    }

    public Patient(String name, String firstName, String nationalId, String password) {
        this.name = name;
        this.firstName = firstName;
        this.nationalId = nationalId;
        this.password = password;
    }

    public Patient(String name, String firstName, String nationalId, String password, LocalDate birthDate) {
        this.name = name;
        this.firstName = firstName;
        this.nationalId = nationalId;
        this.password = password;
        this.birthDate = birthDate;
    }

    public Patient(String name, String firstName, String nationalId, String password, String city, String phone,
            String address, String gender, String accountCreated, String email, LocalDate birthDate) {
        this.name = name;
        this.firstName = firstName;
        this.nationalId = nationalId;
        this.password = password;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.accountCreated = accountCreated;
        this.email = email;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateNaissance() {
        return birthDate;
    }

    public void setDateNaissance(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompteCree() {
        return accountCreated;
    }

    public void setCompteCree(String accountCreated) {
        this.accountCreated = accountCreated;
    }

    public int getAge() {
        if (birthDate == null)
            return 0;
        return java.time.Period.between(birthDate, java.time.LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        if (birthDate != null) {
            this.birthDate = java.time.LocalDate.now().minusYears(age);
        }
    }
}
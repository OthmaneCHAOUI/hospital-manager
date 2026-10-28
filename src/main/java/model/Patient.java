package model;

import java.time.LocalDate;

/**
 * Represents a patient record and the personal information tied to it.
 */
public class Patient {
    /**
     * =============================================================
     * Attributes
     * =============================================================
     */
    
    private int id;
    private String name, firstName, nationalId, password, city, phone, address, gender, accountCreated, email;
    private LocalDate birthDate;

    /**
     * =============================================================
     * Constructors
     * =============================================================
     */
    
    public Patient() {
    }

    /**
     * Constructor for creating a Patient object with only nationalId and password.
     * @param nationalId The national ID of the patient.
     * @param password The password of the patient.
     */
    public Patient(String nationalId, String password) {
        this.nationalId = nationalId;
        this.password = password;
    }

    /**
     * Constructor for creating a Patient object with name, firstName, nationalId, and password.
     * @param name The last name of the patient.
     * @param firstName The first name of the patient.
     * @param nationalId The national ID of the patient.
     * @param password The password of the patient.
     */
    public Patient(String name, String firstName, String nationalId, String password) {
        this.name = name;
        this.firstName = firstName;
        this.nationalId = nationalId;
        this.password = password;
    }

    /**
     * Constructor for creating a Patient object with name, firstName, nationalId, password, and birthDate.
     * @param name The last name of the patient.
     * @param firstName The first name of the patient.
     * @param nationalId The national ID of the patient.
     * @param password The password of the patient.
     * @param birthDate The birth date of the patient.
     */
    public Patient(String name, String firstName, String nationalId, String password, LocalDate birthDate) {
        this.name = name;
        this.firstName = firstName;
        this.nationalId = nationalId;
        this.password = password;
        this.birthDate = birthDate;
    }

    /**
     * Constructor for creating a Patient object with all attributes.
     * @param name The last name of the patient.
     * @param firstName The first name of the patient.
     * @param nationalId The national ID of the patient.
     * @param password The password of the patient.
     * @param city The city where the patient resides.
     * @param phone The phone number of the patient.
     * @param address The address of the patient.
     * @param gender The gender of the patient.
     * @param accountCreated The date when the patient's account was created.
     * @param email The email address of the patient.
     * @param birthDate The birth date of the patient.
     */
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

    /**
     * =============================================================
     * Getters and Setters
     * =============================================================
     */
    
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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

    public String getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(String accountCreated) {
        this.accountCreated = accountCreated;
    }

    /**
     * =============================================================
     * Additional Methods
     * =============================================================
     */
    
    /**
     * Gets the age of the patient.
     * @return The age of the patient.
     */
    // public int getAge() {
    //     if (birthDate == null)
    //         return 0;
    //     return java.time.Period.between(birthDate, java.time.LocalDate.now()).getYears();
    // }

    /**
     * Sets the age of the patient by calculating the birth date based on the provided age.
     * If the provided age is negative, the birth date will not be updated.
     * If the birth date is not set, this method will not set it and will return without making any changes.
     * @param age The age of the patient to set. The birth date will be calculated as the current date minus the provided age in years.
     * @throws IllegalArgumentException if the provided age is negative.
     */
    // public void setAge(int age) {
    //     if (age < 0) {
    //         throw new IllegalArgumentException("Age cannot be negative.");
    //     }
        
    //     if (birthDate != null) {
    //         this.birthDate = java.time.LocalDate.now().minusYears(age);
    //     }
    // }
}
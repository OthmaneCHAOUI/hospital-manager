package model;

/**
 * Represents a doctor profile in the medical management application.
 */
public class Doctor {
    /**
     * =============================================================
     * Attributes
     * =============================================================
     */
    
    private int id;
    private String name;
    private String firstName;
    private String nationalId;
    private String specialty;
    private String clinicName;
    private String phone;
    private String clinicAddress;
    private String password;

    /**
     * =============================================================
     * Constructors
     * =============================================================
     */
    
    /**
     * No-args constructor for creating an empty Doctor object.
     */
    public Doctor() {
    }

    /**
     * Constructor for creating a Doctor object with basic information.
     * @param id The ID of the doctor.
     * @param name The name of the doctor.
     * @param firstName The first name of the doctor.
     * @param specialty The specialty of the doctor.
     */
    public Doctor(int id, String name, String firstName, String specialty) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.specialty = specialty;
    }

    /**
     * Constructor for creating a Doctor object with all information.
     * @param name The name of the doctor.
     * @param firstName The first name of the doctor.
     * @param nationalId The national ID of the doctor.
     * @param specialty The specialty of the doctor.
     * @param clinicName The name of the clinic where the doctor works.
     * @param phone The phone number of the doctor.
     * @param clinicAddress The address of the clinic where the doctor works.
     * @param password The password of the doctor.
     */
    public Doctor(String name, String firstName, String nationalId, String specialty, String clinicName, String phone,
            String clinicAddress, String password) {
        this.name = name;
        this.firstName = firstName;
        this.nationalId = nationalId;
        this.specialty = specialty;
        this.clinicName = clinicName;
        this.phone = phone;
        this.clinicAddress = clinicAddress;
        this.password = password;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package Models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The {@code Patient} class represents a patient in the hospital management system.
 * It extends the {@code User} class and includes additional fields specific to patients,
 * such as blood type, contact information, date of birth, and medical records.
 * This class implements {@code Serializable} to allow data persistence.
 */

public class Patient extends User implements Serializable {

    /**
     * The blood type of the patient.
     */

    private String bloodType;

    /**
     * The phone number of the patient.
     */

    private String phoneNumber;

    /**
     * The email address of the patient.
     */

    private String emailAddress;

    /**
     * The date of birth of the patient.
     */

    private LocalDate dateOfBirth;

    /**
     * The medical record associated with the patient.
     */

    private MedicalRecord medicalRecord;

    /**
     * Constructs a {@code Patient} object with the specified details.
     *
     * @param userID       the unique identifier of the patient.
     * @param name         the name of the patient.
     * @param password     the password for the patient's account.
     * @param role         the role of the user (e.g., "patient").
     * @param gender       the gender of the patient.
     * @param age          the age of the patient.
     * @param bloodType    the blood type of the patient.
     * @param phoneNumber  the phone number of the patient.
     * @param emailAddress the email address of the patient.
     * @param dateOfBirth  the date of birth of the patient.
     */

    public Patient(String userID, String name, String password, String salt, String role, String gender, int age, String bloodType,
                   String phoneNumber, String emailAddress, LocalDate dateOfBirth) {
        super(userID, name, password, salt, role, gender, age);
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the blood type of the patient.
     *
     * @return the blood type of the patient.
     */

    public String getBloodType() {
        return bloodType;
    }

    /**
     * Sets the blood type of the patient.
     *
     * @param bloodType the new blood type of the patient.
     */

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Gets the phone number of the patient.
     *
     * @return the phone number of the patient.
     */

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the patient.
     *
     * @param phoneNumber the new phone number of the patient.
     */

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email address of the patient.
     *
     * @return the email address of the patient.
     */

    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address of the patient.
     *
     * @param emailAddress the new email address of the patient.
     */

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the date of birth of the patient.
     *
     * @return the date of birth of the patient.
     */

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the patient.
     *
     * @param dateOfBirth the new date of birth of the patient.
     */

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the medical record associated with the patient.
     *
     * @return the medical record of the patient.
     */

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Sets the medical record for the patient.
     *
     * @param medicalRecord the new medical record for the patient.
     */

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    /**
     * Returns a string representation of the patient.
     * The format includes user details, blood type, phone number, email address, and date of birth.
     *
     * @return a string representation of the patient.
     */
    
    @Override
    public String toString() {
        return super.toString() + "," + bloodType + "," + phoneNumber + "," + emailAddress + "," + String.valueOf(dateOfBirth);
    }
}
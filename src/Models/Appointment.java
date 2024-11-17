package Models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The {@code Appointment} class represents a medical appointment in the system.
 * It stores details such as the appointment ID, patient ID, doctor ID, appointment time, status, and outcome record.
 * This class implements {@code Serializable} for enabling data persistence.
 */

public class Appointment implements Serializable {

    /**
     * The unique identifier for the appointment.
     */

    private String appointmentID;

    /**
     * The ID of the patient associated with the appointment.
     */

    private String patientID;

    /**
     * The ID of the doctor associated with the appointment.
     */

    private String doctorID;

    /**
     * The scheduled date and time of the appointment.
     */

    private LocalDateTime appointmentTime;

    /**
     * The current status of the appointment (e.g., "Confirmed", "Cancelled", "Completed").
     */

    private String status;

    /**
     * The outcome record associated with the appointment, if applicable.
     */

    private AppointmentOutcomeRecord outcomeRecord;

    /**
     * Default constructor for {@code Appointment}.
     * Initializes an empty appointment object.
     */

    public Appointment() {}

    /**
     * Constructs a new {@code Appointment} object with the specified details.
     *
     * @param appointmentID   the unique identifier for the appointment.
     * @param patientID       the ID of the patient associated with the appointment.
     * @param doctorID        the ID of the doctor associated with the appointment.
     * @param appointmentTime the scheduled date and time of the appointment.
     * @param status          the current status of the appointment.
     * @param outcomeRecord   the outcome record of the appointment, if applicable.
     */

    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime appointmentTime, String status, AppointmentOutcomeRecord outcomeRecord) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.outcomeRecord = outcomeRecord;
    }

    /**
     * Gets the unique identifier of the appointment.
     *
     * @return the appointment ID.
     */

    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the unique identifier of the appointment.
     *
     * @param appointmentID the appointment ID to set.
     */

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the ID of the patient associated with the appointment.
     *
     * @return the patient ID.
     */

    public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the ID of the patient associated with the appointment.
     *
     * @param patientID the patient ID to set.
     */

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the ID of the doctor associated with the appointment.
     *
     * @return the doctor ID.
     */

    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the ID of the doctor associated with the appointment.
     *
     * @param doctorID the doctor ID to set.
     */

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Gets the scheduled date and time of the appointment.
     *
     * @return the appointment time.
     */

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Sets the scheduled date and time of the appointment.
     *
     * @param appointmentTime the new appointment time to set.
     */

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Gets the current status of the appointment.
     *
     * @return the appointment status.
     */

    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the appointment.
     *
     * @param status the new status to set (e.g., "Confirmed", "Cancelled").
     */

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the outcome record associated with the appointment, if available.
     *
     * @return the appointment outcome record.
     */

    public AppointmentOutcomeRecord getOutcomeRecord() {
        return outcomeRecord;
    }

    /**
     * Sets the outcome record for the appointment.
     *
     * @param outcomeRecord the appointment outcome record to set.
     */
    
    public void setOutcomeRecord(AppointmentOutcomeRecord outcomeRecord) {
        this.outcomeRecord = outcomeRecord;
    }
}
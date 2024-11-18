package Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code AppointmentOutcomeRecord} class represents the outcome of a medical appointment.
 * It contains details about the appointment's ID, date, service type, prescriptions, and consultation notes.
 * This class implements {@code Serializable} for data persistence.
 */

public class AppointmentOutcomeRecord implements Serializable {

    /**
     * The unique identifier for the appointment associated with this record.
     */

    private String appointmentId;

    /**
     * The date of the appointment.
     */

    private LocalDate date;

    /**
     * The type of service provided during the appointment (e.g., "Consultation", "Surgery").
     */

    private String serviceType;

    /**
     * The list of prescriptions issued during the appointment.
     */

    private List<Prescription> prescriptions;

    /**
     * The consultation notes recorded during the appointment.
     */

    private String consultaionNotes;

    /**
     * Default constructor for {@code AppointmentOutcomeRecord}.
     * Initializes an empty outcome record.
     */

    public AppointmentOutcomeRecord() {
    }

    /**
     * Constructs an {@code AppointmentOutcomeRecord} with the specified details.
     *
     * @param appointmentId   the unique identifier of the appointment.
     * @param date            the date of the appointment.
     * @param serviceType     the type of service provided during the appointment.
     * @param prescriptions   the list of prescriptions issued.
     * @param consultaionNotes the consultation notes recorded during the appointment.
     */

    public AppointmentOutcomeRecord(String appointmentId, LocalDate date, String serviceType, List<Prescription> prescriptions, String consultaionNotes) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.serviceType = serviceType;
        this.prescriptions = prescriptions;
        this.consultaionNotes = consultaionNotes;
    }

    /**
     * Gets the unique identifier of the appointment.
     *
     * @return the appointment ID.
     */

    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the unique identifier of the appointment.
     *
     * @param appointmentId the appointment ID to set.
     */

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return the appointment date.
     */

    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param date the appointment date to set.
     */

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the type of service provided during the appointment.
     *
     * @return the service type.
     */

    public String getServiceType() {
        return serviceType;
    }

    /**
     * Sets the type of service provided during the appointment.
     *
     * @param serviceType the service type to set.
     */

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Gets the list of prescriptions issued during the appointment.
     *
     * @return the list of prescriptions.
     */

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Sets the list of prescriptions issued during the appointment.
     *
     * @param prescriptions the list of prescriptions to set.
     */

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    /**
     * Gets the consultation notes recorded during the appointment.
     *
     * @return the consultation notes.
     */

    public String getConsultationNotes() {
        return consultaionNotes;
    }

    /**
     * Sets the consultation notes recorded during the appointment.
     *
     * @param consultaionNotes the consultation notes to set.
     */
    
    public void setConsultaionNotes(String consultaionNotes) {
        this.consultaionNotes = consultaionNotes;
    }
}
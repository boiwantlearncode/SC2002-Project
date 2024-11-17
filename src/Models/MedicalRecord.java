package Models;

import java.io.Serializable;
import java.util.List;

/**
 * The {@code MedicalRecord} class represents a patient's medical record.
 * It includes the patient's ID, a list of past diagnoses, and a list of past treatments.
 * This class implements {@code Serializable} for data persistence.
 */

public class MedicalRecord implements Serializable {

    /**
     * The unique ID of the patient associated with this medical record.
     */

    private String patientID;

    /**
     * A list of past diagnoses for the patient.
     */

    private List<String> pastDiagnosis;

    /**
     * A list of past treatments for the patient.
     */

    private List<String> pastTreatment;

    /**
     * Constructs a {@code MedicalRecord} object with the specified patient ID,
     * list of past diagnoses, and list of past treatments.
     *
     * @param patientID the unique ID of the patient.
     * @param pastDiagnosis a list of past diagnoses for the patient.
     * @param pastTreatment a list of past treatments for the patient.
     */

    public MedicalRecord(String patientID, List<String> pastDiagnosis, List<String> pastTreatment) {
        this.patientID = patientID;
        this.pastDiagnosis = pastDiagnosis;
        this.pastTreatment = pastTreatment;
    }

    /**
     * Default constructor for {@code MedicalRecord}.
     * Initializes an empty medical record.
     */

    public MedicalRecord() {
    }

    /**
     * Gets the patient ID associated with this medical record.
     *
     * @return the patient ID.
     */

    public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient ID for this medical record.
     *
     * @param patientID the unique ID of the patient to set.
     */

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the list of past diagnoses for the patient.
     *
     * @return a list of past diagnoses.
     */

    public List<String> getPastDiagnosis() {
        return pastDiagnosis;
    }

    /**
     * Sets the list of past diagnoses for the patient.
     *
     * @param pastDiagnosis a list of past diagnoses to set.
     */

    public void setPastDiagnosis(List<String> pastDiagnosis) {
        this.pastDiagnosis = pastDiagnosis;
    }

    /**
     * Gets the list of past treatments for the patient.
     *
     * @return a list of past treatments.
     */

    public List<String> getPastTreatment() {
        return pastTreatment;
    }

    /**
     * Sets the list of past treatments for the patient.
     *
     * @param pastTreatment a list of past treatments to set.
     */
    
    public void setPastTreatment(List<String> pastTreatment) {
        this.pastTreatment = pastTreatment;
    }
}
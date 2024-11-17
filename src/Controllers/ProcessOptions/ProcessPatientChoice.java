package Controllers.ProcessOptions;

import Models.Patient;
import Views.Patient.PatientView;
import Views.Patient.PatientAppointmentView;

import java.io.IOException;

/**
 * The {@code ProcessPatientChoice} class handles the choices made by a patient in the hospital management system.
 * It provides functionality for patients to manage their medical records, appointments, and personal information.
 */

public class ProcessPatientChoice implements ProcessUserChoice<Patient>{

    /**
     * The view layer responsible for displaying and updating the patient's personal and medical information.
     */
    
    private final PatientView patientView = new PatientView();

    /**
     * The view layer responsible for displaying and managing the patient's appointment-related options.
     */

    private final PatientAppointmentView patientAppointmentView = new PatientAppointmentView();

    /**
     * Processes the patient's choice by invoking the corresponding functionality for the selected option.
     *
     * @param patient the {@code Patient} who is making the choice.
     * @param choice  the choice made by the patient, represented as a string.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if data cannot be loaded properly.
     */

    @Override
    public void processUserChoice(Patient patient, String choice) throws IOException, ClassNotFoundException {

        switch (choice) {
            case "1": patientView.viewMedicalRecord(patient); break;
            case "2": patientView.updatePersonalInfo(patient); break;
            case "3": patientAppointmentView.viewAvailableAppointmentSlots(); break;
            case "4": patientAppointmentView.scheduleAppointment(patient); break;
            case "5": patientAppointmentView.rescheduleAppointment(patient); break;
            case "6": patientAppointmentView.cancelAppointment(patient); break;
            case "7": patientAppointmentView.viewScheduledAppointments(patient); break;
            case "8": patientAppointmentView.viewPastAppointmentOutcomeRecords(patient); break;
            case "9": patientView.changePassword(patient); break;
            default: System.out.println("Invalid choice. Please try again.");
        }
    }
}

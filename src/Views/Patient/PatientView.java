package Views.Patient;

import Controllers.AuthenticationController.PasswordController.PasswordController;
import Controllers.PatientController.PatientController;
import DataManager.AppointmentsRepo;
import Models.Appointment;
import Models.AppointmentOutcomeRecord;
import Models.Patient;
import Models.Prescription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The {@code PatientView} class provides methods for patients to interact with their medical records
 * and personal information through a user interface.
 */

public class PatientView {

    /**
     * Scanner object for handling user input.
     */

    static final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a new {@code PatientView}.
     */

    public PatientView() {
    }

    /**
     * Displays the patient's medical record, including personal details, past diagnoses, and treatments.
     *
     * @param patient the {@code Patient} whose medical record is to be viewed.
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
     
    public void viewMedicalRecord(Patient patient) throws ClassNotFoundException, IOException {
        System.out.println("Medical Record for " + patient.getName());
        System.out.println("Patient ID: " + patient.getUserID());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Blood Type: " + patient.getBloodType());
        System.out.println("Contact Information:");
        System.out.println("  Phone: " + patient.getPhoneNumber());
        System.out.println("  Email: " + patient.getEmailAddress());
        System.out.println("Past Diagnoses:");

        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        Map<String, List<String>> diagnosesAndTreatmentsMap = new HashMap<>();

        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patient.getUserID()) && appointment.getOutcomeRecord() != null) {
                AppointmentOutcomeRecord outcomeRecord = appointment.getOutcomeRecord();
                String appointmentID = appointment.getAppointmentID();

                if (!diagnosesAndTreatmentsMap.containsKey(appointmentID)) {
                    diagnosesAndTreatmentsMap.put(appointmentID, new ArrayList<>());
                }

                if (outcomeRecord.getConsultationNotes() != null) {
                    diagnosesAndTreatmentsMap.get(appointmentID).add("Diagnosis: " + outcomeRecord.getConsultationNotes());
                }

                if (outcomeRecord.getPrescriptions() != null) {
                    List<String> medicationDetails = new ArrayList<>();
                    for (Prescription prescription : outcomeRecord.getPrescriptions()) {
                        String medicationDetail = "Medication: " + prescription.getMedication().getName() +
                                " (Status: " + prescription.getStatus() + ")";
                        medicationDetails.add(medicationDetail);
                    }
                    if (!medicationDetails.isEmpty()) {
                        diagnosesAndTreatmentsMap.get(appointmentID).add("Prescriptions:");
                        diagnosesAndTreatmentsMap.get(appointmentID).addAll(medicationDetails);
                    }
                }
            }
        }

        if (diagnosesAndTreatmentsMap.isEmpty()) {
            System.out.println("No Past Diagnoses or Treatments");
        } else {
            for (Map.Entry<String, List<String>> entry : diagnosesAndTreatmentsMap.entrySet()) {
                String appointmentID = entry.getKey();
                List<String> details = entry.getValue();

                System.out.println("Appointment ID: " + appointmentID);
                for (String detail : details) {
                    System.out.println("- " + detail);
                }
                System.out.println();
            }
        }
    }

    /**
     * Updates the patient's contact information, including phone number and email address.
     *
     * @param patient the {@code Patient} whose contact information is to be updated.
     * @throws IOException            if an error occurs while saving the updated information.
     * @throws ClassNotFoundException if the data file cannot be found or loaded.
     */

    public void updatePersonalInfo(Patient patient) throws IOException, ClassNotFoundException {
        PatientController patientController = new PatientController();
        String newPhone, newEmail;

        while (true) {
            System.out.print("Enter new phone number: ");
            newPhone = scanner.nextLine();

            if (newPhone != null && !newPhone.isEmpty() && newPhone.matches("\\d+")) {
                System.out.println("Phone number is valid!");
                break;
            } else {
                System.out.println("Invalid phone number. Please try again.");
            }
        }
        while (true) {
            System.out.print("Enter new email address: ");
            newEmail = scanner.nextLine();

            if (newEmail != null && newEmail.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("Email address is valid!");
                break; // Exit the loop when the email is valid
            } else {
                System.out.println("Invalid email address. Please try again.");
            }
        }

        patientController.updateContactInfo(newPhone, newEmail, patient);
    }

    /**
     * Allows the patient to change their password.
     *
     * @param patient the {@code Patient} whose password is to be changed.
     * @throws IOException            if an error occurs while saving the updated password.
     * @throws ClassNotFoundException if the data file cannot be found or loaded.
     */
    
    public void changePassword(Patient patient) throws IOException, ClassNotFoundException {
        PasswordController passwordController = new PasswordController();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        passwordController.changePassword(newPassword, patient.getUserID());
    }
}
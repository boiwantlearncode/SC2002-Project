package Views.Doctor;

import Controllers.DoctorController.DoctorController;
import DataManager.UserRepo;
import Models.Doctor;
import Models.Patient;
import Models.User;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code DoctorPatientView} class provides the user interface for doctors to interact with patient records.
 * This includes viewing and updating patient medical records.
 */

public class DoctorPatientView {

    /**
     * A {@code Scanner} object to handle input from the doctor.
     */

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the interface for viewing a specific patient's medical record.
     * Prompts the doctor to enter the patient ID.
     *
     * @param doctor the {@code Doctor} viewing the patient's record.
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the patient data cannot be loaded.
     */
    
    public void viewPatientRecord(Doctor doctor) throws IOException, ClassNotFoundException {
        DoctorController doctorController = new DoctorController();
        System.out.print("Enter patient ID: ");
        String patientID = scanner.next();

        doctorController.viewPatientRecord(doctor, patientID);
    }

    /**
     * Displays the interface for updating a specific patient's medical record.
     * Prompts the doctor to enter the patient ID, diagnosis, and treatment details.
     *
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the patient data cannot be loaded.
     */
    
    public void updatePatientRecord() throws IOException, ClassNotFoundException {
        DoctorController doctorController = new DoctorController();
        System.out.print("Enter Patient ID: ");
        String patientID = scanner.next();
        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.next();
        System.out.print("Enter treatment: ");
        String treatment = scanner.next();

        doctorController.updatePatientMedicalRecord(patientID, diagnosis, treatment);
    }
}
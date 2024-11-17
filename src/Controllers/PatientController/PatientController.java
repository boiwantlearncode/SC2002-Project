package Controllers.PatientController;

import DataManager.UserRepo;
import Models.MedicalRecord;
import Models.Patient;
import Models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PatientController} class provides methods to manage patient-related actions such as
 * updating contact information and changing passwords.
 */

public class PatientController {

    /**
     * Updates the contact information (phone number and email address) for a given patient.
     *
     * @param phoneNumber  the new phone number of the patient.
     * @param emailAddress the new email address of the patient.
     * @param patient      the {@code Patient} object to update.
     * @throws IOException            if an I/O error occurs while saving the updated data.
     * @throws ClassNotFoundException if the user data file cannot be loaded.
     */

    public void updateContactInfo(String phoneNumber, String emailAddress, Patient patient) throws IOException, ClassNotFoundException {

        UserRepo userRepo = new UserRepo();
        userRepo.loadData();
        List<User> users = userRepo.getData();

        int userIndex = 0;
        int size = users.size();
        boolean userFound = false;

        for (; userIndex < size; userIndex++) {
            if (users.get(userIndex).getUserID().equals(patient.getUserID())) {
                patient.setPhoneNumber(phoneNumber);
                patient.setEmailAddress(emailAddress);
                userFound = true;
                break;
            }
        }

        if (userFound) {
            userRepo.setUsers(users);
            userRepo.saveData();
            System.out.println("Contact information updated successfully.");
        } else {
            System.out.println("Patient not found in the records.");
        }
    }
}
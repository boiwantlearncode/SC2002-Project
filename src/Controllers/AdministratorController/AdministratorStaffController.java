package Controllers.AdministratorController;

import DataManager.AppointmentsRepo;
import DataManager.UserRepo;
import Models.*;
import Views.Administrator.AdministratorStaffView;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controllers.AuthenticationController.PasswordController.PasswordController;

/**
 * The {@code AdministratorStaffController} class handles operations related to hospital staff management.
 * This includes viewing, adding, updating, and removing staff, as well as managing appointment details.
 */

public class AdministratorStaffController {

    /**
     * Scanner for input operations.
     */

    Scanner scanner = new Scanner(System.in);

    /**
     * Administrator performing the actions.
     */

    private Administrator admin;

    /**
     * View layer for interacting with the administrator for staff management.
     */

    private AdministratorStaffView adminView;

    /**
     * List of all users in the system.
     */

    private List<User> users;

    /**
     * List of all appointments in the system.
     */

    private List<Appointment> appointments;

    /**
     * Repository for managing user data.
     */

    private UserRepo userRepo;

    /**
     * Constructs an {@code AdministratorStaffController} and initializes the view, user repository, and data.
     *
     * @throws IOException            if an error occurs while loading user data.
     * @throws ClassNotFoundException if the user data cannot be loaded.
     */

    public AdministratorStaffController() throws IOException, ClassNotFoundException {
        this.adminView = new AdministratorStaffView();
        this.users = new ArrayList<>();
        this.appointments = new ArrayList<>();
        userRepo = new UserRepo();
        userRepo.loadData();
        users = userRepo.getData();
    }

    /**
     * Manages hospital staff by presenting options to the administrator.
     *
     * @param admin the {@code Administrator} managing staff.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if the user data cannot be loaded.
     */

    public void manageHospitalStaff(Administrator admin) throws IOException, ClassNotFoundException {
        while (true) {
            adminView.displayStaffManagementMenu();
            int choice = adminView.getChoiceInput();

            switch (choice) {
                case 1:
                    viewAllStaff();
                    break;
                case 2:
                    addNewStaff();
                    break;
                case 3:
                    removeStaff();
                    break;
                case 4:
                    updateStaff();
                    break;
                case 5:
                    return;
                default:
                    adminView.displayError("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays all staff in the hospital.
     *
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the user data cannot be loaded.
     */

    private void viewAllStaff() throws IOException, ClassNotFoundException {
        System.out.println("All Hospital Staff:");
        for (User user : users) {
            if (!(user instanceof Patient)) {
                adminView.displayAllStaff();
            }
        }
    }

    /**
     * Adds a new staff member to the system.
     *
     * @throws IOException            if an error occurs during data saving.
     * @throws ClassNotFoundException if the user data cannot be loaded.
     */

    private void addNewStaff() throws IOException, ClassNotFoundException {
        String role = adminView.getRoleInput();
        String name = adminView.getNameInput();

        String password = adminView.getPasswordInput();
        String salt = PasswordController.generateSalt();
        String hashedPassword = null;
        try {
            hashedPassword = PasswordController.hashPassword(password, salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            adminView.displayError("Error hashing password. Please try again.");
        }

        String gender = adminView.getGenderInput();
        int age = adminView.getAgeInput();
        String userID = generateUserID(role);

        User newUser;
        switch (role) {
            case "Doctor":
                String specialization = adminView.getSpecializationInput();
                List<LocalDateTime> availability = adminView.getAvailabilityInput();
                newUser = new Doctor(userID, name, hashedPassword, salt, role, gender, age, specialization, availability);
                break;
            case "Pharmacist":
                newUser = new Pharmacist(userID, name, hashedPassword, salt, role, gender, age);
                break;
            case "Administrator":
                newUser = new Administrator(userID, name, hashedPassword, salt, role, gender, age);
                break;
            default:
                adminView.displayError("Invalid role.");
                return;
        }

        users.add(newUser);
        userRepo.setUsers(users);
        userRepo.saveData();
        adminView.displayNewStaffAdded(userID);
    }

    /**
     * Removes a staff member based on their user ID.
     *
     * @throws IOException if an error occurs during data saving.
     */

    private void removeStaff() throws IOException {
        String userID = adminView.getUserIDToRemove();
        User userToRemove = null;
        for (User user : users) {
            if (user.getUserID().equals(userID) && !(user instanceof Patient)) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            users.remove(userToRemove);
            userRepo.setUsers(users);
            userRepo.saveData();
            adminView.displayStaffRemoved();
        } else {
            adminView.displayError("Invalid User ID or not authorized to remove.");
        }
    }

    /**
     * Updates the details of an existing staff member.
     *
     * @throws IOException            if an error occurs during data saving.
     * @throws ClassNotFoundException if the user data cannot be loaded.
     */

    private void updateStaff() throws IOException, ClassNotFoundException {
        adminView.displayAllStaff();
        System.out.println("Enter User id to update the user");
        String enteredID = scanner.nextLine();

        User user = adminView.findUserById(enteredID);
        User userToUpdate = null;

        if (user.getUserID().equals(enteredID) && !(user instanceof Patient)) {
            userToUpdate = user;
        }

        if (userToUpdate != null) {
            String newName = adminView.getNameInput();
            if (!newName.isEmpty()) {
                userToUpdate.setName(newName);
            }

            System.out.println("Enter New Password");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                String salt = PasswordController.generateSalt();
                String hashedPassword = null;
                try {
                    hashedPassword = PasswordController.hashPassword(newPassword, salt);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    adminView.displayError("Error the hashing new password. Please try again.");
                }
                
                userToUpdate.setPassword(hashedPassword);
            }

            System.out.println("Enter Gender");
            String newGender = scanner.nextLine();
            if (!newGender.isEmpty()) {
                userToUpdate.setGender(newGender);
            }

            Integer newAge = adminView.getAgeInput();
            if (newAge != null) {
                userToUpdate.setAge(newAge);
            }

            if (userToUpdate instanceof Doctor doctor) {
                String newSpecialization = adminView.getSpecializationInput();
                if (!newSpecialization.isEmpty()) {
                    doctor.setSpecialization(newSpecialization);
                }

                List<LocalDateTime> newAvailability = adminView.getAvailabilityInput();
                if (!newAvailability.isEmpty()) {
                    doctor.setAvailability(newAvailability);
                }
            }
            users.add(userToUpdate);
            userRepo.setUsers(users);
            userRepo.saveData();
            adminView.displayAllStaff();
        } else {
            adminView.displayError("Invalid User ID or not authorized to update.");
        }
    }

    /**
     * Generates a unique user ID based on the role.
     *
     * @param role the role of the user (e.g., Doctor, Pharmacist).
     * @return a unique user ID string.
     */

    private String generateUserID(String role) {
        return role.substring(0, 2).toUpperCase() + System.currentTimeMillis();
    }

    /**
     * Retrieves all appointments from the repository.
     *
     * @return a list of all {@code Appointment} objects.
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the appointment data cannot be loaded.
     */

    private List<Appointment> getAllAppointments() throws IOException, ClassNotFoundException {
        AppointmentsRepo app = new AppointmentsRepo();
        app.loadData();
        return app.getData();
    }

    /**
     * Displays details of all appointments.
     *
     * @param admin the {@code Administrator} viewing appointments.
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the appointment data cannot be loaded.
     */
    
    public void viewAppointmentsDetails(Administrator admin) throws IOException, ClassNotFoundException {
        List<Appointment> appointmentsList = getAllAppointments();
        if (appointmentsList.isEmpty()) {
            System.out.println("No Appointments");
        }
        System.out.println("All Appointments:");
        for (Appointment apt : appointmentsList) {
            adminView.displayAppointments(apt);
        }
    }
}
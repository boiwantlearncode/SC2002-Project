package Views.Administrator;

import DataManager.UserRepo;
import Models.Appointment;
import Models.Patient;
import Models.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code AdministratorStaffView} class provides the view layer for managing hospital staff.
 * It handles displaying menus, receiving input, and showing feedback to administrators.
 */

public class AdministratorStaffView {

    /**
     * A scanner object to handle user input.
     */

    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructs an {@code AdministratorStaffView} instance.
     */

    public AdministratorStaffView() {
    }

    /**
     * Displays the staff management menu to the administrator.
     */

    public void displayStaffManagementMenu() {
        System.out.println("\n--- Manage Hospital Staff ---");
        System.out.println("1. View all staff");
        System.out.println("2. Add new staff");
        System.out.println("3. Remove staff");
        System.out.println("4. Update staff");
        System.out.println("5. Return to main menu");
        System.out.print("Enter your choice: ");
    }

    /**
     * Displays all staff in the hospital by fetching user data from the repository.
     *
     * @throws IOException            if an error occurs while loading data.
     * @throws ClassNotFoundException if the data format is invalid.
     */

    public void displayAllStaff() throws IOException, ClassNotFoundException {
        UserRepo userRepo = new UserRepo();
        userRepo.loadData();
        List<User> users = userRepo.getData();
    
        List<User> doctors = new ArrayList<>();
        List<User> pharmacists = new ArrayList<>();
        List<User> administrators = new ArrayList<>();
    
        for (User user : users) {
            if (!(user instanceof Patient)) {
                switch (user.getRole().toLowerCase()) {
                    case "doctor":
                        doctors.add(user);
                        break;
                    case "pharmacist":
                        pharmacists.add(user);
                        break;
                    case "admin":
                        administrators.add(user);
                        break;
                }
            }
        }
    
        System.out.println("==============================================");
        System.out.println("Doctors:");
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            for (User doctor : doctors) {
                System.out.println("- " + doctor.getUserID() + " | " + doctor.getName());
            }
        }
    
        System.out.println("==============================================");
        System.out.println("Pharmacists:");
        if (pharmacists.isEmpty()) {
            System.out.println("No pharmacists found.");
        } else {
            for (User pharmacist : pharmacists) {
                System.out.println("- " + pharmacist.getUserID() + " | " + pharmacist.getName());
            }
        }
    
        System.out.println("==============================================");
        System.out.println("Admins:");
        if (administrators.isEmpty()) {
            System.out.println("No administrators found.");
        } else {
            for (User admin : administrators) {
                System.out.println("- " + admin.getUserID() + " | " + admin.getName());
            }
        }
    
        System.out.println("==============================================");
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to find.
     * @return the {@code User} object if found, otherwise {@code null}.
     * @throws IOException            if an error occurs while loading data.
     * @throws ClassNotFoundException if the data format is invalid.
     */

    public User findUserById(String id) throws IOException, ClassNotFoundException {
        UserRepo userRepo = new UserRepo();
        List<User> users = new ArrayList<>();
        userRepo.loadData();
        users = userRepo.getData();

        for (User user : users) {
            if (user.getUserID().equals(id)) {
                return user;
            }
        }
        System.out.println("User not found");
        return null;
    }

    /**
     * Gets the role input for new staff.
     *
     * @return the role as a {@code String}.
     */

    public String getRoleInput() {
        System.out.print("Enter role (Doctor/Pharmacist/Administrator): ");
        return scanner.nextLine();
    }

    /**
     * Gets the name input for new staff.
     *
     * @return the name as a {@code String}.
     */
    
    public String getNameInput() {
        System.out.print("Enter name: ");
        return scanner.nextLine();
    }

    /**
     * Gets the password input for new staff.
     *
     * @return the password as a {@code String}.
     */

    public String getPasswordInput() {
        System.out.print("Enter password: ");
        return scanner.nextLine();
    }

    /**
     * Gets the gender input for new staff.
     *
     * @return the gender as a {@code String}.
     */

    public String getGenderInput() {
        System.out.print("Enter gender: ");
        return scanner.nextLine();
    }

    /**
     * Gets the age input for new staff.
     *
     * @return the age as an {@code int}.
     */

    public int getAgeInput() {
        System.out.print("Enter age: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Gets the specialization input for doctors.
     *
     * @return the specialization as a {@code String}.
     */

    public String getSpecializationInput() {
        System.out.print("Enter specialization: ");
        return scanner.nextLine();
    }

    /**
     * Gets the availability input for doctors.
     *
     * @return a list of available {@code LocalDateTime} slots.
     */

    public List<LocalDateTime> getAvailabilityInput() {
        List<LocalDateTime> availability = new ArrayList<>();
        System.out.println("Enter availability times (in format yyyy-MM-dd HH:mm). Enter 'done' when finished:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                availability.add(LocalDateTime.parse(input, formatter));
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        return availability;
    }

    /**
     * Gets the User ID for removing staff.
     *
     * @return the User ID as a {@code String}.
     */

    public String getUserIDToRemove() {
        System.out.print("Enter User ID to remove: ");
        return scanner.nextLine();
    }

    /**
     * Displays a message confirming the addition of new staff.
     *
     * @param userID the ID of the newly added staff.
     */

    public void displayNewStaffAdded(String userID) {
        System.out.println("New staff added successfully. User ID: " + userID);
    }

    /**
     * Displays a message confirming the removal of staff.
     */

    public void displayStaffRemoved() {
        System.out.println("Staff member removed successfully.");
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display.
     */

    public void displayError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Gets the choice input for menu options.
     *
     * @return the choice as an {@code int}.
     */

    public int getChoiceInput() {
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Displays an appointment's details.
     *
     * @param apt the {@code Appointment} to display.
     */
    
    public void displayAppointments(Appointment apt) {
        System.out.println(apt.getAppointmentID() + " - " + apt.getAppointmentTime() + " (" + apt.getStatus() + ")"
                + apt.getDoctorID());
    }
}
import Controllers.AuthenticationController.LoginController.LoginController;
import Controllers.ProcessOptions.ProcessAdministratorChoice;
import Controllers.ProcessOptions.ProcessDoctorChoice;
import Controllers.ProcessOptions.ProcessPatientChoice;
import Controllers.ProcessOptions.ProcessPharmacistChoice;
import Controllers.ShowOptions.ShowAdministratorMenu;
import Controllers.ShowOptions.ShowDoctorMenu;
import Controllers.ShowOptions.ShowPatientMenu;
import Controllers.ShowOptions.ShowPharmacistMenu;
import Controllers.AuthenticationController.PasswordController.PasswordController; 
import Models.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The {@code Main} class serves as the entry point for the Hospital Management System.
 * It provides a login mechanism and interactive role-based menus for users, such as administrators,
 * doctors, pharmacists, and patients.
 */

public class Main {

    /**
     * Scanner instance to handle user input.
     */

    public static Scanner scanner = new Scanner(System.in);


    /**
     * Main method to run the Hospital Management System. The system provides a login screen
     * and role-based menus for administrators, doctors, pharmacists, and patients. Users can
     * interact with the system based on their role.
     *
     * @param args command-line arguments (not used).
     * @throws IOException            if there is an issue accessing data files.
     * @throws ClassNotFoundException if data cannot be loaded properly.
     * @throws NoSuchAlgorithmException 
     */
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {

        // Initialize menu and process options for different roles
        ShowAdministratorMenu showAdministratorMenu = new ShowAdministratorMenu();
        ShowDoctorMenu showDoctorMenu = new ShowDoctorMenu();
        ShowPatientMenu showPatientMenu = new ShowPatientMenu();
        ShowPharmacistMenu showPharmacistMenu = new ShowPharmacistMenu();

        ProcessAdministratorChoice processAdministratorChoice = new ProcessAdministratorChoice();
        ProcessDoctorChoice processDoctorChoice = new ProcessDoctorChoice();
        ProcessPatientChoice processPatientChoice = new ProcessPatientChoice();
        ProcessPharmacistChoice processPharmacistChoice = new ProcessPharmacistChoice();

        System.out.println("****** HOSPITAL MANAGEMENT SYSTEM ******\n");

        User validatedUser = null;

        // Main loop for login and role-based menu interaction
        while (true) {
            System.out.println("Enter 1 to login:");
            System.out.println("Enter 2 to exit:");
            System.out.println("Enter 3 to Forget password:"); 
            System.out.print("Your Choice -> ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    validatedUser = LoginController.validateUser();
                    break;
                case "2":
                    System.exit(0);
                    break;
                case "3":
                    System.out.println("Please log in as an administrator to reset a password.");
                
                    User adminUser = LoginController.validateUser();

                    if (adminUser instanceof Administrator) {
                        System.out.print("Enter the UserID to reset the password: ");
                        String userID = scanner.nextLine();
                        PasswordController.resetPassword(userID);
                    } else {
                        System.out.println("Access denied. Only administrators can reset the passwords for user.");
                    }
                    continue;
                default:
                    System.out.println("Invalid Input");
                    continue;
            }

            if (validatedUser == null) {
                System.out.println("Username or Password is incorrect!!\nTRY AGAIN");
            } else {
                while (true) {
                    System.out.println("\n--- " + validatedUser.getRole().toUpperCase() + " Menu ---");
                    if (validatedUser instanceof Patient) {
                        showPatientMenu.showUserMenu();
                    } else if (validatedUser instanceof Doctor) {
                        showDoctorMenu.showUserMenu();
                    } else if (validatedUser instanceof Pharmacist) {
                        showPharmacistMenu.showUserMenu();
                    } else if (validatedUser instanceof Administrator) {
                        showAdministratorMenu.showUserMenu();
                    }

                    System.out.print("Enter your choice (or 'logout' to return to main menu): ");
                    choice = scanner.nextLine();

                    if (choice.equalsIgnoreCase("logout")) {
                        break;
                    }

                    // Process user choice based on role
                    if (validatedUser instanceof Patient) {
                        processPatientChoice.processUserChoice((Patient) validatedUser, choice);
                    } else if (validatedUser instanceof Doctor) {
                        processDoctorChoice.processUserChoice((Doctor) validatedUser, choice);
                    } else if (validatedUser instanceof Pharmacist) {
                        processPharmacistChoice.processUserChoice((Pharmacist) validatedUser, choice);
                    } else if (validatedUser instanceof Administrator) {
                        processAdministratorChoice.processUserChoice((Administrator) validatedUser, choice);
                    }
                }
            }
        }
    }
}
package Controllers.AuthenticationController.LoginController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

import DataManager.UserRepo;
import Models.User;

import Controllers.AuthenticationController.PasswordController.*;

public class LoginController {
    /**
     * Scanner instance to handle user input.
     */

     public static Scanner scanner = new Scanner(System.in);

     /**
      * Validates the user's login credentials. If it's the user's first login, they are required
      * to change their password.
      *
      * @return a {@code User} object if the login is successful, otherwise {@code null}.
      * @throws IOException            if there is an issue accessing user data.
      * @throws ClassNotFoundException if the user data cannot be loaded properly.
     * @throws NoSuchAlgorithmException 
      */
 
     public static User validateUser() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
         System.out.print("Enter username: ");
         String username = scanner.nextLine();
         System.out.print("Enter password: ");
         String password = scanner.nextLine();
 
         UserRepo userRepo = new UserRepo();
         userRepo.loadData();
         List<User> users = userRepo.getData();
 
         for (User user : users) {
            String storedSalt = user.getSalt();
            String storedHash = user.getPassword();
            if (user.getUserID().equalsIgnoreCase(username) && PasswordController.validatePassword(password, storedHash, storedSalt)) {
                 System.out.println("Login Successful. Welcome " + user.getName());
                 if (user.isFirstLogin()) {
                     System.out.println("\nFirst Login detected. You must change your password.");
                     String newPassword;
                     String confirmPassword;
                     while (true) {
                         System.out.print("Enter New Password: ");
                         newPassword = scanner.nextLine();
                         System.out.print("Confirm New Password: ");
                         confirmPassword = scanner.nextLine();
                         if (confirmPassword == null || confirmPassword.isEmpty()) {
                             System.out.println("Password cannot be empty.\nTRY AGAIN.");
                         } else if (PasswordController.validatePassword(newPassword, storedHash, storedSalt)) {
                             System.out.println("Your new password cannot be your old password.\nTRY AGAIN.");
                         } else if (confirmPassword.equals(newPassword)) {
                             String newSalt = PasswordController.generateSalt();
                             user.setSalt(newSalt);
                             user.setPassword(PasswordController.hashPassword(newPassword, newSalt));
                             user.setFirstLogin(false);
                             break;
                         } else {
                             System.out.println("Passwords do not match. Try again.");
                         }
                     }
 
                     // Save the new password
                     System.out.println("New password has been set.");
                     userRepo.setUsers(users);
                     userRepo.saveData();
                     return user;
                 } else {
                     return user;
                 }
             }
         }
 
         return null;
     }
}

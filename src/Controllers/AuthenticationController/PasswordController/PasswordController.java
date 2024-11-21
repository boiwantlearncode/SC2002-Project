package Controllers.AuthenticationController.PasswordController;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import DataManager.UserRepo;
import Models.User;

/**
 * The PasswordController class provides functionality for handling user password operations, 
 * including hashing, validation, password changes, and password resets. 
 * It uses SHA-256 hashing with salting for secure password management.
 */

public class PasswordController {

    /**
     * Generates a random salt value to be used for password hashing.
     *
     * @return A Base64-encoded string representation of the generated salt.
     */
    
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a given password with a provided salt using the SHA-256 algorithm.
     *
     * @param password The plain-text password to hash.
     * @param salt     The salt value to include in the hash.
     * @return A Base64-encoded string representation of the hashed password.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes()); // Include salt in the hash
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    /**
     * Validates a user-provided password against a stored hash and salt.
     *
     * @param inputPassword The plain-text password provided by the user.
     * @param storedHash    The stored hashed password.
     * @param storedSalt    The stored salt value.
     * @return True if the hashed input password matches the stored hash, false otherwise.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    
    public static boolean validatePassword(String inputPassword, String storedHash, String storedSalt) 
            throws NoSuchAlgorithmException {
        String hashedInput = hashPassword(inputPassword, storedSalt);
        return hashedInput.equals(storedHash);
    }

    /**
     * Changes the password for a user with a given user ID.
     *
     * @param newPassword The new password to set for the user.
     * @param userID      The ID of the user whose password is being changed.
     * @throws IOException            If an error occurs while saving data or the new password is invalid.
     * @throws ClassNotFoundException If an error occurs while loading user data.
     */
    
    public void changePassword(String newPassword, String userID) throws IOException, ClassNotFoundException {
        // Check if valid password condition
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IOException("Password cannot be empty");
        }

        UserRepo userRepo = new UserRepo();
        userRepo.loadData();
        List<User> users = userRepo.getData();

        for (User user : users) {
            if (user instanceof User && user.getUserID().equals(userID)) {
                String salt = generateSalt();
                String newHashedPassword = null;
                try {
                    newHashedPassword = hashPassword(newPassword, salt);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                user.setPassword(newHashedPassword);
                user.setSalt(salt);
                userRepo.setUsers(users);
                userRepo.saveData();
                break;
            }
        }
        System.out.println("Password successfully changed.");
    }

    /**
     * Resets the password for a user with a given user ID to a default password.
     * The default password is set to "password".
     *
     * @param userID The ID of the user whose password is being reset.
     * @throws IOException            If an error occurs while saving data.
     * @throws ClassNotFoundException If an error occurs while loading user data.
     */

    public static void resetPassword(String userID) throws IOException, ClassNotFoundException {
        UserRepo userRepo = new UserRepo();
        userRepo.loadData();
        List<User> users = userRepo.getData();

        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                String defaultPassword = "password";
                String salt = generateSalt();
                String newHashedPassword = null;
                try {
                    newHashedPassword = hashPassword(defaultPassword, salt);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                user.setPassword(newHashedPassword);
                user.setSalt(salt);
                user.setFirstLogin(true);
                userRepo.setUsers(users);
                userRepo.saveData();
                System.out.println("Password for User ID: " + userID + " has been reset to the default password: password.");
                return;
            }
        }
        System.out.println("User with ID '" + userID + "' not found.");
    }
 
}



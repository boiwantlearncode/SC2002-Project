package Controllers.AuthenticationController.PasswordController;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import DataManager.UserRepo;
import Models.User;

// SHA256 hashing + Salt
// A salt is a random value unique to each password that prevents attackers from using precomputed hashes (e.g., rainbow tables).
public class PasswordController {

    // Generate a random salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash a password with a given salt using SHA-256
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes()); // Include salt in the hash
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // Validate a password against a hash
    public static boolean validatePassword(String inputPassword, String storedHash, String storedSalt) 
            throws NoSuchAlgorithmException {
        String hashedInput = hashPassword(inputPassword, storedSalt);
        return hashedInput.equals(storedHash);
    }

    // Change password
    public void changePassword(String newPassword, String userID) throws IOException, ClassNotFoundException {
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
    }

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
                System.out.println("Password for User ID: " + userID + " has been reset to 'password."); 
                return;  
            } 
        } 
        System.out.println("User with ID '" + userID + "' not found."); 
    } 
 
}



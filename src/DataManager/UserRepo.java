package DataManager;

import Models.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Controllers.AuthenticationController.PasswordController.PasswordController;

/**
 * The {@code UserRepo} class manages the persistence and retrieval of user data.
 * This repository handles a list of {@code User} objects, including their storage in a file
 * and retrieval from the file when the application starts.
 *
 * Implements the {@link SerializableRepo} interface for saving and loading data.
 */

public class UserRepo implements SerializableRepo<List<User>>{

    /**
     * The path to the file where user data is stored.
     */
    
    private static final String USERS_FILE = "src/DataManager/users.txt";

    /**
     * A list containing all users managed by this repository.
     */
    
    private List<User> users = new ArrayList<>();

    /**
     * Saves the current user data to the file specified by {@code USERS_FILE}.
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */

    @Override
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            //System.out.println("User data saved successfully.");
        }
    }

    /**
     * Loads user data from the file specified by {@code USERS_FILE}.
     * If the file is empty, it populates it with initial data, including sample users such as
     * a patient, a doctor, a pharmacist, and an administrator. Passwords are hashed and salted
     * using the {@link PasswordController}.
     *
     * @throws IOException if an I/O error occurs while reading from the file or if hashing fails.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */

    @Override
    public void loadData() throws IOException, ClassNotFoundException {
        try {

            String defaultPassword = "password";
            String salt = PasswordController.generateSalt();
            String hashedPassword = PasswordController.hashPassword(defaultPassword, salt);

            if (new File(USERS_FILE).exists()) {
                if (Files.size(Paths.get(USERS_FILE)) == 0) {
                    // File is empty, populate with initial data
                    users.add(new Patient("P001", "John Doe", hashedPassword, salt, "patient","male", 34, "A+",
                    "84476972", "john@gmail.com",LocalDate.of(1990, 5, 15)));

                    List<LocalDateTime> availability = new ArrayList<>();
                    availability.add(LocalDateTime.of(2024, 11, 20, 15, 10));
                    availability.add(LocalDateTime.of(2024, 11, 24, 15, 10));
                    availability.add(LocalDateTime.of(2024, 12, 1, 10, 30));

                    users.add(new Doctor("D001", "Dr. Jane Smith", hashedPassword, salt,
                        "doctor", "male", 32,"General Practitioner", availability));

                    users.add(new Pharmacist("PH001", "Bob Johnson", hashedPassword, salt,
                        "pharmacist", "male", 24));

                    users.add(new Administrator("A001", "Admin User", hashedPassword, salt,
                        "admin", "male", 27));

                    setUsers(users);
                    saveData();
                    System.out.println("Initialized users.txt with sample data.");
                } else {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
                        users = (List<User>) ois.readObject();
                        //System.out.println("User data loaded successfully.");
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Failed to hash password: SHA-256 algorithm not available", e);
        }
    }

    /**
     * Retrieves the list of users managed by this repository.
     *
     * @return a {@link List} of {@link User} objects.
     */

    @Override
    public List<User> getData() {
        return users;
    }

    /**
     * Sets the list of users managed by this repository.
     *
     * @param users a {@link List} of {@link User} objects to be managed.
     */

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

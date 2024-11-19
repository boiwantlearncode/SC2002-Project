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

public class UserRepo implements SerializableRepo<List<User>>{
    private static final String USERS_FILE = "src/DataManager/users.txt";
    private List<User> users = new ArrayList<>();

    @Override
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            //System.out.println("User data saved successfully.");
        }
    }

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

    @Override
    public List<User> getData() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

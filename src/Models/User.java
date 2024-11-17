package Models;

import java.io.Serializable;

/**
 * The {@code User} class serves as an abstract representation of a user in the system.
 * It is the base class for all specific user types (e.g., {@code Administrator}, {@code Doctor}, {@code Patient}, {@code Pharmacist}).
 * The class implements {@code Serializable} to support data persistence.
 */

public abstract class User implements Serializable {

    /**
     * The unique identifier for the user.
     */

    protected String userID;

    /**
     * The password associated with the user account.
     */

    protected String password;

    /**
     * The salt associated with the user account.
     */

     protected String salt;

    /**
     * The name of the user.
     */

    protected String name;

    /**
     * The role of the user (e.g., "admin", "doctor", "patient", "pharmacist").
     */

    protected String role;

    /**
     * Indicates whether this is the user's first login.
     */

    protected boolean firstLogin;

    /**
     * The gender of the user.
     */

    protected String gender;

    /**
     * The age of the user.
     */

    protected int age;

    /**
     * Constructs a default {@code User} object with no attributes set.
     */

    public User() {}

    /**
     * Constructs a {@code User} object with the specified attributes.
     *
     * @param userID   the unique identifier for the user.
     * @param name     the name of the user.
     * @param password the password for the user account.
     * @param password the salt for the user authentication.
     * @param role     the role of the user in the system.
     * @param gender   the gender of the user.
     * @param age      the age of the user.
     */

    public User(String userID, String name, String password, String salt, String role, String gender, int age) {
        this.userID = userID;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.role = role;
        this.firstLogin = true;
        this.gender = gender;
        this.age = age;
    }

    /**
     * Validates the user's login by checking the provided password against the stored password.
     *
     * @param enteredPassword the password entered by the user.
     * @return {@code true} if the entered password matches the stored password, {@code false} otherwise.
     */

    public boolean login(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    /**
     * Changes the user's password and sets {@code firstLogin} to {@code false}.
     *
     * @param newPassword the new password to be set for the user.
     */
    
    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.firstLogin = false;
    }

    /**
     * Retrieves the user's unique identifier.
     *
     * @return the user ID as a {@code String}.
     */

    public String getUserID() {
        return userID;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param userID the user ID as a {@code String}.
     */

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Retrieves the user's name.
     *
     * @return the name of the user as a {@code String}.
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the name of the user as a {@code String}.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the user's role.
     *
     * @return the role of the user as a {@code String}.
     */

    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     *
     * @param role the role of the user as a {@code String}.
     */

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retrieves the user's password.
     *
     * @return the password of the user as a {@code String}.
     */

    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password of the user as a {@code String}.
     */

    public void setPassword(String password) {
        this.password = password;
    }

        /**
     * Retrieves the user's salt.
     *
     * @return the salt of the user as a {@code String}.
     */

     public String getSalt() {
        return salt;
    }

    /**
     * Sets the user's salt.
     *
     * @param salt the salt of the user as a {@code String}.
     */

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Checks if this is the user's first login.
     *
     * @return {@code true} if it is the first login, {@code false} otherwise.
     */

    public boolean isFirstLogin() {
        return firstLogin;
    }

    /**
     * Sets the first login status of the user.
     *
     * @param firstLogin {@code true} if it is the first login, {@code false} otherwise.
     */

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    /**
     * Retrieves the user's gender.
     *
     * @return the gender of the user as a {@code String}.
     */

    public String getGender() {
        return gender;
    }

    /**
     * Sets the user's gender.
     *
     * @param gender the gender of the user as a {@code String}.
     */

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Retrieves the user's age.
     *
     * @return the age of the user as an {@code int}.
     */

    public int getAge() {
        return age;
    }

    /**
     * Sets the user's age.
     *
     * @param age the age of the user as an {@code int}.
     */

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a {@code String} containing the user's details in CSV format.
     */
    
    @Override
    public String toString() {
        return userID + ','  + password + ',' + salt + ','+ name + ',' + role + ','+ firstLogin + ',' + gender + ','+ age;
    }
}
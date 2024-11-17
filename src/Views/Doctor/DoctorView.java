package Views.Doctor;

import Controllers.DoctorController.DoctorController;
import DataManager.UserRepo;
import Models.Doctor;
import Models.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code DoctorView} class provides the user interface for doctors to manage their schedules.
 * This includes viewing available slots and setting availability for appointments.
 */

public class DoctorView {

    /**
     * A {@code Scanner} object to handle input from the doctor.
     */

    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the personal schedule of the doctor by listing all available time slots.
     *
     * @param doctor the {@code Doctor} whose schedule is being displayed.
     */

    public void viewPersonalSchedule(Doctor doctor) throws IOException, ClassNotFoundException {
        UserRepo userRepo = new UserRepo();
        userRepo.loadData();
        List<User> users = userRepo.getData();

        Doctor existingDoctor = null;
        for (User user : users) {
            if (user.getUserID().equals(doctor.getUserID())) {
                existingDoctor = (Doctor) user;
                break;
            }
        }

        if (existingDoctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        List<LocalDateTime> availability = existingDoctor.getAvailability();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        if (availability.isEmpty()) {
            System.out.println("NO SLOTS AVAILABLE.");
            return;
        }
        
        System.out.println("SLOTS AVAILABLE:");
        for (LocalDateTime date : availability) {
            String dateTimeString = date.format(formatter);
            System.out.println(dateTimeString);
        }
    }

    /**
     * Allows the doctor to set their availability by entering a date and time.
     * The availability is then updated in the doctor's record.
     *
     * @param doctor the {@code Doctor} whose availability is being set.
     * @throws IOException            if an error occurs while updating the availability data.
     * @throws ClassNotFoundException if the doctor data cannot be loaded.
     */
    
    public void setAvailability(Doctor doctor) throws IOException, ClassNotFoundException {
        DoctorController doctorController = new DoctorController();
        System.out.println("Enter date (YYYY-MM-DD) for doctor availability: ");
        String date = scanner.nextLine();
        System.out.println("Enter time (HH:MM) for doctor availability: ");
        String time = scanner.nextLine();

        String dateTimeString = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime availableTime = LocalDateTime.parse(dateTimeString, formatter);
        doctorController.setAvailability(doctor, availableTime);

        System.out.println("Availability added successfully.");
    }
}
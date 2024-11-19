package Views.Doctor;

import Controllers.AuthenticationController.PasswordController.PasswordController;
import Controllers.DoctorController.DoctorController;
import DataManager.AppointmentsRepo;
import DataManager.UserRepo;
import Models.Administrator;
import Models.Appointment;
import Models.Doctor;
import Models.User;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

         // Load and filter appointments
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();
        LocalDateTime currentTime = LocalDateTime.now();

        appointments.removeIf(apt -> 
            !(apt.getAppointmentTime().isAfter(currentTime)) || 
            !apt.getDoctorID().equals(doctor.getUserID())
        );

        // Retrieve availability
        List<LocalDateTime> availability = existingDoctor.getAvailability();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("==============================================");
        System.out.println("Availability Slots:");
        if (availability.isEmpty()) {
            System.out.println("You have no available slots for booking. Input 4 to schedule your availability now.");
        } else {
            for (LocalDateTime date : availability) {
                System.out.println("- " + date.format(formatter));
            }
        }

        System.out.println("==============================================");
        List<Appointment> confirmedAppointments = new ArrayList<>();
        List<Appointment> pendingAppointments = new ArrayList<>();

        for (Appointment apt : appointments) {
            if (apt.getStatus().equalsIgnoreCase("Confirmed")) {
                confirmedAppointments.add(apt);
            } else if (apt.getStatus().equalsIgnoreCase("Pending")) {
                pendingAppointments.add(apt);
            }
        }

        System.out.println("Confirmed Appointments:");
        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments.");
        } else {
            for (Appointment apt : confirmedAppointments) {
                System.out.printf("- ID: %s | Patient: %s | Date/Time: %s%n",
                    apt.getAppointmentID(),
                    apt.getPatientID(),
                    apt.getAppointmentTime().format(formatter));
            }
        }

        System.out.println("==============================================");
        System.out.println("Pending Appointments:");
        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointments.");
        } else {
            for (Appointment apt : pendingAppointments) {
                System.out.printf("- ID: %s | Patient: %s | Date/Time: %s%n",
                    apt.getAppointmentID(),
                    apt.getPatientID(),
                    apt.getAppointmentTime().format(formatter));
            }
        }
        System.out.println("==============================================");
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

        String date;
        String time;
        LocalDateTime availableTime = null;

        System.out.print("Enter date (YYYY-MM-DD) for doctor availability: ");
        date = scanner.nextLine();
        System.out.print("Enter time (HH:MM) for doctor availability: ");
        time = scanner.nextLine();

        String dateTimeString = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            availableTime = LocalDateTime.parse(dateTimeString, formatter);

            if (!availableTime.isAfter(LocalDateTime.now())) {
                throw new DateTimeException("Your available schedule must be in the future. Please try again.");
            }
            
            doctorController.setAvailability(doctor, availableTime);
            System.out.println("Availability added successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date or time format. Please enter the date in YYYY-MM-DD and time in HH:MM format.");
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Allows the patient to change their password.
     *
     * @param doctor the {@code Doctor} whose password is to be changed.
     * @throws IOException            if an error occurs while saving the updated password.
     * @throws ClassNotFoundException if the data file cannot be found or loaded.
     */

    public void changePassword(Doctor doctor) throws IOException, ClassNotFoundException {
        try {
            PasswordController passwordController = new PasswordController();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            passwordController.changePassword(newPassword, doctor.getUserID());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Invalid password format, please try again.");
        }
    }
}
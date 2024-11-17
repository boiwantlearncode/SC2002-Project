package Views.Patient;

import Controllers.PatientController.PatientAppointmentController;
import DataManager.AppointmentsRepo;
import DataManager.UserRepo;
import Models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * The {@code PatientAppointmentView} class provides the user interface for patients to manage their appointments.
 * This includes viewing available slots, scheduling, rescheduling, canceling, and viewing past appointment outcomes.
 */

public class PatientAppointmentView {

    /**
     * Scanner object for handling user input.
     */

    static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays available appointment slots for all doctors.
     *
     * @throws IOException            if an error occurs while loading user data.
     * @throws ClassNotFoundException if the user data file cannot be found or loaded.
     */

    public void viewAvailableAppointmentSlots() throws IOException, ClassNotFoundException {
        UserRepo userRepo = new UserRepo();
        userRepo.loadData();
        List<User> users = userRepo.getData();

        System.out.println("Available Appointment Slots:");
        for (User user : users) {
            if (user instanceof Doctor doctor) {
                System.out.println(doctor.getUserID() + ": " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
                for (LocalDateTime slot : doctor.getAvailability()) {
                    System.out.println("- " + slot.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }
        }
    }

    /**
     * Schedules an appointment for the patient.
     *
     * @param patient the {@code Patient} scheduling the appointment.
     * @throws IOException            if an error occurs while saving the appointment data.
     * @throws ClassNotFoundException if the appointment data file cannot be found or loaded.
     */

    public void scheduleAppointment(Patient patient) throws IOException, ClassNotFoundException {
        PatientAppointmentController patientAppointmentController = new PatientAppointmentController();
        viewAvailableAppointmentSlots();


        Scanner scanner = new Scanner(System.in);
        String doctorID;
        String date;
        String time;
        LocalDateTime appointmentDateTime = null;

        System.out.print("Enter Doctor ID: ");
        doctorID = scanner.nextLine();

        while (true) {
            System.out.print("Enter date (YYYY-MM-DD): ");
            date = scanner.nextLine();
            System.out.print("Enter time (HH:MM): ");
            time = scanner.nextLine();

            String dateTimeString = date + " " + time;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            try {
                appointmentDateTime = LocalDateTime.parse(dateTimeString, formatter);

                // Check if the entered datetime is after the current time
                if (appointmentDateTime.isAfter(LocalDateTime.now())) {
                    break; // Valid date and time, exit the loop
                } else {
                    System.out.println("The appointment datetime must be in the future. Please try again.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date or time format. Please enter the date in YYYY-MM-DD and time in HH:MM format.");
            }
        }

        patientAppointmentController.scheduleAppointment(doctorID, appointmentDateTime, patient);
    }

    /**
     * Displays all scheduled appointments for the patient.
     *
     * @param patient the {@code Patient} viewing their appointments.
     * @throws IOException            if an error occurs while loading the appointment data.
     * @throws ClassNotFoundException if the appointment data file cannot be found or loaded.
     */

    public void viewScheduledAppointments(Patient patient) throws IOException, ClassNotFoundException {
        System.out.println("Scheduled Appointments:");
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        for (Appointment apt : appointments) {
            if (apt.getPatientID().equals(patient.getUserID())) {
                System.out.println("ID: " + apt.getAppointmentID() + ", Doctor: " + apt.getDoctorID() +
                        ", Date/Time: " + apt.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                        ", Status: " + apt.getStatus());
            }
        }
    }

    /**
     * Reschedules an existing appointment for the patient.
     *
     * @param patient the {@code Patient} rescheduling their appointment.
     * @throws IOException            if an error occurs while saving the appointment data.
     * @throws ClassNotFoundException if the appointment data file cannot be found or loaded.
     */

    public void rescheduleAppointment(Patient patient) throws IOException, ClassNotFoundException {
        PatientAppointmentController patientAppointmentController = new PatientAppointmentController();
        viewScheduledAppointments(patient);

        System.out.print("Enter the Appointment ID to reschedule: ");
        String appointmentID = scanner.nextLine();
        System.out.print("Enter new date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter new time (HH:MM): ");
        String time = scanner.nextLine();

        LocalDateTime newDateTime = LocalDateTime.parse(date + "T" + time);
        patientAppointmentController.rescheduleAppointment(patient, appointmentID, newDateTime);
    }

    /**
     * Cancels an existing appointment for the patient.
     *
     * @param patient the {@code Patient} canceling their appointment.
     * @throws IOException            if an error occurs while saving the appointment data.
     * @throws ClassNotFoundException if the appointment data file cannot be found or loaded.
     */

    public void cancelAppointment(Patient patient) throws IOException, ClassNotFoundException {
        PatientAppointmentController patientAppointmentController = new PatientAppointmentController();
        viewScheduledAppointments(patient);

        System.out.print("Enter the Appointment ID to cancel: ");
        String appointmentID = scanner.nextLine();
        patientAppointmentController.cancelAppointment(patient, appointmentID);
    }

    /**
     * Displays the past appointment outcomes for the patient, including services provided and prescribed medications.
     *
     * @param patient the {@code Patient} viewing their past appointment outcomes.
     * @throws IOException            if an error occurs while loading the appointment data.
     * @throws ClassNotFoundException if the appointment data file cannot be found or loaded.
     */
    
    public void viewPastAppointmentOutcomeRecords(Patient patient) throws IOException, ClassNotFoundException {
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        appointments.removeIf(apt -> !apt.getPatientID().equals(patient.getUserID()) || apt.getOutcomeRecord() == null);

        for (Appointment apt : appointments) {
            AppointmentOutcomeRecord record = apt.getOutcomeRecord();
            Prescription prescription = record.getPrescriptions().get(0);
            Medication medication = prescription.getMedication();

            System.out.println("Services Provided: " + record.getServiceType() +
                    " | Prescribed Medication: " + medication.getName() +
                    " | Consultation Notes: " + record.getConsultaionNotes());
        }
    }
}
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

        boolean hasSlots = false;
        System.out.println("Available Appointment Slots:");
        for (User user : users) {
            if (user instanceof Doctor doctor) {
                hasSlots = true;
                System.out.println(doctor.getUserID() + ": " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
                for (LocalDateTime slot : doctor.getAvailability()) {
                    System.out.println("- " + slot.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }
        }

        if (!hasSlots) {
            System.out.println("There are no available slots.");
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
                break;
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
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        boolean hasAppointment = false;
        System.out.println("Scheduled Appointments:");
        for (Appointment apt : appointments) {
            if (apt.getPatientID().equals(patient.getUserID())) {
                hasAppointment = true;
                System.out.println("ID: " + apt.getAppointmentID() + ", Doctor: " + apt.getDoctorID() +
                        ", Date/Time: " + apt.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                        ", Status: " + apt.getStatus());
            }
        }

        if (!hasAppointment) {
            System.out.println("You have no upcoming appointment(s).");
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
        viewAvailableAppointmentSlots();

        Scanner scanner = new Scanner(System.in);
        String appointmentID;
        String date;
        String time;
        LocalDateTime newDateTime = null;

        System.out.print("Enter the Appointment ID to reschedule: ");
        appointmentID = scanner.nextLine();

        while (true) {
            System.out.print("Enter new date (YYYY-MM-DD): ");
            date = scanner.nextLine();
            System.out.print("Enter new time (HH:MM): ");
            time = scanner.nextLine();

            String dateTimeString = date + " " + time;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            try {
                newDateTime = LocalDateTime.parse(dateTimeString, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date or time format. Please enter the date in YYYY-MM-DD and time in HH:MM format.");
            }
        }

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

        if (appointments.isEmpty()) {
            System.out.println("No past appointments with recorded outcomes.");
            return;
        }

        System.out.println("=== Past Appointment Outcome Records ===");
        for (Appointment apt : appointments) {
            if (apt.getStatus().equals("Completed")) {
                AppointmentOutcomeRecord record = apt.getOutcomeRecord();

                System.out.println("Appointment ID: " + apt.getAppointmentID());
                System.out.println("Services Provided: " + record.getServiceType());
                System.out.println("Consultation Notes: " + record.getConsultationNotes());

                System.out.println("Prescribed Medications:");
                for (Prescription prescription : record.getPrescriptions()) {
                    Medication medication = prescription.getMedication();
                    System.out.println("- " + medication.getName() + " (Status: " + prescription.getStatus() + ")");
                }
                System.out.println();
            }
        }
    }
}
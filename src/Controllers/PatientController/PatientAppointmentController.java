package Controllers.PatientController;

import DataManager.AppointmentsRepo;
import DataManager.UserRepo;
import Models.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Controllers.SmsController.SmsController;

/**
 * The {@code PatientAppointmentController} class is responsible for managing patient appointment operations.
 * This includes scheduling, rescheduling, and canceling appointments.
 */

public class PatientAppointmentController {

    /**
     * Schedules an appointment for a patient with a doctor at a specified time.
     *
     * @param doctorID       the ID of the doctor with whom the appointment is being scheduled.
     * @param appointmentTime the {@code LocalDateTime} of the desired appointment.
     * @param patient        the {@code Patient} scheduling the appointment.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if the user or appointment data cannot be loaded.
     */

    public void scheduleAppointment(String doctorID, LocalDateTime appointmentTime, Patient patient) throws IOException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        UserRepo userRepo = new UserRepo();
        userRepo.loadData();
        users = userRepo.getData();
        List<Doctor> doctors = new ArrayList<>();

        for (User user : users) {
            if (user instanceof Doctor doctor) {
                doctors.add(doctor);
            }
        }

        Doctor selectedDoctor = new Doctor();
        boolean doctorExists = false;

        for (Doctor doctor : doctors) {
            if (doctorID.equals(doctor.getUserID())) {
                doctorExists = true;
                selectedDoctor = doctor;
                break;
            }
        }

        if (!doctorExists) {
            System.out.println("Doctor not found");
            return;
        }

        List<LocalDateTime> availability = selectedDoctor.getAvailability();
        boolean isAvailable = true;

        for (LocalDateTime available : availability) {
            if (available.isEqual(appointmentTime)) {
                isAvailable = false;
                break;
            }
        }

        if (isAvailable) {
            System.out.println("The doctor is not available at the selected time!!");
            return;
        }

        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        String appointmentID = "APT" + (appointments.size() + 1);
        Appointment newAppointment = new Appointment(appointmentID, patient.getUserID(), selectedDoctor.getUserID(), appointmentTime, "Pending", new AppointmentOutcomeRecord());
        appointments.add(newAppointment);

        System.out.println("Appointment scheduled successfully.");
        appointmentsRepo.setAppointments(appointments);
        appointmentsRepo.saveData();

        selectedDoctor.getAvailability().remove(appointmentTime);
        userRepo.setUsers(users);
        userRepo.saveData();

        // Hiding the function until the day before presentation - since the my trial account got limited $$ to send SMS 
        SmsController.SendSMS("schedule", appointmentTime, selectedDoctor, patient);
    }

    /**
     * Reschedules an existing appointment for a patient to a new time.
     *
     * @param patient            the {@code Patient} rescheduling the appointment.
     * @param appointmentID      the ID of the appointment to be rescheduled.
     * @param newAppointmentTime the new {@code LocalDateTime} for the appointment.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if the user or appointment data cannot be loaded.
     */

    public void rescheduleAppointment(Patient patient, String appointmentID, LocalDateTime newAppointmentTime) throws IOException, ClassNotFoundException {
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        UserRepo userRepo = new UserRepo();
        List<Doctor> doctors = new ArrayList<>();
        userRepo.loadData();
        List<User> users = userRepo.getData();
        for (User user : users) {
            if (user instanceof Doctor doctor) {
                doctors.add(doctor);
            }
        }

        Appointment appointment = new Appointment();
        LocalDateTime previousTime = LocalDateTime.now();
        boolean appointmentExists = false;
        int appointmentIndex = 0;
        int size = appointments.size();

        for (; appointmentIndex < size; appointmentIndex++) {
            if (appointments.get(appointmentIndex).getAppointmentID().equals(appointmentID) && appointments.get(appointmentIndex).getPatientID().equals(patient.getUserID())) {
                appointment = appointments.get(appointmentIndex);
                previousTime = appointment.getAppointmentTime();
                appointmentExists = true;
                break;
            }
        }

        if (!appointmentExists) {
            System.out.println("Invalid Appointment ID!!");
            return;
        }

        Doctor selectedDoctor = new Doctor();
        int doctorsIndex = 0;

        for (; doctorsIndex < doctors.size(); doctorsIndex++) {
            if (doctors.get(doctorsIndex).getUserID().equals(appointment.getDoctorID())) {
                selectedDoctor = doctors.get(doctorsIndex);
                break;
            }
        }

        List<LocalDateTime> timeList = selectedDoctor.getAvailability();

        if (appointment != null && appointment.getPatientID().equals(patient.getUserID())) {
            boolean isAvailable = false;

            for (LocalDateTime localDateTime : timeList) {
                if (localDateTime.isEqual(newAppointmentTime)) {
                    isAvailable = true;
                    break;
                }
            }

            if (!isAvailable) {
                System.out.println("The doctor is not available at the selected time!!");
                return;
            }

            appointments.get(appointmentIndex).setAppointmentTime(newAppointmentTime);
            appointmentsRepo.saveData();
            doctors.get(doctorsIndex).getAvailability().add(previousTime);
            doctors.get(doctorsIndex).getAvailability().remove(newAppointmentTime);
            userRepo.saveData();
            System.out.println("Appointment rescheduled successfully.");

            // Hiding the function until the day before presentation - since the my trial account got limited $$ to send SMS 
            SmsController.SendSMS("reschedule", newAppointmentTime, selectedDoctor, patient);
        } else {
            System.out.println("Invalid appointment ID or not authorized to reschedule.");
        }
    }

    /**
     * Cancels an existing appointment for a patient.
     *
     * @param patient       the {@code Patient} canceling the appointment.
     * @param appointmentID the ID of the appointment to be canceled.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if the appointment data cannot be loaded.
     */
    
    public void cancelAppointment(Patient patient, String appointmentID) throws IOException, ClassNotFoundException {
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();
        boolean appointmentFound = false;
        int size = appointments.size();
        List<Doctor> doctors = new ArrayList<>();
        List<User> users = new ArrayList<>();
        UserRepo userRepo = new UserRepo();
        userRepo.loadData();
        users = userRepo.getData();
    
        for (User user : users) {
            if (user instanceof Doctor doctor) {
                doctors.add(doctor);
            }
        }

        for (int i = 0; i < size; i++) {
            Appointment apt = appointments.get(i);
            LocalDateTime appointmentDateTime = apt.getAppointmentTime();
            if (apt.getAppointmentID().equals(appointmentID) && apt.getPatientID().equals(patient.getUserID())) {
                if (apt.getStatus().equals("Completed")) {
                    System.out.println("Invalid action. Unable to delete completed appointment!");
                    return;
                }

                // Retrieves doctor object
                Doctor selectedDoctor = new Doctor();
                for (Doctor doctor : doctors) {
                    if (apt.getDoctorID().equals(doctor.getUserID())) {
                        selectedDoctor = doctor;
                        break;
                    }
                }
                selectedDoctor.getAvailability().add(appointmentDateTime);
                appointments.remove(i);
                appointmentFound = true;
                System.out.println("Appointment canceled successfully.");

                // Hiding the function until the day before presentation - since the my trial account got limited $$ to send SMS 
                SmsController.SendSMS("cancel", appointmentDateTime, selectedDoctor , patient);
                break;
            }
        }

        if (!appointmentFound) {
            System.out.println("Invalid Appointment ID or you are not authorized to cancel this appointment.");
        } else {
            userRepo.saveData();
            appointmentsRepo.setAppointments(appointments);
            appointmentsRepo.saveData();
        }


    }
}
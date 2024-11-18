# Table of Contents
1. Best practices
2. Changelog

## Best practices

1. Pull from remote repository before pushing changes to ensure up-to-date version.
```bash
# Run these commands in your shell environment to check for new changes in remote repository.
git remote update
git status
```

2. Remove existing data from `users.txt` to reset user credentials.

3. Put changes in messages of commits and/or changelog of README file


## Changelog

### V2.3 (LATEST)
- Exception handling for stock level, updated stock level, medication name for Administrator #4, #5 and Pharmacist #4
- Replenishment ID no longer null
- Approve replenishment request will add to medication stock level
- New method AdministratorInventoryController -> replenishMedication(). It is called by AdministratorController -> approveReplenishmentRequests()


### V2.2
- Fixed showAdministratorMenu.
- Fixed administatorStaffController -> updateStaff(). Now it correctly updates instead of creating a new user.

Current issues:
Pharmacist unable to view & prescribe medications from doctor's recordAppointmentOutcome.

### V2.1
- Fixed AdministratorStaffController -> viewAllStaff() from printing duplicates & now will only show all staffs, excluding patient.
- Added AdministratorController -> viewAllPatient() to view all patients.


### V2.0 
- DoctorAppointmentView -> viewUpcomingAppointments() now correctly shows all confirmed appointments ONLY.

Current issues:
Pharmacist unable to view & prescribe medications from doctor's recordAppointmentOutcome.

### V1.9
- PatientView -> viewMedicalRecord() for past treatment/diagnosis, will be displayed correctly once doctor recordAppointmentOutcome().

### V1.8 - Reset Password
- Added reset password feature at login screen (Main.java) - FIXED

### V1.7 - Reset Password 
- Added reset password feature at login screen (Main.java) - HALFWAY

### V1.6
- PatientAppointmentView -> viewAvailableAppointmentSlots() now shows "There are no available slots." if there are no available slots from doctors.
- PatientAppointmentView -> viewScheduledAppointment() now shows "You have no upcoming appointments" if there are no upcoming appointments.
- DoctorAppointmentController -> acceptDeclineAppointment() now shows the status for clarity.
- DoctorAppointmentController -> viewUpcomingAppointment() now shows the status for clarity.
- DoctorView -> viewPersonalSchedule() minor changes to print.

### V1.5 
- New patient appointment requests has status set to “Pending” 
- PatientMenu choice “6” -> cancelAppointment(): Unable to cancel “Completed” appointments
- PatientMenu choice “8” -> viewPastAppointmentOutcomeRecords(): Appointment records display correctly now, i.e. only “Completed” appointments
- DoctorMenu choice “5” -> acceptOrDeclineAppointment(): Now only show appointments that are pending
- DoctorMenu choice “7” -> DoctorController -> recordAppointmentOutcome(): When writing appointment outcome record, automatically sets status to “Completed”. 

Potential issues to look at:
- recordAppointmentOutcome can be called multiple times for the same appointment (i.e. doctor will create new appointmentOutcomeRecord for the same Appointment)
- reschedule appointment to consider not just the datetime but the doctor code as well. Because two doctors may have clashing timeslots

### V1.4 - Added SMS Service + Fixed CancelAppointment
- Fixed CancelAppointment(): Doctor's availability timing will be added back whenever there is an appointment that is cancelled
- Added SMS Service: Patient's scheduleAppointment(), rescheduleAppointment(), and cancelAppointment() will now send a text reminder message to the patient's phone number.
 However, since my Twilio account is on the TRIAL version with limited credit $$, I have temporary disabled this function until our meeting/group presentation demo.

### V1.3
- Inside PatientController -> updateContactInfo(): Updated contact information is now persistent and also immediately reflected
- Inside PatientAppointmentView -> scheduleAppointment(): Input validation for appointment scheduling i.e. correct datetime format and after current time
- Inside DoctorView -> setAvailability(): Input validation for doctor availability i.e. correct datetime format and after current time
- Inside DoctorController -> viewPatientRecord(): Previously did not show medical record, now shows medical record.

### V1.2 - input validation when Patient updates contact information
Modified updatePersonalInfo() in PatientView to have regex checks.

### V1.1 - with password hashing
Created LoginController & AuthenticationController for password hashing using SHA256 + Salt.
 (For future improvements, we can write it in a way to use alternative/better ways like Bcrypt instead of manually hashing using SHA256 + salt).

#### Files Modified for password hashing:

_Models:_
Administrator.java
Appointment.java
Doctor.java
Patient.java
User.java

_DataManagers:_
UserRepo.java
Controllers:LoginController.java
AuthenticationController.java
PatientController.java
AdministratorStaffController.java

_Views:_
PatientView.java
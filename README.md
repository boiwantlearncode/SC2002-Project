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

### V1.3 (LATEST)
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
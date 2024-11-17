## Changelog

### V1.1 - with password hashing (LATEST)
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
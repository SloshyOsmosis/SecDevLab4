package com.example.secdevlab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Hospital.db";

    //User Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FNAME = "fName";
    private static final String COLUMN_LNAME = "lName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    //Patient Table
    private static final String TABLE_PATIENTS = "patients";
    private static final String COLUMN_PATIENT_ID = "id";
    private static final String COLUMN_PATIENT_FNAME = "fName";
    private static final String COLUMN_PATIENT_LNAME = "lName";
    private static final String COLUMN_PATIENT_AGE = "age";
    private static final String COLUMN_PATIENT_GENDER = "gender";

    //Doctor Table
    private static final String TABLE_DOCTORS = "doctors";
    private static final String COLUMN_DOCTOR_ID = "id";
    private static final String COLUMN_DOCTOR_FNAME = "fName";
    private static final String COLUMN_DOCTOR_LNAME = "lName";

    private static final String COLUMN_DOCTOR_SPECIALTY = "specialty";


    //Appointment Table
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_APPOINTMENT_ID = "id";
    private static final String COLUMN_APPOINTMENT_PATIENT_ID = "patient_id";
    private static final String COLUMN_APPOINTMENT_DOCTOR_ID = "doctor_id";
    private static final String COLUMN_APPOINTMENT_DATE = "date";
    private static final String COLUMN_APPOINTMENT_TIME = "time";
    private static final String COLUMN_APPOINTMENT_REASON = "reason";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //User Table
        String CreateUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FNAME + " TEXT, "
                + COLUMN_LNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT UNIQUE, "
                + COLUMN_PASSWORD + " TEXT)";

        String CreatePatientsTable = "CREATE TABLE " + TABLE_PATIENTS + "("
                + COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PATIENT_FNAME + " TEXT, "
                + COLUMN_PATIENT_LNAME + " TEXT, "
                + COLUMN_PATIENT_AGE + " INTEGER, "
                + COLUMN_PATIENT_GENDER + " TEXT)";

        String CreateDoctorsTable = "CREATE TABLE " + TABLE_DOCTORS + "("
                + COLUMN_DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DOCTOR_FNAME + " TEXT, "
                + COLUMN_DOCTOR_LNAME + " TEXT, "
                + COLUMN_DOCTOR_SPECIALTY + " TEXT)";

        String CreateAppointmentsTable = "CREATE TABLE " + TABLE_APPOINTMENTS + "("
                + COLUMN_APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_APPOINTMENT_PATIENT_ID + " INTEGER, "
                + COLUMN_APPOINTMENT_DOCTOR_ID + " INTEGER, "
                + COLUMN_APPOINTMENT_DATE + " TEXT, "
                + COLUMN_APPOINTMENT_TIME + " TEXT, "
                + COLUMN_APPOINTMENT_REASON + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_APPOINTMENT_PATIENT_ID + ") REFERENCES " + TABLE_PATIENTS + "(" + COLUMN_PATIENT_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY(" + COLUMN_APPOINTMENT_DOCTOR_ID + ") REFERENCES " + TABLE_DOCTORS + "(" + COLUMN_DOCTOR_ID + ") ON DELETE CASCADE)"; // Add foreign key constraint

        db.execSQL(CreateUsersTable);
        db.execSQL(CreatePatientsTable);
        db.execSQL(CreateDoctorsTable);
        db.execSQL(CreateAppointmentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    public boolean insertPatient(String fName, String lName, int age, String gender) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATIENT_FNAME, fName);
        contentValues.put(COLUMN_PATIENT_LNAME, lName);
        contentValues.put(COLUMN_PATIENT_AGE, age);
        contentValues.put(COLUMN_PATIENT_GENDER, gender);
        long result = myDB.insert(TABLE_PATIENTS, null, contentValues);
        return result != -1;
    }

    public boolean insertDoctor(String fName, String lName, String specialty) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DOCTOR_FNAME, fName);
        contentValues.put(COLUMN_DOCTOR_LNAME, lName);
        contentValues.put(COLUMN_DOCTOR_SPECIALTY, specialty);
        long result = myDB.insert(TABLE_DOCTORS, null, contentValues);
        return result != -1;
    }

    public boolean insertData(String fName, String lName, String email, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FNAME, fName);
        contentValues.put(COLUMN_LNAME, lName);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = myDB.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public boolean insertAppointment(int patientId, int doctorId, String date, String time, String reason){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_APPOINTMENT_PATIENT_ID, patientId);
        contentValues.put(COLUMN_APPOINTMENT_DOCTOR_ID, doctorId);
        contentValues.put(COLUMN_APPOINTMENT_DATE, date);
        contentValues.put(COLUMN_APPOINTMENT_TIME, time);
        contentValues.put(COLUMN_APPOINTMENT_REASON, reason);
        long result = myDB.insert(TABLE_APPOINTMENTS, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String email, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from " + TABLE_USERS + " where email=? and password=?", new String[]{email,password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkUserEmail(String email){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from " + TABLE_USERS + " where email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public String getUserName(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT fName, lName FROM users WHERE id=?", new String[]{String.valueOf(userId)});

        String userName = "User"; // Default name
        if (cursor.moveToFirst()) {
            String fName = cursor.getString(cursor.getColumnIndexOrThrow("fName"));
            String lName = cursor.getString(cursor.getColumnIndexOrThrow("lName"));
            userName = fName + " " + lName;
        }
        cursor.close();
        return userName;
    }
    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PATIENTS, null);

        if (cursor.moveToFirst()) {
            do {

                patients.add(new Patient(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_FNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_LNAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_AGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_GENDER))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return patients;
    }

    public List<Doctor> getDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DOCTORS, null);

        if (cursor.moveToFirst()) {
            do {

                doctors.add(new Doctor(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_FNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_LNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_SPECIALTY))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return doctors;
    }

    public List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_APPOINTMENTS, null);
        if (cursor.moveToFirst()) {
            do {
                appointments.add(new Appointment(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_PATIENT_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DOCTOR_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_REASON))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return appointments;
    }

    public Patient getPatientById(int patientId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PATIENTS + " WHERE id=?", new String[]{String.valueOf(patientId)});
        Patient patient = null;
        if (cursor.moveToFirst()) {
            patient = new Patient(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_FNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_LNAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_GENDER)));
        }
        cursor.close();
        return patient;
    }

    public Doctor getDoctorById(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DOCTORS + " WHERE id=?", new String[]{String.valueOf(doctorId)});
        Doctor doctor = null;
        if (cursor.moveToFirst()) {
            doctor = new Doctor(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_FNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_LNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_SPECIALTY)));
        }
        cursor.close();
        return doctor;
    }

    public boolean updatePatient(int patientId, String firstName, String lastName, int age, String gender) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATIENT_FNAME, firstName);
        contentValues.put(COLUMN_PATIENT_LNAME, lastName);
        contentValues.put(COLUMN_PATIENT_AGE, age);
        contentValues.put(COLUMN_PATIENT_GENDER, gender);

        int result = myDB.update(TABLE_PATIENTS, contentValues, COLUMN_PATIENT_ID + "=?", new String[]{String.valueOf(patientId)});
        return result > 0;
    }

    public boolean updateDoctor(int doctorId, String firstName, String lastName, String specialty) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DOCTOR_FNAME, firstName);
        contentValues.put(COLUMN_DOCTOR_LNAME, lastName);
        contentValues.put(COLUMN_DOCTOR_SPECIALTY, specialty);

        int result = myDB.update(TABLE_DOCTORS, contentValues, COLUMN_DOCTOR_ID + "=?", new String[]{String.valueOf(doctorId)});
        return result > 0;
    }

    public boolean updateAppointment(int appointmentId, int patientId, int doctorId, String date, String time, String reason) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_APPOINTMENT_PATIENT_ID, patientId);
        contentValues.put(COLUMN_APPOINTMENT_DOCTOR_ID, doctorId);
        contentValues.put(COLUMN_APPOINTMENT_DATE, date);
        contentValues.put(COLUMN_APPOINTMENT_TIME, time);
        contentValues.put(COLUMN_APPOINTMENT_REASON, reason);
        int result = myDB.update(TABLE_APPOINTMENTS, contentValues, COLUMN_APPOINTMENT_ID + "=?", new String[]{String.valueOf(appointmentId)});
        return result > 0;
    }

    //Deletes the patient from the database from the ID.
    public boolean deletePatient(int patientId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int result = myDB.delete(TABLE_PATIENTS, COLUMN_PATIENT_ID + "=?", new String[]{String.valueOf(patientId)});
        return result > 0;
    }

    //Deletes the doctor from the database from the ID.
    public boolean deleteDoctor(int doctorId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int result = myDB.delete(TABLE_DOCTORS, COLUMN_DOCTOR_ID + "=?", new String[]{String.valueOf(doctorId)});
        return result > 0;
    }

    //Deletes the appointment from the database from the ID.
    public boolean deleteAppointment(int appointmentId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int result = myDB.delete(TABLE_APPOINTMENTS, COLUMN_APPOINTMENT_ID + "=?", new String[]{String.valueOf(appointmentId)});
        return result > 0;
    }
}
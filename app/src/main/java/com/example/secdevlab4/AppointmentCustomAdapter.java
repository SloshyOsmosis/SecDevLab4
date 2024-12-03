package com.example.secdevlab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AppointmentCustomAdapter extends BaseAdapter {
    private Context context;
    private List<Appointment> appointmentsList;

    private DBHelper myDB;

    public AppointmentCustomAdapter(Context context, List<Appointment> appointmentsList){
        this.context = context;
        this.appointmentsList = appointmentsList;
        this.myDB = new DBHelper(context);
    }

    @Override
    public int getCount() {return appointmentsList.size();}

    @Override
    public Object getItem(int position) {return appointmentsList.get(position);}

    @Override
    public long getItemId(int position){
        return appointmentsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.appointment_item, parent, false);
        }

        TextView patientNameTextView = convertView.findViewById(R.id.patientName);
        TextView patientAgeTextView = convertView.findViewById(R.id.patientAge);
        TextView patientGenderTextView = convertView.findViewById(R.id.patientGender);
        TextView doctorNameTextView = convertView.findViewById(R.id.doctorName);
        TextView doctorSpecialtyTextView = convertView.findViewById(R.id.doctorSpecialty);
        TextView appointmentidTextView = convertView.findViewById(R.id.appointmentId);
        TextView appointmentdateTextView = convertView.findViewById(R.id.appointmentDate);
        TextView appointmenttimeTextView = convertView.findViewById(R.id.appointmentTime);
        TextView appointmentreasonTextView = convertView.findViewById(R.id.appointmentReason);

        Appointment appointment = appointmentsList.get(position);

        appointmentidTextView.setText("Appointment ID: " + appointment.getId());
        appointmentreasonTextView.setText("Reason: " + appointment.getReason());
        appointmentdateTextView.setText("Date: " + appointment.getDate());
        appointmenttimeTextView.setText("Time: " + appointment.getTime());

        //Fetch details from the database for patient and doctor.
        Patient patient = myDB.getPatientById(appointment.getPatientId());
        Doctor doctor = myDB.getDoctorById(appointment.getDoctorId());

        if (patient != null) {
            patientNameTextView.setText("Patient Name: " + patient.getFName() + " " + patient.getLName());
            patientAgeTextView.setText("Age: " + patient.getAge());
            patientGenderTextView.setText("Gender: " + patient.getGender());
        } else {
            patientNameTextView.setText("Patient Name: Unknown");
            patientAgeTextView.setText("Age: Unknown");
            patientGenderTextView.setText("Gender: Unknown");
        }

        if (doctor != null) {
            doctorNameTextView.setText("Doctor Name: " + doctor.getFName() + " " + doctor.getLName());
            doctorSpecialtyTextView.setText("Specialty: " + doctor.getSpecialty());
        } else {
            doctorNameTextView.setText("Doctor Name: Unknown");
            doctorSpecialtyTextView.setText("Specialty: Unknown");
        }

        return convertView;
    }
}
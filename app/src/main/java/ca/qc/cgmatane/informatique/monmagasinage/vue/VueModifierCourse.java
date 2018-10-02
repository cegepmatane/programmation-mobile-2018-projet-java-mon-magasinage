package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Notification;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueModifierCourse extends AppCompatActivity {

    private CourseDAO courseDAO ;
    private MagasinDAO magasinDAO;
    private EditText nomCourse;
    private EditText dateNotification;
    private Spinner spinnerMagasin;
    private Calendar currentTimeCalendar = Calendar.getInstance(TimeZone.getDefault());
    private final Calendar dateNotificationCalendar = Calendar.getInstance(TimeZone.getDefault());
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_course);

        courseDAO = CourseDAO.getInstance();
        magasinDAO = MagasinDAO.getInstance();
        Bundle parametres = this.getIntent().getExtras();
        String parametreIdCourse = (String) parametres.get(Course.CHAMP_ID_COURSE);
        int idCourse = Integer.parseInt(parametreIdCourse);

        final Course courseAModifier = courseDAO.getListeCourses().trouverAvecId(idCourse);

        nomCourse = findViewById(R.id.vue_modifier_course_nom_course);
        nomCourse.setText(courseAModifier.getNom());

        String dateNotificationFormatted="";
        if (courseAModifier.getDateNotification() != null)
            dateNotificationFormatted = courseAModifier.getDateNotification().format(formatter);
        
        dateNotification = findViewById(R.id.vue_modifier_course_date_notification);
        dateNotification.setText(dateNotificationFormatted);

        //set spinner value
        spinnerMagasin = findViewById(R.id.vue_modifier_course_spinner_produit);
        spinnerMagasin.setAdapter(magasinDAO.getListeMagasins().recuperereListeMagasinPourSpinner(this));
        spinnerMagasin.setSelection(magasinDAO.getListeMagasins().retournerPositionMagasinDansListe(courseAModifier.getMonMagasin().getId()));

        Button actionNaviguerEnregistrerCourse = (Button) findViewById(R.id.vue_modifier_course_action_enregistrer);

        actionNaviguerEnregistrerCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(nomCourse.getText().toString())){
                    //TODO ajouter idOriginal
                    courseDAO.modifierCourse(courseAModifier.getId(),
                            nomCourse.getText().toString(),
                            dateNotification.getText().toString(),
                            "",
                            0,
                            magasinDAO.getListeMagasins().get(spinnerMagasin.getSelectedItemPosition()));

                    //Notification
                    ComponentName nomServiceNotification = new ComponentName(getApplicationContext(),
                            Notification.class);

                    long offset = dateNotificationCalendar.getTimeInMillis()-currentTimeCalendar.getTimeInMillis();
                    offset /= 60;
                    Log.d("timee date nottification", String.valueOf(dateNotificationCalendar.getTimeInMillis()));
                    Log.d("timee current", String.valueOf(currentTimeCalendar.getTimeInMillis()));
                    Log.d("offsetNotificationTime", String.valueOf(offset));

                    PersistableBundle bundle = new PersistableBundle();
                    bundle.putString("titre", nomCourse.getText().toString());
                    bundle.putString("text", "va faire tes courses");

                    JobInfo.Builder jobInfo = new JobInfo.Builder(1, nomServiceNotification);
                    jobInfo.setMinimumLatency(offset);
                    //jobInfo.setOverrideDeadline(6000);
                    jobInfo.setExtras(bundle);

                    JobScheduler mSchedular = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
                    mSchedular.schedule(jobInfo.build());




                    Log.d("notif", "start notif");

                    finish();
                }else {
                    Toast message = Toast.makeText(getApplicationContext(), //display toast message
                            "Vous devez choisir un nom", Toast.LENGTH_SHORT);
                    message.show();
                }

            }
        });

        dateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(VueModifierCourse.this, date,
                        currentTimeCalendar.get(Calendar.YEAR), currentTimeCalendar.get(Calendar.MONTH),
                        currentTimeCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateNotificationCalendar.set(Calendar.HOUR, hourOfDay);
            dateNotificationCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateNotificationCalendar.set(Calendar.MINUTE, minute);
            updateLabel();
        }

    };


    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            dateNotificationCalendar.set(Calendar.YEAR, year);
            dateNotificationCalendar.set(Calendar.MONTH, monthOfYear);
            dateNotificationCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(VueModifierCourse.this, time, currentTimeCalendar.get(Calendar.HOUR),
                    currentTimeCalendar.get(Calendar.MINUTE), true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }

    };



    private void updateLabel(){
        String myFormat = "yyyy-MM-dd-HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        dateNotification.setText(sdf.format(dateNotificationCalendar.getTime()));
    }

}

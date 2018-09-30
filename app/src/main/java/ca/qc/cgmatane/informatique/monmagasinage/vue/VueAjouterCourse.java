package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueAjouterCourse extends AppCompatActivity {

    private final int AJOUTER_PRODUIT_ID_RETOUR=0;

    private CourseDAO courseDAO = CourseDAO.getInstance();
    private MagasinDAO magasinDAO = MagasinDAO.getInstance();
    private final Calendar myCalendar = Calendar.getInstance(TimeZone.getDefault());
    private EditText dateNotification;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_course);

        final EditText nomCourse = findViewById(R.id.vue_ajouter_course_nom_course);
        dateNotification = findViewById(R.id.vue_ajouter_course_date_notification);
        final Spinner spinnerMagasin = findViewById(R.id.vue_ajouter_course_spinner_produit);
        Button actionNaviguerAjouterProduitCourse = (Button) findViewById(R.id.vue_ajouter_course_action_ajouter_produit);

        spinnerMagasin.setAdapter(magasinDAO.getListeMagasins().recuperereListeMagasinPourSpinner(this));

        final Course course = new Course();
        //int id, String nom, LocalDateTime dateNotification, LocalDateTime dateRealisation

        actionNaviguerAjouterProduitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionNaviguerAjouterProduitCourse = new Intent(VueAjouterCourse.this, VueAjouterProduitListeCourse.class);
                ArrayList<Integer> produits = new ArrayList<>();
                produits.add(1);
                produits.add(3);
                produits.add(4);
                intentionNaviguerAjouterProduitCourse.putExtra("course", produits);
                startActivityForResult(intentionNaviguerAjouterProduitCourse, AJOUTER_PRODUIT_ID_RETOUR);
            }
        });

        Button actionNaviguerEnregistrerCourse = (Button) findViewById(R.id.vue_ajouter_course_action_enregistrer);

        actionNaviguerEnregistrerCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(nomCourse.getText().toString())){
                    courseDAO.creerCourse(nomCourse.getText().toString(),
                            dateNotification.getText().toString(),
                            null,
                            0,
                            magasinDAO.getListeMagasins().get(spinnerMagasin.getSelectedItemPosition()));
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

                new DatePickerDialog(VueAjouterCourse.this, date,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR, hourOfDay);
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateLabel();
        }

    };


    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(VueAjouterCourse.this, time, calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE), true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }

    };



    private void updateLabel(){
        String myFormat = "yyyy-MM-dd-HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        dateNotification.setText(sdf.format(myCalendar.getTime()));
    }
}

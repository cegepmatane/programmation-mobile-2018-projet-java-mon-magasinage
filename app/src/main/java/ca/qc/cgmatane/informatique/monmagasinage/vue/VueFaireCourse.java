package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneFaireCourseAdapter;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.LigneCourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.ShakeDetector;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueFaireCourse extends AppCompatActivity {

    private static final int ACTIVITE_RESULTAT_PRISE_PHOTO = 1;

    private Course courseActuelle;
    protected ListViewLigneFaireCourseAdapter listViewLigneFaireCourseAdapter;
    protected ListView listViewPanier;
    protected CourseDAO courseDAO;
    protected LigneCourseDAO ligneCourseDAO;
    protected Button actionTerminerCourse;

    /** Secouer*/
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_faire_course);

        Bundle parametres = this.getIntent().getExtras();
        assert parametres != null;
        String parametreIdCourse = (String) parametres.get(Course.CHAMP_ID_COURSE);
        int idCourse = Integer.parseInt(parametreIdCourse);

        courseDAO = CourseDAO.getInstance();
        ligneCourseDAO = LigneCourseDAO.getInstance();

        listViewPanier =(ListView) findViewById(R.id.vue_faire_course_list_view_panier);
        actionTerminerCourse = findViewById(R.id.vue_faire_course_terminer);

        courseActuelle = courseDAO.getListeCourses().trouverAvecId(idCourse);
        ligneCourseDAO.chargerListeLigneCoursePourUneCourse(courseActuelle);

     /*   Toast message = Toast.makeText(getApplicationContext(), ""+courseActuelle.getMesLignesCourse().size(), Toast.LENGTH_SHORT);
        message.show();*/
        listViewLigneFaireCourseAdapter = new ListViewLigneFaireCourseAdapter(courseActuelle, VueFaireCourse.this);
        listViewPanier.setAdapter(listViewLigneFaireCourseAdapter);

        // Secouer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                Toast message = Toast.makeText(getApplicationContext(), //display toast message
                        "Tu as secoué, on décoche tout !!", Toast.LENGTH_SHORT);
                message.show();
                ligneCourseDAO.deocherUneCourseEntiere(courseActuelle);
                rechargerActivite();
            }
        });

        actionTerminerCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, ACTIVITE_RESULTAT_PRISE_PHOTO);
        }
    }

    protected void onActivityResult(int activite, int resultat, Intent donnees){
        switch (activite){
            case ACTIVITE_RESULTAT_PRISE_PHOTO:
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    protected void rechargerActivite(){
        Intent refresh = new Intent(this, VueFaireCourse.class);
        refresh.putExtra(Course.CHAMP_ID_COURSE, courseActuelle.getId() + "");
        startActivity(refresh);
        this.finish();
    }
}

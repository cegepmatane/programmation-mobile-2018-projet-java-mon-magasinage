package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
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
import ca.qc.cgmatane.informatique.monmagasinage.modele.gesture.ImageSaver;
import ca.qc.cgmatane.informatique.monmagasinage.modele.gesture.ShakeDetector;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class VueFaireCourse extends AppCompatActivity {

    private static final int ACTIVITE_RESULTAT_PRISE_PHOTO = 1;
    private static final int ACTIVITE_RESULTAT_NAVIGUER_VERS_VALIDER_COURSE = 2;
    private static final int REQUETE_PERMISSION_WRITE_STORAGE = 102;

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

    /** Photo */
    protected String mCurrentPhotoPath;

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

        listViewLigneFaireCourseAdapter = new ListViewLigneFaireCourseAdapter(courseActuelle, VueFaireCourse.this);
        listViewPanier.setAdapter(listViewLigneFaireCourseAdapter);

        // Evenement sur le secoué ou shake
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
                prendrePhoto();
            }
        });
    }

    /*private void dispatchTakePictureIntent(){
        Intent intentionFairePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentionFairePhoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentionFairePhoto, ACTIVITE_RESULTAT_PRISE_PHOTO);
        }
    }
*/
    public void prendrePhoto(){
        Intent intentionFairePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentionFairePhoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentionFairePhoto, ACTIVITE_RESULTAT_PRISE_PHOTO);
        }
    }

    protected void onActivityResult(int activite, int resultat, Intent donnees){

        if (activite == ACTIVITE_RESULTAT_PRISE_PHOTO && resultat == RESULT_OK) {

            Bitmap imageBitmap = (Bitmap) donnees.getExtras().get("data");
            sauvegarderPhoto(imageBitmap);
            /*Intent intentionNaviguerVersVueValiderCourse = new Intent(VueFaireCourse.this, VueValiderFaireCourse.class);
            intentionNaviguerVersVueValiderCourse.putExtra("photo", imageBitmap);
            startActivityForResult(intentionNaviguerVersVueValiderCourse, ACTIVITE_RESULTAT_NAVIGUER_VERS_VALIDER_COURSE);*/

       /*
        Intent intentionNaviguerVersVueValiderCourse = new Intent(VueFaireCourse.this, VueValiderFaireCourse.class);
        Bundle extras = donnees.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        if(imageBitmap != null){
            intentionNaviguerVersVueValiderCourse.putExtra("photo", imageBitmap);
            startActivityForResult(intentionNaviguerVersVueValiderCourse, ACTIVITE_RESULTAT_NAVIGUER_VERS_VALIDER_COURSE);
        }*/

        }


    }

    private void sauvegarderPhoto(Bitmap photoEnBitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        File monDossier = new File (root + "/imageMonMagasinage");
        monDossier.mkdir();
        String nomFichier = "TiquetCourse"+courseActuelle.getId() + ".jpg";
        ImageSaver imageSaver = new ImageSaver(this, nomFichier);
        imageSaver.save(photoEnBitmap);
       /* String nomFichier = "TiquetCourse"+courseActuelle.getId() *//*+ "-"+courseActuelle.getNom().replace(" ","_")*//* + ".jpg";
        File fichier = new File (monDossier, nomFichier);
        if(fichier.exists()){
            fichier.delete();
        }
        FileOutputStream out = null;

        try{
            out = new FileOutputStream(fichier);
            photoEnBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

            out.flush();
            out.close();
            Toast message = Toast.makeText(getApplicationContext(), //display toast message
                    "j'ai ma photo", Toast.LENGTH_SHORT);
            message.show();
        }catch (Exception e){
            e.printStackTrace();
            Toast message = Toast.makeText(getApplicationContext(), //display toast message
                    "Erreur sur l'enregistrement de la photo", Toast.LENGTH_SHORT);
            message.show();
        }*/
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

    private void rechargerActivite(){
        Intent refresh = new Intent(this, VueFaireCourse.class);
        refresh.putExtra(Course.CHAMP_ID_COURSE, courseActuelle.getId() + "");
        startActivity(refresh);
        this.finish();
    }

    protected void demanderPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(VueFaireCourse.this, new String[]{Manifest.permission.CAMERA},1);
            }
        }
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VueFaireCourse.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUETE_PERMISSION_WRITE_STORAGE);
        }
        else {
            prendrePhoto();
            }
        }

}

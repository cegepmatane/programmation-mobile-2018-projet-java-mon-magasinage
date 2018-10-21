package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.*;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneCourseAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewProduitAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.LigneCourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.ProduitDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Notification;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class VueModifierCourse extends AppCompatActivity {
    /** Données*/
    private CourseDAO courseDAO ;
    private MagasinDAO magasinDAO;
    private ProduitDAO produitDAO;
    private LigneCourseDAO ligneCourseDAO;
    private Produits listeProduits;
    private Course courseAModifier;

    /** Affichage*/
    private EditText nomCourse;
    private EditText dateNotification;
    private Spinner spinnerMagasin;
    private Calendar currentTimeCalendar = Calendar.getInstance(TimeZone.getDefault());
    private final Calendar dateNotificationCalendar = Calendar.getInstance(TimeZone.getDefault());
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
    protected ListView listeviewProduits;
    protected String rechercheUtilisateur ="";
    protected ToggleButton actionTogglePanier;
    protected ListViewProduitAdaptater listViewProduitAdaptater;
    protected ListViewLigneCourseAdaptater listViewLigneCourseAdaptater;
    protected TextView recapitualtifPanier;
    private LignesCourse ligneCourseDeSauvegarde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_course);
        getSupportActionBar().setTitle("Modifier une course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseDAO = CourseDAO.getInstance();
        magasinDAO = MagasinDAO.getInstance();
        produitDAO = ProduitDAO.getInstance();
        ligneCourseDAO = LigneCourseDAO.getInstance();
        Bundle parametres = this.getIntent().getExtras();
        String parametreIdCourse = (String) parametres.get(Course.CHAMP_ID_COURSE);
        int idCourse = Integer.parseInt(parametreIdCourse);

        courseAModifier = courseDAO.getListeCourses().trouverAvecId(idCourse);
        if(courseAModifier.getMesLignesCourse().size()<1)
        ligneCourseDAO.chargerListeLigneCoursePourUneCourse(courseAModifier);

        nomCourse = findViewById(R.id.vue_modifier_course_nom_course);
        dateNotification = findViewById(R.id.vue_modifier_course_date_notification);
        spinnerMagasin = findViewById(R.id.vue_modifier_course_spinner_produit);
        Button actionNaviguerEnregistrerCourse = (Button) findViewById(R.id.vue_modifier_course_action_enregistrer);
        SearchView barreDeRecherche = findViewById(R.id.vue_modifier_course_barre_recherche);
        recapitualtifPanier = findViewById(R.id.vue_modifier_course_recapitulatif_panier);
        listeviewProduits =(ListView) findViewById(R.id.vue_modifier_course_liste_produits);
        actionTogglePanier = findViewById(R.id.vue_modifier_course_toggle_panier);

        /** Affichage des valeurs de la course sélectionnée*/
        nomCourse.setText(courseAModifier.getNom());
        String dateNotificationFormatted="";
        if (courseAModifier.getDateNotification() != null)
            dateNotificationFormatted = courseAModifier.getDateNotification().format(formatter);
        dateNotification.setText(dateNotificationFormatted);
        //set spinner value
        spinnerMagasin.setAdapter(magasinDAO.getListeMagasins().recuperereListeMagasinPourSpinner(this));
        spinnerMagasin.setSelection(magasinDAO.getListeMagasins().retournerPositionMagasinDansListe(courseAModifier.getMonMagasin().getId()));

        recapitualtifPanier.setText("Produits : "+courseAModifier.getMesLignesCourse().recupererQuantiteTotal());
        ligneCourseDeSauvegarde= courseAModifier.getMesLignesCourse().creerListeParValeur();

        listeProduits = new Produits();
        listViewProduitAdaptater = new ListViewProduitAdaptater(listeProduits, courseAModifier, VueModifierCourse.this);
        listViewLigneCourseAdaptater = new ListViewLigneCourseAdaptater(courseAModifier.getMesLignesCourse(),courseAModifier, VueModifierCourse.this);
        actualiserAffichageAvecListeProduits();

        /** Validation*/
        actionNaviguerEnregistrerCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(nomCourse.getText().toString())){

                    courseDAO.modifierCourse(courseAModifier.getId(),
                            nomCourse.getText().toString(),
                            dateNotification.getText().toString(),
                            "",
                            courseAModifier.getIdCourseOriginal(),
                            magasinDAO.getListeMagasins().get(spinnerMagasin.getSelectedItemPosition()));

                    //Notification
                    ComponentName nomServiceNotification = new ComponentName(getApplicationContext(),
                            Notification.class);

                    long offset = dateNotificationCalendar.getTimeInMillis()-Calendar.getInstance().getTimeInMillis();
                    //offset /= 6 ;
                    Log.d("timee date nottification", String.valueOf(dateNotificationCalendar.getTimeInMillis()));
                    Log.d("timee current", String.valueOf(currentTimeCalendar.getTimeInMillis()));
                    Log.d("offsetNotificationTime", String.valueOf(offset));

                    PersistableBundle bundle = new PersistableBundle();
                    bundle.putString("titre", nomCourse.getText().toString());
                    bundle.putString("text", "va faire tes courses");
                    bundle.putInt("id", courseAModifier.getId());

                    //suppresion ancienne notification
                    JobScheduler mSchedular = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
                    mSchedular.cancel(courseAModifier.getId());

                    //creation nouvelle notification
                    JobInfo.Builder jobInfo = new JobInfo.Builder(1, nomServiceNotification);
                    jobInfo.setMinimumLatency(offset);
                    jobInfo.setExtras(bundle);

                    mSchedular.schedule(jobInfo.build());

                    Log.d("notif", "start notif");
                    ligneCourseDeSauvegarde = courseAModifier.getMesLignesCourse();
                    finish();
                }else {
                    Toast message = Toast.makeText(getApplicationContext(), //display toast message
                            "Vous devez choisir un nom", Toast.LENGTH_SHORT);
                    message.show();
                }

            }
        });

        /** Affichage dynamique*/
        dateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(VueModifierCourse.this, date,
                        currentTimeCalendar.get(Calendar.YEAR), currentTimeCalendar.get(Calendar.MONTH),
                        currentTimeCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        actionTogglePanier.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    actualiserAffichageAvecPanier();

                }else {
                    actualiserAffichageAvecListeProduits();
                }
            }
        });

        barreDeRecherche.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rechercheUtilisateur = newText;
                if(actionTogglePanier.isChecked()){
                    actualiserAffichageAvecPanier();
                }else {
                    actualiserAffichageAvecListeProduits();
                }
                return true;
            }
        });

        dateNotification.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    dateNotification.setText("");
                    return true;
                }
            }
        );

        barreDeRecherche.setActivated(true);
        barreDeRecherche.setQueryHint("Nom du produit");
        barreDeRecherche.onActionViewExpanded();
        barreDeRecherche.setIconified(false);
        barreDeRecherche.clearFocus();


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(VueAjouterCourse.EVENT_RECHARGER_AFFICHAGE));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            if(message.equals("panier")){
                actualiserAffichageAvecPanier();
            }else {
                actualiserAffichageAvecListeProduits();
            }
            recapitualtifPanier.setText("Produits : " +courseAModifier.getMesLignesCourse().recupererQuantiteTotal());
        }
    };

    @Override
    protected void onDestroy(){
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void actualiserAffichageAvecListeProduits() {
        listeProduits.clear();
        for(Produit produit: produitDAO.getListeProduits()){
            if(produit.getNom().toLowerCase().contains(rechercheUtilisateur.toLowerCase())){
                listeProduits.add(produit);
            }
        }
        listViewProduitAdaptater.setListeProduits(listeProduits);
        listeviewProduits.setAdapter(listViewProduitAdaptater);
    }

    private void actualiserAffichageAvecPanier(){
        LignesCourse ligneCourses = new LignesCourse();
        for(LigneCourse ligneCourse: courseAModifier.getMesLignesCourse()){
            if(ligneCourse.getProduit().getNom().toLowerCase().contains(rechercheUtilisateur.toLowerCase())){
                ligneCourses.add(ligneCourse);
            }
        }
        listViewLigneCourseAdaptater.setPanier(ligneCourses);
        listeviewProduits.setAdapter(listViewLigneCourseAdaptater);
    }

    final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateNotificationCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateNotificationCalendar.set(Calendar.MINUTE, minute);
            dateNotificationCalendar.set(Calendar.SECOND,0);
            dateNotificationCalendar.set(Calendar.MILLISECOND,0);
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
            mTimePicker = new TimePickerDialog(VueModifierCourse.this, time, currentTimeCalendar.get(Calendar.HOUR_OF_DAY),
                    currentTimeCalendar.get(Calendar.MINUTE), true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }

    };

    private void updateLabel(){
        Toast.makeText(this, (String)"alarme ajoutée à "
                        + dateNotificationCalendar.get(Calendar.HOUR_OF_DAY)
                        + ":" + dateNotificationCalendar.get(Calendar.MINUTE)
                        + " le " + dateNotificationCalendar.get(Calendar.DAY_OF_MONTH)
                        + "/" + dateNotificationCalendar.get(Calendar.MONTH)
                        + "/" + dateNotificationCalendar.get(Calendar.YEAR),
                Toast.LENGTH_LONG).show();
        String myFormat = "yyyy-MM-dd-HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        dateNotification.setText(sdf.format(dateNotificationCalendar.getTime()));
    }

    public void onStop(){
        courseAModifier.setMesLignesCourse(ligneCourseDeSauvegarde);
        super.onStop();
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

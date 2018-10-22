package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class VueAjouterCourse extends AppCompatActivity {
    public static final String EVENT_RECHARGER_AFFICHAGE= "event_recharger_affichage";

    /** Données*/
    private CourseDAO courseDAO = CourseDAO.getInstance();
    private MagasinDAO magasinDAO = MagasinDAO.getInstance();
    private ProduitDAO produitDAO = ProduitDAO.getInstance();
    private Produits listeProduits;
    private Course courseActuelle;

    /** Affichage*/
    protected EditText dateNotification;
    protected ListView listeviewProduits;
    protected String rechercheUtilisateur ="";
    protected ToggleButton actionTogglePanier;
    protected ListViewProduitAdaptater listViewProduitAdaptater;
    protected ListViewLigneCourseAdaptater listViewLigneCourseAdaptater;
    protected TextView recapitualtifPanier;

    /** Notification*/
    private final Calendar dateNotificationCalendar = Calendar.getInstance(TimeZone.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_course);
        getSupportActionBar().setTitle("Ajouter une course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText nomCourse = findViewById(R.id.vue_ajouter_course_nom_course);
        dateNotification = findViewById(R.id.vue_ajouter_course_date_notification);
        final Spinner spinnerMagasin = findViewById(R.id.vue_ajouter_course_spinner_produit);
        listeviewProduits =(ListView) findViewById(R.id.vue_ajouter_course_liste_produits);
        SearchView barreDeRecherche = findViewById(R.id.vue_ajouter_course_barre_recherche);
        Button actionNaviguerEnregistrerCourse = (Button) findViewById(R.id.vue_ajouter_course_action_enregistrer);
        recapitualtifPanier = findViewById(R.id.vue_ajouter_course_recapitulatif_panier);
        actionTogglePanier = findViewById(R.id.vue_ajouter_course_toggle_panier);

        /** Affichage de base*/
        listeProduits = new Produits();
        courseActuelle = new Course();
        listViewProduitAdaptater = new ListViewProduitAdaptater(listeProduits, courseActuelle,this);
        listViewLigneCourseAdaptater = new ListViewLigneCourseAdaptater(courseActuelle.getMesLignesCourse(),courseActuelle, this);
        actualiserAffichageAvecListeProduits();

        spinnerMagasin.setAdapter(magasinDAO.getListeMagasins().recuperereListeMagasinPourSpinner(this));

        /** Validation*/
        actionNaviguerEnregistrerCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(nomCourse.getText().toString())){
                    int nouvelleId = courseDAO.creerCourse(nomCourse.getText().toString(),
                            dateNotification.getText().toString(),
                            "",
                            0,
                            magasinDAO.getListeMagasins().get(spinnerMagasin.getSelectedItemPosition()), courseActuelle.getMesLignesCourse());


                    if (!"".equals(dateNotification.getText().toString())) {
                    /** Creation des Notifications*/
                    ComponentName nomServiceNotification = new ComponentName(getApplicationContext(),
                            Notification.class);
                        long offset = dateNotificationCalendar.getTimeInMillis() - Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis();

                        PersistableBundle bundle = new PersistableBundle();
                        bundle.putString("titre", nomCourse.getText().toString());
                        bundle.putString("text", "va faire tes courses");
                        bundle.putInt("id", nouvelleId);

                        JobInfo.Builder jobInfo = new JobInfo.Builder(nouvelleId, nomServiceNotification);
                        jobInfo.setMinimumLatency(offset);
                        jobInfo.setExtras(bundle);

                        JobScheduler mSchedular = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
                        mSchedular.schedule(jobInfo.build());
                    }
                    finish();
                }else {
                    Toast message = Toast.makeText(getApplicationContext(), //display toast message
                            "Vous devez choisir un nom", Toast.LENGTH_SHORT);
                    message.show();
                }

            }
        });

        /** Affichage dynamique*/
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

        barreDeRecherche.setActivated(true);
        barreDeRecherche.setQueryHint("Nom du produit");
        barreDeRecherche.onActionViewExpanded();
        barreDeRecherche.setIconified(false);
        barreDeRecherche.clearFocus();

        /** changement de la date de notification*/
        dateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(VueAjouterCourse.this, date,
                        dateNotificationCalendar.get(Calendar.YEAR),
                        dateNotificationCalendar.get(Calendar.MONTH),
                        dateNotificationCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(VueAjouterCourse.EVENT_RECHARGER_AFFICHAGE));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            if(message.equals("panier")){
                actualiserAffichageAvecPanier();
            }else {
                actualiserAffichageAvecListeProduits();
            }
            recapitualtifPanier.setText("Produits : " +courseActuelle.getMesLignesCourse().recupererQuantiteTotal());
        }
    };

    @Override
    protected void onDestroy(){
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateNotificationCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateNotificationCalendar.set(Calendar.MINUTE, minute);
            dateNotificationCalendar.set(Calendar.SECOND,0);
            dateNotificationCalendar.set(Calendar.MILLISECOND,0);
            actualiserLabelDate();
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
            mTimePicker = new TimePickerDialog(VueAjouterCourse.this, time, dateNotificationCalendar.get(Calendar.HOUR_OF_DAY),
                    dateNotificationCalendar.get(Calendar.MINUTE), true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }

    };

    private void actualiserLabelDate(){
        String myFormat = "yyyy-MM-dd-HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateNotification.setText(sdf.format(dateNotificationCalendar.getTime()));
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
        for(LigneCourse ligneCourse: courseActuelle.getMesLignesCourse()){
            if(ligneCourse.getProduit().getNom().toLowerCase().contains(rechercheUtilisateur.toLowerCase())){
                ligneCourses.add(ligneCourse);
            }
        }
        listViewLigneCourseAdaptater.setPanier(ligneCourses);
        listeviewProduits.setAdapter(listViewLigneCourseAdaptater);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}

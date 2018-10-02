package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneCourseAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewProduitAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.ProduitDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;

public class VueAjouterCourse extends AppCompatActivity {
    public static final String EVENT_RECHARGER_AFFICHAGE= "event_recharger_affichage";

    /** Données*/
    private CourseDAO courseDAO = CourseDAO.getInstance();
    private MagasinDAO magasinDAO = MagasinDAO.getInstance();
    private ProduitDAO produitDAO = ProduitDAO.getInstance();
    private Produits listeProduits;
    private Course courseActuelle;

    /** Affichage*/
    protected final Calendar myCalendar = Calendar.getInstance(TimeZone.getDefault());
    protected EditText dateNotification;
    protected Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    protected ListView listeviewProduits;
    protected String rechercheUtilisateur ="";
    protected ToggleButton actionTogglePanier;
    protected ListViewProduitAdaptater listViewProduitAdaptater;
    protected ListViewLigneCourseAdaptater listViewLigneCourseAdaptater;
    protected TextView recapitualtifPanier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_course);

        final EditText nomCourse = findViewById(R.id.vue_ajouter_course_nom_course);
        dateNotification = findViewById(R.id.vue_ajouter_course_date_notification);
        final Spinner spinnerMagasin = findViewById(R.id.vue_ajouter_course_spinner_produit);
        listeviewProduits =(ListView) findViewById(R.id.vue_ajouter_course_liste_produits);
        SearchView barreDeRecherche = findViewById(R.id.vue_ajouter_course_barre_recherche);
        Button actionNaviguerEnregistrerCourse = (Button) findViewById(R.id.vue_ajouter_course_action_enregistrer);
        recapitualtifPanier = findViewById(R.id.vue_ajouter_course_recapitulatif_panier);

        actionTogglePanier = findViewById(R.id.vue_ajouter_course_toggle_panier);
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

        listeProduits = new Produits();
        courseActuelle = new Course();
        listViewProduitAdaptater = new ListViewProduitAdaptater(listeProduits, courseActuelle,this);
        listViewLigneCourseAdaptater = new ListViewLigneCourseAdaptater(courseActuelle.getMesLignesCourse(), this);
        actualiserAffichageAvecListeProduits();

        spinnerMagasin.setAdapter(magasinDAO.getListeMagasins().recuperereListeMagasinPourSpinner(this));

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

        actionNaviguerEnregistrerCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(nomCourse.getText().toString())){
                    courseDAO.creerCourse(nomCourse.getText().toString(),
                            dateNotification.getText().toString(),
                            "",
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
            recapitualtifPanier.setText("Produits : " +courseActuelle.getMesLignesCourse().size());
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
            myCalendar.set(Calendar.HOUR, hourOfDay);
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            actualiserLabelDate();
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

    private void actualiserLabelDate(){
        String myFormat = "yyyy-MM-dd-HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateNotification.setText(sdf.format(myCalendar.getTime()));
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
}

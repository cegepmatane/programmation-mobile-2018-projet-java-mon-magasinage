package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewProduitAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.ProduitDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;

public class VueAjouterCourse extends AppCompatActivity {

    private final int AJOUTER_PRODUIT_ID_RETOUR=0;
    /** Données*/
    private CourseDAO courseDAO = CourseDAO.getInstance();
    private MagasinDAO magasinDAO = MagasinDAO.getInstance();
    private ProduitDAO produitDAO = ProduitDAO.getInstance();
    private Produits listeProduits;

    /** Affichage*/
    private final Calendar myCalendar = Calendar.getInstance(TimeZone.getDefault());
    private EditText dateNotification;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    private ListView listeviewProduits;
    protected String rechercheUtilisateur ="";
    ListViewProduitAdaptater listViewProduitAdaptater;
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
        //Button actionNaviguerAjouterProduitCourse = (Button) findViewById(R.id.vue_ajouter_course_action_ajouter_produit);
        Button actionNaviguerEnregistrerCourse = (Button) findViewById(R.id.vue_ajouter_course_action_enregistrer);

        try {
            listeProduits = new Produits();
            listViewProduitAdaptater = new ListViewProduitAdaptater(listeProduits,this);
            actualiserAffichageListeProduits();
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinnerMagasin.setAdapter(magasinDAO.getListeMagasins().recuperereListeMagasinPourSpinner(this));

      /*  actionNaviguerAjouterProduitCourse.setOnClickListener(new View.OnClickListener() {
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
        });*/
        barreDeRecherche.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rechercheUtilisateur = newText;
                actualiserAffichageListeProduits();
                return true;
            }
        });

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

    private void actualiserAffichageListeProduits() {
        listeProduits.clear();
        for(Produit produit: produitDAO.getListeProduits()){
            if(produit.getNom().toLowerCase().contains(rechercheUtilisateur.toLowerCase())){
                listeProduits.add(produit);
            }
        }
        listViewProduitAdaptater.setListeProduits(listeProduits);
        listeviewProduits.setAdapter(listViewProduitAdaptater);
    }

}

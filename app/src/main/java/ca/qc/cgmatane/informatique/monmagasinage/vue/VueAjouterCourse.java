package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(EnumerationTheme.isThemeSombre() ? R.style.ThemeSombre : R.style.ThemeLumineux);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_course);

        final EditText nomCourse = findViewById(R.id.vue_ajouter_course_nom_course);
        Spinner spinnerMagasin = findViewById(R.id.vue_ajouter_course_spinner_produit);
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
                // TODO ajouter selection date
                courseDAO.creerCourse(nomCourse.getText().toString(),"2000-01-01-00:00","2000-01-01-01:01","1",0);
                finish();
            }
        });
    }
}

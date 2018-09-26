package ca.qc.cgmatane.informatique.monmagasinage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.time.LocalDateTime;
import java.util.HashMap;

import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Courses;
import ca.qc.cgmatane.informatique.monmagasinage.vue.VueAjouterCourse;
import ca.qc.cgmatane.informatique.monmagasinage.vue.VueListeMagasin;
import ca.qc.cgmatane.informatique.monmagasinage.vue.VueModifierCourse;
import ca.qc.cgmatane.informatique.monmagasinage.vue.VueModifierTheme;

public class ListeCourse extends AppCompatActivity {

    private static final int ACTIVITE_MODIFIER_COURSE = 1;

    /** Données*/
    protected Courses listeCourse;
    protected CourseDAO courseDAO;

    /** Composants graphiques*/
    protected ListView vueListViewCourse;
    protected SearchView vueBarreRechercheCourse;

    protected String rechercheUtilisateur ="";
    protected Courses listeCourseAffichage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Instanciation des composants graphiques*/
        vueListViewCourse = findViewById(R.id.vue_liste_course_listeview);
        vueBarreRechercheCourse = findViewById(R.id.vue_liste_course_barre_recherche);

        /** Instanciation des données*/
        BaseDeDonnees.getInstance(this); // Initialiser l'intance avec une activity
        courseDAO = CourseDAO.getInstance();
//        listeCourse = simulerListeCourse();
        listeCourse = courseDAO.listerCourses();
        listeCourseAffichage = new Courses();

        /** Affichage*/
        actualisationAffichage();

        FloatingActionButton actionNaviguerAjouterCourse = (FloatingActionButton) findViewById(R.id.vue_liste_course_action_naviguer_ajouter_course);
        actionNaviguerAjouterCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intentionNaviguerAjouterCourse = new Intent(ListeCourse.this, VueAjouterCourse.class);
                startActivityForResult(intentionNaviguerAjouterCourse, ACTIVITE_MODIFIER_COURSE);
            }
        });

        vueBarreRechercheCourse.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rechercheUtilisateur = newText;
                actualisationAffichage();
                return true;
            }
        });

        vueListViewCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View vue, int position, long id) {
                ListView vueListeCourse = (ListView) vue.getParent();
                @SuppressWarnings("unchecked")
                HashMap<String,String> course =(HashMap<String,String>) vueListeCourse.getItemAtPosition((int)position);

                Intent intentionNaviguerModifierCourse = new Intent(ListeCourse.this, VueModifierCourse.class);
                intentionNaviguerModifierCourse.putExtra(Course.CHAMP_ID_COURSE, course.get(Course.CHAMP_ID_COURSE));
                startActivityForResult(intentionNaviguerModifierCourse, ACTIVITE_MODIFIER_COURSE);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_gestion_magasin) {
            Intent intentionNaviguerVueGestionMagasin = new Intent(this, VueListeMagasin.class);
            startActivity(intentionNaviguerVueGestionMagasin);
            return true;
        }
        else if(id == R.id.action_changer_theme){
            Intent intentionNavigierChangerTheme = new Intent(this, VueModifierTheme.class);
            startActivity(intentionNavigierChangerTheme);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actualisationAffichage() {
        listeCourseAffichage.clear();
        for(Course course: listeCourse){
            if(course.getNom().toLowerCase().contains(rechercheUtilisateur.toLowerCase())){
                listeCourseAffichage.add(course);
            }
        }

        afficherToutesLesCourses();
    }

    private void afficherToutesLesCourses(){
        SimpleAdapter adapterListeCourses = new SimpleAdapter(this, listeCourseAffichage.recuperereListePourAdapteur(), android.R.layout.two_line_list_item,
                new String[]{Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION},
                new int[]{ android.R.id.text1, android.R.id.text2});

        vueListViewCourse.setAdapter(adapterListeCourses);
    }
    protected void onActivityResult(int activite, int resultat, Intent donnees){
        switch (activite){
            case ACTIVITE_MODIFIER_COURSE:
                actualisationAffichage();
                break;
        }
    }
}

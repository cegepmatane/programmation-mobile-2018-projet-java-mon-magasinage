package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneCourseAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneFaireCourseAdapter;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewProduitAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;

public class VueFaireCourse extends AppCompatActivity {
    private Course courseActuelle;
    protected ListViewLigneFaireCourseAdapter listViewLigneFaireCourseAdapter;
    protected ListView listView;
    protected Produits listeProduits;
    protected CourseDAO courseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EnumerationTheme.changerTheme(this);
        setContentView(R.layout.vue_faire_course);
        Bundle parametres = this.getIntent().getExtras();
        String parametreIdCourse = (String) parametres.get(Course.CHAMP_ID_COURSE);
        int idCourse = Integer.parseInt(parametreIdCourse);
        courseDAO = CourseDAO.getInstance();
        courseActuelle = courseDAO.getListeCourses().trouverAvecId(idCourse);
        listeProduits = new Produits();
        listView =(ListView) findViewById(R.id.list_view_faire_course);

        listViewLigneFaireCourseAdapter = new ListViewLigneFaireCourseAdapter(courseActuelle.getMesLignesCourse(),courseActuelle, this);
        listView.setAdapter(listViewLigneFaireCourseAdapter);
    }
}

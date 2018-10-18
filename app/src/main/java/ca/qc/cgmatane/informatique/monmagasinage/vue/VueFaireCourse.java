package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneCourseAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneFaireCourseAdapter;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewProduitAdaptater;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.LigneCourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;

public class VueFaireCourse extends AppCompatActivity {
    private Course courseActuelle;
    protected ListViewLigneFaireCourseAdapter listViewLigneFaireCourseAdapter;
    protected ListView listView;
    protected Produits listeProduits;
    protected CourseDAO courseDAO;
    protected LigneCourseDAO ligneCourseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_faire_course);
        Bundle parametres = this.getIntent().getExtras();
        String parametreIdCourse = (String) parametres.get(Course.CHAMP_ID_COURSE);
        int idCourse = Integer.parseInt(parametreIdCourse);
        courseDAO = CourseDAO.getInstance();
        ligneCourseDAO = ligneCourseDAO.getInstance();
        courseActuelle = courseDAO.getListeCourses().trouverAvecId(idCourse);
        ligneCourseDAO.chargerListeLigneCoursePourUneCourse(courseActuelle);
        listView =(ListView) findViewById(R.id.list_view_faire_course);
        listViewLigneFaireCourseAdapter = new ListViewLigneFaireCourseAdapter(courseActuelle, VueFaireCourse.this);
        listView.setAdapter(listViewLigneFaireCourseAdapter);
    }
}

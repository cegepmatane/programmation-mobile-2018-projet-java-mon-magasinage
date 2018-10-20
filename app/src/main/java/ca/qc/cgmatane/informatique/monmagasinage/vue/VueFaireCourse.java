package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneFaireCourseAdapter;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.LigneCourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueFaireCourse extends AppCompatActivity {

    private static final int ACTIVITE_RESULTAT_PRISE_PHOTO = 1;

    private Course courseActuelle;
    protected ListViewLigneFaireCourseAdapter listViewLigneFaireCourseAdapter;
    protected ListView listViewPanier;
    protected CourseDAO courseDAO;
    protected LigneCourseDAO ligneCourseDAO;
    protected Button actionTerminerCourse;
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
}

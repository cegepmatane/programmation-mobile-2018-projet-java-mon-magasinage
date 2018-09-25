package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;

public class VueModifierCourse extends AppCompatActivity {

    private CourseDAO courseDAO = CourseDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_modifier_course);

        Bundle parametres = this.getIntent().getExtras();
        String parametreIdCourse = (String) parametres.get(Course.CHAMP_ID_COURSE);
        int idCourse = Integer.parseInt(parametreIdCourse);

        System.out.println(idCourse);

        Course courseAModifier = courseDAO.recupererCourseAvecId(idCourse);


    }


}

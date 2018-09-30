package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueModifierCourse extends AppCompatActivity {

    private CourseDAO courseDAO ;
    private MagasinDAO magasinDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_course);

        courseDAO = CourseDAO.getInstance();
        magasinDAO = MagasinDAO.getInstance();
        Bundle parametres = this.getIntent().getExtras();
        String parametreIdCourse = (String) parametres.get(Course.CHAMP_ID_COURSE);
        int idCourse = Integer.parseInt(parametreIdCourse);

        Course courseAModifier = courseDAO.getListeCourses().trouverAvecId(idCourse);

        EditText nomCourse = findViewById(R.id.vue_modifier_course_nom_course);
        nomCourse.setText(courseAModifier.getNom());

        System.out.println(idCourse);
        System.out.println(courseAModifier.getMonMagasin().getId());

        //final Spinner spinnerMagasin = findViewById(R.id.vue_modifier_course_spinner_produit);
        //spinnerMagasin.setAdapter(magasinDAO.getListeMagasins().recuperereListeMagasinPourSpinner(this));


    }

}

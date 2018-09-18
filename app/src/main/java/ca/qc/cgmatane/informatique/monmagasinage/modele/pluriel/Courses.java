package ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;

public class Courses extends ArrayList<Course> {

    public Course trouverAvecId(int id){
        for(Course course: this){
            if(course.getId() == id){
                return course;
            }
        };
        return null;
    }

    public List<HashMap<String, String>> recuperereListePourAdapteur() {
        List<HashMap<String, String>> listePourAdapteur = new ArrayList<HashMap<String, String>>();

        for(Course course:this){
            listePourAdapteur.add(course.obtenirObjetPourAdapteur());
        }
        return listePourAdapteur;
    }
}

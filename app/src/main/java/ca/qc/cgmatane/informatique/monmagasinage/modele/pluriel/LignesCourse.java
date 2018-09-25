package ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel;

import java.util.ArrayList;

import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;

public class LignesCourse extends ArrayList<LigneCourse>{

    public LigneCourse trouverAvecIdCourse(int id){
        for(LigneCourse ligneCourse: this){
            if(null != ligneCourse.getCourse() && ligneCourse.getCourse().getId() == id){
                return ligneCourse;
            }
        };
        return null;
    }
    
    public LigneCourse trouverAvecIdProduit(int id){
        for(LigneCourse ligneCourse: this){
            if(null != ligneCourse.getProduit() && ligneCourse.getProduit().getId() == id){
                return ligneCourse;
            }
        };
        return null;
    }
}

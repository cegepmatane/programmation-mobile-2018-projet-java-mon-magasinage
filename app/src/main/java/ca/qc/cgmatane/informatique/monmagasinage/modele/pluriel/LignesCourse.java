package ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;

import java.util.ArrayList;

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

    public boolean contenirProduit(int idProduit){
        for(LigneCourse ligneCourse: this){
            if(ligneCourse.getProduit().getId() == idProduit){
                return true;
            }
        };
        return false;
    }

    /***
     * Nombre total de produit
     * @return
     */
    public int recupererQuantiteTotal() {
        int quantite = 0;
        for (LigneCourse ligneCourse : this) {
            quantite += ligneCourse.getQuantite();
        }
        return  quantite;
    }

    public LignesCourse creerListeParValeur(){
        LignesCourse result= new LignesCourse();
        result.addAll(this);
        return  result;
    }

    /***
     * Change la course pour toutes les lignes de la liste
     * @param course
     */
    public void setCourseDeToutesLesLignes(Course course){
        for(LigneCourse ligneCourse: this){
            ligneCourse.setCourse(course);

        };
    }
}

package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;

import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;

public class LigneCourseDAO {

    private static LigneCourseDAO instance;
    private BaseDeDonnees accesseurBaseDeDonnees;
    private UniteDAO uniteDAO;
    private ProduitDAO produitDAO;

    private LigneCourseDAO(){
        accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        uniteDAO = UniteDAO.getInstance();
        produitDAO = ProduitDAO.getInstance();
    }

    public static LigneCourseDAO getInstance(){
        if(instance ==null){
            instance = new LigneCourseDAO();
        }
        return instance;
    }

    public boolean enregistrerListeLigneCoursePourUneCourse(LignesCourse lignesCourses){
        if(lignesCourses.size() <1) return true;

        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        db.beginTransaction();
        //TODO faire une transaction pour eviter les erreurs
        //On considere que toutes les lignes course on la meme course
        try {
            db.delete(LigneCourse.NOM_TABLE, LigneCourse.CHAMP_ID_COURSE +"="+lignesCourses.get(0).getCourse().getId() , null);
            for (LigneCourse ligneCourse: lignesCourses){
                ContentValues values = new ContentValues();
                values.put(LigneCourse.CHAMP_ID_COURSE, ligneCourse.getCourse().getId());
                values.put(LigneCourse.CHAMP_ID_PRODUIT, ligneCourse.getProduit().getId());
                values.put(LigneCourse.CHAMP_COCHE, ligneCourse.isCoche());
                values.put(LigneCourse.CHAMP_ID_UNITE, ligneCourse.getUnite().getId());
                values.put(LigneCourse.CHAMP_QUANTITE, ligneCourse.getQuantite());
                db.insert(LigneCourse.NOM_TABLE, null, values);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            db.endTransaction();
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

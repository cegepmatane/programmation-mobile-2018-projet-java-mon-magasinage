package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Unites;

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

    public boolean enregistrerListeLigneCoursePourUneCourse(int idCourse, LignesCourse lignesCourses){
        if(lignesCourses.size() <1) return true;

        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        db.beginTransaction();
        //On considere que toutes les lignes course on la meme course
        try {
            db.delete(LigneCourse.NOM_TABLE, LigneCourse.CHAMP_ID_COURSE +"="+lignesCourses.get(0).getCourse().getId() , null);
            for (LigneCourse ligneCourse: lignesCourses){
                ContentValues values = new ContentValues();
                values.put(LigneCourse.CHAMP_ID_COURSE, idCourse);
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

    /***
     * Rempli la liste LigneCourse de la course
     * @param course
     */
    public void chargerListeLigneCoursePourUneCourse(Course course){
        Produits listeProduits = produitDAO.getListeProduits();
        Unites listeUnites = uniteDAO.getListeUnite();

        Cursor curseurLignesCourse = accesseurBaseDeDonnees.getReadableDatabase().
                rawQuery(String.format("select * from %s WHERE %s=%s",LigneCourse.NOM_TABLE, LigneCourse.CHAMP_ID_COURSE, course.getId()), null);
        System.out.println(" QUANTITE PANIER : "+ curseurLignesCourse.getCount());
        int indexIdProduit = curseurLignesCourse.getColumnIndex(LigneCourse.CHAMP_ID_PRODUIT);
        int indexCoche = curseurLignesCourse.getColumnIndex(LigneCourse.CHAMP_COCHE);
        int indexIdUnite = curseurLignesCourse.getColumnIndex(LigneCourse.CHAMP_ID_UNITE);
        int indexQuantite = curseurLignesCourse.getColumnIndex(LigneCourse.CHAMP_QUANTITE);

        for(curseurLignesCourse.moveToFirst();!curseurLignesCourse.isAfterLast();curseurLignesCourse.moveToNext()){
            System.out.println(" QUANTITE PANIER : je passe dans le for");
            LigneCourse ligneCourse= new LigneCourse();
            ligneCourse.setCourse(course);
            ligneCourse.setProduit(listeProduits.trouverAvecId(curseurLignesCourse.getInt(indexIdProduit)));
            ligneCourse.setQuantite(curseurLignesCourse.getInt(indexQuantite));
            ligneCourse.setUnite(listeUnites.trouverAvecId(curseurLignesCourse.getInt(indexIdUnite)));
            ligneCourse.setCoche(curseurLignesCourse.getInt(indexCoche)>0);
            course.getMesLignesCourse().add(ligneCourse);
        }
        System.out.println(" QUANTITE PANIER : "+ course.getMesLignesCourse().recupererQuantiteTotal());
        curseurLignesCourse.close();
    }
}

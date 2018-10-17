package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.database.Cursor;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Unite;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Unites;

public class UniteDAO {
    private Unites listeUnite;
    private static UniteDAO instance;
    private BaseDeDonnees accesseurBaseDeDonnees;

    private UniteDAO() {
        accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        listeUnite = new Unites();
        listerUnites();
    }

    public static UniteDAO getInstance(){
        if(instance == null){
            instance = new UniteDAO();
        }
        return instance;
    }

    public Unites listerUnites(){

        Cursor curseurUnites = accesseurBaseDeDonnees.getReadableDatabase().rawQuery("select * from "+ Unite.NOM_TABLE, null);
        this.listeUnite.clear();

        Unite unite;

        int indexId = curseurUnites.getColumnIndex(Unite.CHAMP_ID);
        int indexLibelle = curseurUnites.getColumnIndex(Unite.CHAMP_LIBELLE);

        for(curseurUnites.moveToFirst();!curseurUnites.isAfterLast();curseurUnites.moveToNext()){
            int id= curseurUnites.getInt(indexId);
            String libelle= curseurUnites.getString(indexLibelle);

            unite = new Unite(id, libelle);
            this.listeUnite.add(unite);
        }

        curseurUnites.close();

        return this.listeUnite;
    }

    public Unites getListeUnite() {
        return listeUnite;
    }
}

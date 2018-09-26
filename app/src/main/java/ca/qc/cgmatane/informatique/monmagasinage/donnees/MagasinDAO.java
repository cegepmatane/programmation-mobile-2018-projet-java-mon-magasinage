package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Magasins;

public class MagasinDAO {
    private static MagasinDAO instance;
    private BaseDeDonnees accesseurBaseDeDonnees;

    protected Magasins listeMagasins;

    private MagasinDAO() {
        listeMagasins = new Magasins();
        accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
    }


    public static MagasinDAO getInstance(){
        if(null == instance){
            instance = new MagasinDAO();
        }
        return instance;
    }

    public Magasins listerMagasins(){
        listeMagasins.clear();
        Cursor curseurMagasin = accesseurBaseDeDonnees.getReadableDatabase().rawQuery("select * from "+ Magasin.NOM_TABLE, null);
        Magasin magasin;

        int indexId = curseurMagasin.getColumnIndex(Magasin.CHAMP_ID);
        int indexNom = curseurMagasin.getColumnIndex(Magasin.CHAMP_NOM);
        int indexAdresse = curseurMagasin.getColumnIndex(Magasin.CHAMP_ADRESSE);
        int indexVille = curseurMagasin.getColumnIndex(Magasin.CHAMP_VILLE);
        int indexCoorX = curseurMagasin.getColumnIndex(Magasin.CHAMP_COOR_X);
        int indexCooY = curseurMagasin.getColumnIndex(Magasin.CHAMP_COOR_Y);

        for(curseurMagasin.moveToFirst();!curseurMagasin.isAfterLast();curseurMagasin.moveToNext()){
            int id = curseurMagasin.getInt(indexId);
            String nom = curseurMagasin.getString(indexNom);
            String ville = curseurMagasin.getString(indexVille);
            String adresse = curseurMagasin.getString(indexAdresse);
            Double coorX = curseurMagasin.getDouble(indexCoorX);
            Double coorY = curseurMagasin.getDouble(indexCooY);

            magasin= new Magasin(id, nom, adresse, ville, coorX, coorY);
            listeMagasins.add(magasin);
        }
        curseurMagasin.close();
        return listeMagasins;
    }

    public Magasins getListeMagasins() {
        return listeMagasins;
    }

    public void setListeMagasins(Magasins listeMagasins) {
        this.listeMagasins = listeMagasins;
    }
}

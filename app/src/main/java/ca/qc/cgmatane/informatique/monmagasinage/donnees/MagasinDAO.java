package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Magasins;

public class MagasinDAO {
    private static MagasinDAO instance;
    private BaseDeDonnees accesseurBaseDeDonnees;

    private Magasins listeMagasins;

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

    public void ajouterMagasin(Magasin magasin) {
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Magasin.CHAMP_NOM, magasin.getNom());
        values.put(Magasin.CHAMP_ADRESSE, magasin.getAdresse());
        values.put(Magasin.CHAMP_VILLE, magasin.getVille());
        values.put(Magasin.CHAMP_COOR_X, magasin.getCoorX());
        values.put(Magasin.CHAMP_COOR_Y, magasin.getCoorY());

        int id = (int) db.insert(Magasin.NOM_TABLE, null, values);
        magasin.setId(id);
        listeMagasins.add(magasin);
    }


    public void modifierMagasin(int id, String nom, String adresse, String ville, String coorX, String coorY) {
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Magasin.CHAMP_ID, id);
        values.put(Magasin.CHAMP_NOM, nom);
        values.put(Magasin.CHAMP_ADRESSE, adresse);
        values.put(Magasin.CHAMP_VILLE, ville);
        values.put(Magasin.CHAMP_COOR_X, coorX);
        values.put(Magasin.CHAMP_COOR_Y, coorY);

        db.update(Magasin.NOM_TABLE, values, Magasin.CHAMP_ID+"="+id, null);
        Magasin magasin = listeMagasins.trouverAvecId(id);
        magasin.setNom(nom);
        magasin.setAdresse(adresse);
        magasin.setVille(ville);
        magasin.setCoorX(Double.parseDouble(coorX));
        magasin.setCoorY(Double.parseDouble(coorY));

    }

    public void supprimerMagasin(int id) {
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        db.delete(Magasin.NOM_TABLE, Magasin.CHAMP_ID +"="+ id, null);
        listeMagasins.remove(listeMagasins.trouverAvecId(id));
    }
}

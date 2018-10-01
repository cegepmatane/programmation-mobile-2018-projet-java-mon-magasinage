package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.database.Cursor;

import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;

public class ProduitDAO {
    private Produits listeProduits;
    private static ProduitDAO instance;
    private BaseDeDonnees accesseurBaseDeDonnees;

    private ProduitDAO() {
        accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        listeProduits = new Produits();
    }

    public static ProduitDAO getInstance(){
        if(instance == null){
            instance = new ProduitDAO();
        }
        return instance;
    }

    public Produits listerProduits(){

        Cursor curseurProduits = accesseurBaseDeDonnees.getReadableDatabase().rawQuery("select * from "+Produit.NOM_TABLE, null);
        this.listeProduits.clear();

        Produit produit;

        int indexId = curseurProduits.getColumnIndex(Produit.CHAMP_ID);
        int indexNom = curseurProduits.getColumnIndex(Produit.CHAMP_NOM);
        int indexQuantiteDefaut = curseurProduits.getColumnIndex(Produit.CHAMP_QUANTITE_DEFAUT);
        int indexRecurenceAchat = curseurProduits.getColumnIndex(Produit.CHAMP_RECURENCE_ACHAT);

        for(curseurProduits.moveToFirst();!curseurProduits.isAfterLast();curseurProduits.moveToNext()){
            int id_produit= curseurProduits.getInt(indexId);
            String nom= curseurProduits.getString(indexNom);
            int quantiteDefaut = curseurProduits.getInt(indexQuantiteDefaut);
            int recurenceAchat = curseurProduits.getInt(indexRecurenceAchat);

            produit = new Produit(id_produit, nom, quantiteDefaut, recurenceAchat);
            this.listeProduits.add(produit);
        }

        curseurProduits.close();

        return this.listeProduits;
    }

    public Produits getListeProduits() {
        return listeProduits;
    }
}

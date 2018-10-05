package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.database.Cursor;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Unites;

public class ProduitDAO {

    private Produits listeProduits;
    private UniteDAO uniteDAO;
    private static ProduitDAO instance;
    private BaseDeDonnees accesseurBaseDeDonnees;

    private ProduitDAO() {
        accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        uniteDAO = UniteDAO.getInstance();
        listeProduits = new Produits();
        listerProduits();
    }

    public static ProduitDAO getInstance(){
        if(instance == null){
            instance = new ProduitDAO();
        }
        return instance;
    }

    public Produits listerProduits(){
        Unites listeUnites = uniteDAO.listerUnites();
        Cursor curseurProduits = accesseurBaseDeDonnees.getReadableDatabase().rawQuery("select * from "+Produit.NOM_TABLE, null);
        this.listeProduits.clear();

        Produit produit;

        int indexId = curseurProduits.getColumnIndex(Produit.CHAMP_ID);
        int indexNom = curseurProduits.getColumnIndex(Produit.CHAMP_NOM);
        int indexQuantiteDefaut = curseurProduits.getColumnIndex(Produit.CHAMP_QUANTITE_DEFAUT);
        int indexRecurenceAchat = curseurProduits.getColumnIndex(Produit.CHAMP_RECURRENCE_ACHAT);
        int indexIdUnite = curseurProduits.getColumnIndex(Produit.CHAMP_UNITE_DEFAUT);

        for(curseurProduits.moveToFirst();!curseurProduits.isAfterLast();curseurProduits.moveToNext()){
            int id_produit= curseurProduits.getInt(indexId);
            String nom= curseurProduits.getString(indexNom);
            int quantiteDefaut = curseurProduits.getInt(indexQuantiteDefaut);
            int recurenceAchat = curseurProduits.getInt(indexRecurenceAchat);
            int idUnite = curseurProduits.getInt(indexIdUnite);

            produit = new Produit(id_produit, nom, quantiteDefaut, recurenceAchat);
            produit.setUniteDefaut(listeUnites.trouverAvecId(idUnite));
            this.listeProduits.add(produit);
        }

        curseurProduits.close();

        return this.listeProduits;
    }

    public Produits getListeProduits() {
        return listeProduits;
    }
}

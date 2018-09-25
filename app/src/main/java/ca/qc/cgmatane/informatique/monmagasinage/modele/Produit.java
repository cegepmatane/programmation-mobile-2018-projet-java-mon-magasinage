package ca.qc.cgmatane.informatique.monmagasinage.modele;

public class Produit {
    public static final String NOM_TABLE = "produit";

    public static final String CHAMP_ID = "id_produit";
    public static final String CHAMP_NOM = "nom";
    public static final String CHAMP_QUANTITE_DEFAUT = "quantiteDefaut";
    public static final String CHAMP_RECURENCE_ACHAT = "recurenceAchat";
    public static final String CHAMP_ID_UNITE= "id_unite";

    private int id;
    private String nom;
    private int quantiteDefaut;
    private int recurenceAchat; //Jour
    private Unite monUnite;

    public Produit(int id, String nom, int quantiteDefaut) {
        this.id = id;
        this.nom = nom;
        this.quantiteDefaut = quantiteDefaut;
    }

    public Produit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantiteDefaut() {
        return quantiteDefaut;
    }

    public void setQuantiteDefaut(int quantiteDefaut) {
        this.quantiteDefaut = quantiteDefaut;
    }

    public int getRecurenceAchat() {
        return recurenceAchat;
    }

    public void setRecurenceAchat(int recurenceAchat) {
        this.recurenceAchat = recurenceAchat;
    }

    public Unite getMonUnite() {
        return monUnite;
    }

    public void setMonUnite(Unite monUnite) {
        this.monUnite = monUnite;
    }
}

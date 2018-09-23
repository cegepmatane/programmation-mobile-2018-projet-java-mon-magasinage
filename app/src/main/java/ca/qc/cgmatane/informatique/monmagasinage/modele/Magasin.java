package ca.qc.cgmatane.informatique.monmagasinage.modele;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Magasin {
    public static final String NOM_TABLE = "magasin";
    public static final String CHAMP_ID = "id_magasin";
    public static final String CHAMP_NOM = "nom";
    private int id;
    private String nom;

    public Magasin(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Magasin() {
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
    public HashMap<String, String> obtenirObjetPourAdapteur() {
        HashMap<String, String> magasinPourAdapteur = new HashMap<String, String>();
        magasinPourAdapteur.put(CHAMP_ID, String.valueOf(this.id));
        magasinPourAdapteur.put(CHAMP_NOM, this.nom);
        return magasinPourAdapteur;
    }
}

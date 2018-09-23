package ca.qc.cgmatane.informatique.monmagasinage.modele;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Magasin {
    public static final String NOM_TABLE = "magasin";
    public static final String CHAMP_ID = "id_magasin";
    public static final String CHAMP_NOM = "nom";
    public static final String CHAMP_ADRESSE = "adresse";
    public static final String CHAMP_VILLE = "ville";
    public static final String CHAMP_COOR_X = "coor_x";
    public static final String CHAMP_COOR_Y = "coor_y";


    private int id;
    private String nom;
    private String adresse;
    private String ville;
    private Double coorX;
    private Double coorY;

    public Magasin(int id, String nom, String adresse, String ville, Double coorX, Double coorY) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.coorX = coorX;
        this.coorY = coorY;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Double getCoorX() {
        return coorX;
    }

    public void setCoorX(Double coorX) {
        this.coorX = coorX;
    }

    public Double getCoorY() {
        return coorY;
    }

    public void setCoorY(Double coorY) {
        this.coorY = coorY;
    }

    public HashMap<String, String> obtenirObjetPourAdapteur() {
        HashMap<String, String> magasinPourAdapteur = new HashMap<String, String>();
        magasinPourAdapteur.put(CHAMP_ID, String.valueOf(this.id));
        magasinPourAdapteur.put(CHAMP_NOM, this.nom);
        return magasinPourAdapteur;
    }
}

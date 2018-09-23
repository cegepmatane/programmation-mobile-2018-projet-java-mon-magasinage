package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MagasinDAO {
    private static MagasinDAO instance;

    private MagasinDAO() {
    }

    public List<HashMap<String,String>> recuperereListeMagasinPourAdapteur() {
        List<HashMap<String, String>> liste;
        liste = new ArrayList<HashMap<String, String>>();
        liste.add(obtenirMagasinPourAdapteur("Magasin1", "lieu1"));
        liste.add(obtenirMagasinPourAdapteur("Magasin2", "lieu2"));
        liste.add(obtenirMagasinPourAdapteur("Magasin3", "lieu3"));
        return liste;
    }
    public HashMap<String, String> obtenirMagasinPourAdapteur(String nom, String lieu)
    {
        HashMap<String, String> magasinPourAdapteur = new HashMap<String,String>();
        magasinPourAdapteur.put("Nom", nom);
        magasinPourAdapteur.put("Lieu", lieu);
        return magasinPourAdapteur;
    }
    public static MagasinDAO getInstance(){
        if(null == instance){
            instance = new MagasinDAO();
        }
        return instance;
    }
}

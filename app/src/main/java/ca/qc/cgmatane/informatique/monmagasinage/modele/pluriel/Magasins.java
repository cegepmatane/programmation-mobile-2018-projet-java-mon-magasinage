package ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;

public class Magasins extends ArrayList<Magasin>{


    public Magasin trouverAvecId(int id){
        for(Magasin magasin: this){
            if(magasin.getId() == id){
                return magasin;
            }
        };
        return null;
    }
    public List<HashMap<String,String>> recuperereListeMagasinPourAdapteur() {
        List<HashMap<String, String>> listePourAdapteur = new ArrayList<HashMap<String, String>>();

        for(Magasin magasin:this){
            listePourAdapteur.add(magasin.obtenirObjetPourAdapteur());
        }
        return listePourAdapteur;
    }



}

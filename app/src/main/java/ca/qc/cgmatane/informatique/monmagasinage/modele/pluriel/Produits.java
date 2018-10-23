package ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;

import java.util.ArrayList;

public class Produits extends ArrayList<Produit>{
    
    public Produit trouverAvecId(int id){
        for(Produit produit: this){
            if(produit.getId() == id){
                return produit;
            }
        };
        return null;
    }
}

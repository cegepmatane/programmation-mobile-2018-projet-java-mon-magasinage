package ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel;

import java.util.ArrayList;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;

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

package ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel;

import java.util.ArrayList;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Unite;

public class Unites extends ArrayList<Unite>{

    public Unite trouverAvecId(int id){
        for(Unite unite: this){
            if(unite.getId() == id){
                return unite;
            }
        };
        return null;
    }
}

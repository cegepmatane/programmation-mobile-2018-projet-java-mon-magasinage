package ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel;

import android.content.Context;
import android.widget.ArrayAdapter;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Unite;

import java.util.ArrayList;
import java.util.List;

public class Unites extends ArrayList<Unite>{

    public Unite trouverAvecId(int id){
        for(Unite unite: this){
            if(unite.getId() == id){
                return unite;
            }
        };
        return null;
    }

    public ArrayAdapter recuperAdapterPourSpinner(Context context){
        List<String> listPourSpinner = new ArrayList<String>();
        for (Unite unite: this){
            listPourSpinner.add(unite.getLibelle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listPourSpinner);

        return  adapter;
    }

    public int retournerPositionDansLaListe(int idUnite){
        int i=0;
        for(Unite unite: this){
            if(unite.getId() == idUnite){
                return i;
            }
            i++;
        }
        return -1;
    }
}

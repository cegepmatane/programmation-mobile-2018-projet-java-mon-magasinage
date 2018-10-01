package ca.qc.cgmatane.informatique.monmagasinage.adaptater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.UniteDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Unite;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;

public class ListViewProduitAdaptater extends BaseAdapter {
    private UniteDAO uniteDAO;
    private Produits listeProduits;
    private LayoutInflater layoutInflater = null;
    private Activity context;

    public ListViewProduitAdaptater(Produits listeProduits, Activity context) {
        this.listeProduits = listeProduits;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;

        uniteDAO= UniteDAO.getInstance();
        uniteDAO.listerUnites();
    }

    @Override
    public int getCount() {
        return listeProduits.size();
    }

    @Override
    public Object getItem(int position) {
        return listeProduits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.ligne_listview_produit, null);
            TextView textViewNomProduit = (TextView) convertView.findViewById(R.id.ligne_listview_produit_nom_produit);
            Spinner spinnerQuantite = (Spinner) convertView.findViewById(R.id.ligne_listview_produit_spinner_quantite);
            Spinner spinnerUnite = (Spinner) convertView.findViewById(R.id.ligne_listview_produit_spinner_unite);
            Button actionLigneProduit = (Button) convertView.findViewById(R.id.ligne_listview_produit_button_action);
            spinnerUnite.setAdapter(uniteDAO.getListeUnite().recuperAdapterPourSpinner(context));
            List<String> listPourSpinner = new ArrayList<String>();
            for (int i = 1;i<12;i++){
                listPourSpinner.add(i + "");
            }
            //TODO à faire ailleur pour eviter de la faire à chaque ligne
            spinnerQuantite.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listPourSpinner));

            final Produit produitSelectionne = listeProduits.get(position);
            if (produitSelectionne != null){
                textViewNomProduit.setText(produitSelectionne.getNom().toLowerCase());

            }
            return convertView;
        }
        return convertView;

    }
}

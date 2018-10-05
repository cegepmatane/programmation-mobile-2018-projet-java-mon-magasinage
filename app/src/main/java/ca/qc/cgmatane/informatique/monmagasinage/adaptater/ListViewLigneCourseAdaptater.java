package ca.qc.cgmatane.informatique.monmagasinage.adaptater;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.UniteDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Unites;

import java.util.ArrayList;
import java.util.List;

public class ListViewLigneCourseAdaptater extends BaseAdapter {
    private Unites listeUnites;
    private LignesCourse panier;
    private LayoutInflater layoutInflater = null;
    private Activity context;
    private Course courseActuelle;
    private ArrayAdapter<String> adaptaterQuantite;

    public ListViewLigneCourseAdaptater(LignesCourse ligneCourses, Course course, Activity context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        listeUnites = UniteDAO.getInstance().getListeUnite();
        panier = ligneCourses;
        courseActuelle = course;

        List<String> listPourSpinner = new ArrayList<String>();
        for (int i = 1;i<10;i++){
            listPourSpinner.add(i + "");
        }
        adaptaterQuantite= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listPourSpinner);
    }

    @Override
    public int getCount() {
        return panier.size();
    }

    @Override
    public Object getItem(int position) {
        return panier.get(position);
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

            spinnerUnite.setAdapter(listeUnites.recuperAdapterPourSpinner(context));
            spinnerQuantite.setAdapter(adaptaterQuantite);
            actionLigneProduit.setText("-");

            final LigneCourse ligneCourse = panier.get(position);
            if(ligneCourse != null && ligneCourse.getProduit() != null){
                textViewNomProduit.setText(ligneCourse.getProduit().getNom().toLowerCase());
                spinnerUnite.setSelection(listeUnites.retournerPositionDansLaListe(ligneCourse.getUnite().getId()));
                spinnerQuantite.setSelection(ligneCourse.getQuantite()-1);
                actionLigneProduit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        courseActuelle.getMesLignesCourse().remove(ligneCourse);
                        envoyerMessagePourActualisation("panier");
                    }
                });

                spinnerQuantite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position+1 != ligneCourse.getQuantite()){
                            ligneCourse.setQuantite(position+1);
                            envoyerMessagePourActualisation("panier");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinnerUnite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(listeUnites.get(position) != ligneCourse.getUnite()){
                            ligneCourse.setUnite(listeUnites.get(position));
                            envoyerMessagePourActualisation("panier");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            return convertView;
        }
        return convertView;
    }

    private void envoyerMessagePourActualisation(String message) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("event_recharger_affichage");
        // You can also include some extra data.
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    public LignesCourse getPanier() {
        return panier;
    }

    public void setPanier(LignesCourse panier) {
        this.panier = panier;
    }
}

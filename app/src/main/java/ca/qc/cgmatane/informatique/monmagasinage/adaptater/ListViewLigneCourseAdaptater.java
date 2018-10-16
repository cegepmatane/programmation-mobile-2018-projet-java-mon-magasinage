package ca.qc.cgmatane.informatique.monmagasinage.adaptater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.UniteDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Unites;

import java.util.ArrayList;
import java.util.List;

public class ListViewLigneCourseAdaptater extends ArrayAdapter<LigneCourse> {
    private Unites listeUnites;
    private LignesCourse panier;
    private Context monContext;
    private Course courseActuelle;
    private ArrayAdapter<String> adaptaterQuantite;

    private static class VueBloque {
        TextView textViewNomProduit;
        Spinner spinnerQuantite;
        Spinner spinnerUnite;
        Button actionLigneProduit;
    }

    public ListViewLigneCourseAdaptater(LignesCourse ligneCourses, Course course, Activity context) {
        super(context, R.layout.ligne_listview_produit);
        this.monContext = context;

        panier = ligneCourses;
        listeUnites = UniteDAO.getInstance().getListeUnite();
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
    public LigneCourse getItem(int position) {
        return panier.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LigneCourse ligneCourse = (LigneCourse) this.getItem(position);
        VueBloque vueBloque; // view lookup cache stored in tag

        final View result;
        if (convertView == null) {
/*
            convertView = layoutInflater.inflate(R.layout.ligne_listview_produit, null);
*/
            vueBloque = new VueBloque();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ligne_listview_produit, parent, false);

            vueBloque.textViewNomProduit = convertView.findViewById(R.id.ligne_listview_produit_nom_produit);
            vueBloque.spinnerQuantite = convertView.findViewById(R.id.ligne_listview_produit_spinner_quantite);
            vueBloque.spinnerUnite = convertView.findViewById(R.id.ligne_listview_produit_spinner_unite);
            vueBloque.actionLigneProduit = convertView.findViewById(R.id.ligne_listview_produit_button_action);

            vueBloque.spinnerUnite.setAdapter(listeUnites.recuperAdapterPourSpinner(monContext));
            vueBloque.spinnerQuantite.setAdapter(adaptaterQuantite);
            vueBloque.actionLigneProduit.setText("-");
            result = convertView;
            convertView.setTag(vueBloque);
        }else{
            vueBloque = (VueBloque) convertView.getTag();
            result=convertView;
        }
        Animation animation = AnimationUtils.loadAnimation(monContext, (position > lastPosition) ? R.anim.haut_vers_le_bas : R.anim.bas_vers_le_haut);
        result.startAnimation(animation);
        lastPosition = position;



        if(ligneCourse != null &&  ligneCourse.getProduit() != null){
            vueBloque.textViewNomProduit.setText(((LigneCourse) ligneCourse).getProduit().getNom().toLowerCase());
            vueBloque.spinnerUnite.setSelection(listeUnites.retournerPositionDansLaListe(ligneCourse.getUnite().getId()));
            vueBloque.spinnerQuantite.setSelection(ligneCourse.getQuantite()-1);
            vueBloque.actionLigneProduit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseActuelle.getMesLignesCourse().remove(ligneCourse);
                    envoyerMessagePourActualisation("panier");
                }
            });

            vueBloque.spinnerQuantite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

            vueBloque.spinnerUnite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void envoyerMessagePourActualisation(String message) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("event_recharger_affichage");
        // You can also include some extra data.
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(monContext).sendBroadcast(intent);
    }
    public LignesCourse getPanier() {
        return panier;
    }

    public void setPanier(LignesCourse panier) {
        this.panier = panier;
    }
}

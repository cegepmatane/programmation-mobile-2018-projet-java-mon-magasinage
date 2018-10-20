package ca.qc.cgmatane.informatique.monmagasinage.adaptater;

import android.app.Activity;
import android.content.Context;
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

public class ListViewLigneCourseAdaptater extends ArrayAdapter<LigneCourse> {
    private Unites listeUnites;
    private LignesCourse panier;
    private Context monContext;
    private Course courseActuelle;
    private ArrayAdapter<String> adaptaterQuantite;

    private static class VueBloqueLigneCourse {
        TextView textViewNomProduit;
        Spinner spinnerQuantite;
        Spinner spinnerUnite;
        Button actionLigneProduit;
    }

    public ListViewLigneCourseAdaptater(LignesCourse ligneCourses, Course course, Activity context) {
        super(context, R.layout.ligne_listview_panier);
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
        VueBloqueLigneCourse vueBloqueLigneCourse; // view lookup cache stored in tag

        final View result;
        if (convertView == null) {

            vueBloqueLigneCourse = new VueBloqueLigneCourse();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ligne_listview_panier, parent, false);

            vueBloqueLigneCourse.textViewNomProduit = convertView.findViewById(R.id.ligne_listview_panier_nom_produit);
            vueBloqueLigneCourse.spinnerQuantite = convertView.findViewById(R.id.ligne_listview_panier_spinner_quantite);
            vueBloqueLigneCourse.spinnerUnite = convertView.findViewById(R.id.ligne_listview_panier_spinner_unite);
            vueBloqueLigneCourse.actionLigneProduit = convertView.findViewById(R.id.ligne_listview_panier_button_action);

            vueBloqueLigneCourse.spinnerUnite.setAdapter(listeUnites.recuperAdapterPourSpinner(monContext));
            vueBloqueLigneCourse.spinnerQuantite.setAdapter(adaptaterQuantite);
            vueBloqueLigneCourse.actionLigneProduit.setText("-");
            result = convertView;
            convertView.setTag(vueBloqueLigneCourse);
        }else{
            vueBloqueLigneCourse = (VueBloqueLigneCourse) convertView.getTag();
            result=convertView;
        }
        /*Animation animation = AnimationUtils.loadAnimation(monContext, (position > lastPosition) ? R.anim.haut_vers_le_bas : R.anim.bas_vers_le_haut);
        result.startAnimation(animation);*/
        lastPosition = position;



        if(ligneCourse != null &&  ligneCourse.getProduit() != null){


            vueBloqueLigneCourse.textViewNomProduit.setText(((LigneCourse) ligneCourse).getProduit().getNom());

            if (ligneCourse.getProduit().getNom().length() > 15) {
                String nomReduit = ligneCourse.getProduit().getNom().substring(0, 15);
                vueBloqueLigneCourse.textViewNomProduit.setText(nomReduit + "...");
            } else {
                vueBloqueLigneCourse.textViewNomProduit.setText(ligneCourse.getProduit().getNom());
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast message = Toast.makeText(monContext, //display toast message
                            ligneCourse.getProduit().getNom(), Toast.LENGTH_SHORT);
                    message.show();
                }
            });
            vueBloqueLigneCourse.spinnerUnite.setSelection(listeUnites.retournerPositionDansLaListe(ligneCourse.getUnite().getId()));
            vueBloqueLigneCourse.spinnerQuantite.setSelection(ligneCourse.getQuantite()-1);
            vueBloqueLigneCourse.actionLigneProduit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseActuelle.getMesLignesCourse().remove(ligneCourse);
                    envoyerMessagePourActualisation("panier");
                }
            });

            vueBloqueLigneCourse.spinnerQuantite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

            vueBloqueLigneCourse.spinnerUnite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

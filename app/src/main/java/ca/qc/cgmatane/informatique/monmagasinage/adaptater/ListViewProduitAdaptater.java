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
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Unites;

import java.util.ArrayList;
import java.util.List;

public class ListViewProduitAdaptater extends ArrayAdapter<Produit> {
    private Unites listeUnites;
    private Produits listeProduits;
    private Context monContext;

    private Course course;
    private ArrayAdapter<String> adaptaterQuantite;

    private static class VueBloque {
        TextView textViewNomProduit;
        Spinner spinnerQuantite;
        Spinner spinnerUnite;
        Button actionLigneProduit;
    }
    public ListViewProduitAdaptater(Produits listeProduits, Course pcourse, Activity context) {
        super(context, R.layout.ligne_listview_produit);
        this.listeProduits = listeProduits;
        this.monContext = context;

        listeUnites = UniteDAO.getInstance().getListeUnite();
        course = pcourse;

        List<String> listPourSpinner = new ArrayList<String>();
        for (int i = 1;i<10;i++){
            listPourSpinner.add(i + "");
        }
        adaptaterQuantite= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listPourSpinner);
    }


    @Override
    public int getCount() {
        return listeProduits.size();
    }

    @Override
    public Produit getItem(int position) {
        return listeProduits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produit produitSelectionne = (Produit) this.getItem(position);
        VueBloque vueBloque; // view lookup cache stored in tag

        final View result;
        if (convertView == null) {
            //convertView = layoutInflater.inflate(R.layout.ligne_listview_produit, null);
            vueBloque = new VueBloque();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ligne_listview_produit, parent, false);

            vueBloque.textViewNomProduit = convertView.findViewById(R.id.ligne_listview_produit_nom_produit);
            vueBloque.spinnerQuantite =  convertView.findViewById(R.id.ligne_listview_produit_spinner_quantite);
            vueBloque.spinnerUnite = convertView.findViewById(R.id.ligne_listview_produit_spinner_unite);
            vueBloque.actionLigneProduit = convertView.findViewById(R.id.ligne_listview_produit_button_action);

            vueBloque.spinnerUnite.setAdapter(listeUnites.recuperAdapterPourSpinner(monContext));
            vueBloque.spinnerQuantite.setAdapter(adaptaterQuantite);

            result=convertView;
            convertView.setTag(vueBloque);
        }else {
            vueBloque = (VueBloque) convertView.getTag();
            result=convertView;

        }
        Animation animation = AnimationUtils.loadAnimation(monContext, (position > lastPosition) ? R.anim.haut_vers_le_bas : R.anim.bas_vers_le_haut);
        result.startAnimation(animation);
        lastPosition = position;


            vueBloque.textViewNomProduit.setText(produitSelectionne.getNom().toLowerCase());
            LigneCourse ligneCourse = course.getMesLignesCourse().trouverAvecIdProduit(produitSelectionne.getId());

            if(ligneCourse!= null ){
                //Le produit est dans le panier
                vueBloque.actionLigneProduit.setText("-");
                vueBloque.spinnerQuantite.setSelection(ligneCourse.getQuantite()-1);
                vueBloque.spinnerUnite.setSelection(listeUnites.retournerPositionDansLaListe(ligneCourse.getUnite().getId()));
                convertView.setBackgroundColor(0xFFB4E2B1);
                vueBloque.spinnerQuantite.setEnabled(false);
                vueBloque.spinnerUnite.setEnabled(false);
                vueBloque.actionLigneProduit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        course.getMesLignesCourse().remove(ligneCourse);
                        envoyerMessagePourActualisation("produits");
                    }
                });

            }else {
                //Le produit n'est pas dans le panier
                vueBloque.actionLigneProduit.setText("+");
                vueBloque.spinnerUnite.setSelection(listeUnites.retournerPositionDansLaListe(produitSelectionne.getUniteDefaut().getId()));
                vueBloque.spinnerQuantite.setSelection(produitSelectionne.getQuantiteDefaut()-1);
                convertView.setBackgroundColor(0x000000);
                vueBloque.spinnerQuantite.setEnabled(true);
                vueBloque.spinnerUnite.setEnabled(true);
                vueBloque.actionLigneProduit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LigneCourse ligneCourse = new LigneCourse(course, produitSelectionne, vueBloque.spinnerQuantite.getSelectedItemPosition()+1, false);
                        ligneCourse.setUnite(listeUnites.get(vueBloque.spinnerUnite.getSelectedItemPosition()));
                        course.getMesLignesCourse().add(ligneCourse);
                        envoyerMessagePourActualisation("produits");
                    }
                });
            }

        // Return the completed view to render on screen
        return convertView;

    }

    private void envoyerMessagePourActualisation(String message) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("event_recharger_affichage");
        // You can also include some extra data.
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(monContext).sendBroadcast(intent);
    }

    public Produits getListeProduits() {
        return listeProduits;
    }

    public void setListeProduits(Produits listeProduits) {
        this.listeProduits = listeProduits;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

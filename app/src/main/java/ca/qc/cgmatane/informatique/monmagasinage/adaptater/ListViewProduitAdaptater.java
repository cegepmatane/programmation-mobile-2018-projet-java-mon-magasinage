package ca.qc.cgmatane.informatique.monmagasinage.adaptater;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
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
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Unites;
import ca.qc.cgmatane.informatique.monmagasinage.vue.VueAjouterCourse;

public class ListViewProduitAdaptater extends BaseAdapter {
    private Unites listeUnites;
    private Produits listeProduits;
    private LayoutInflater layoutInflater = null;
    private Activity context;

    private Course course;

    public ListViewProduitAdaptater(Produits listeProduits, Course pcourse, Activity context) {
        this.listeProduits = listeProduits;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;

        listeUnites = UniteDAO.getInstance().getListeUnite();
        course = pcourse;
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

            spinnerUnite.setAdapter(listeUnites.recuperAdapterPourSpinner(context));
            List<String> listPourSpinner = new ArrayList<String>();
            for (int i = 1;i<10;i++){
                listPourSpinner.add(i + "");
            }
            //TODO à faire ailleur pour eviter de la faire à chaque ligne
            spinnerQuantite.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listPourSpinner));

            final Produit produitSelectionne = listeProduits.get(position);
            if (produitSelectionne != null){
                LigneCourse ligneCourse = course.getMesLignesCourse().trouverAvecIdProduit(produitSelectionne.getId());
                if(ligneCourse != null){
                    //Le produit est dans le panier
                    actionLigneProduit.setText("suprr");
                    spinnerQuantite.setSelection(ligneCourse.getQuantite()-1);
                    spinnerUnite.setSelection(listeUnites.retournerPositionDansLaListe(ligneCourse.getUnite().getId()));
                    convertView.setBackgroundColor(0xFFB4E2B1);
                    spinnerQuantite.setEnabled(false);
                    spinnerUnite.setEnabled(false);
                    actionLigneProduit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            course.getMesLignesCourse().remove(ligneCourse);
                            envoyerMessagePourActualisation("produits");
                        }
                    });
                }else {
                    //Le produit n'est pas dans le panier
                    actionLigneProduit.setText("add");
                    spinnerUnite.setSelection(listeUnites.retournerPositionDansLaListe(produitSelectionne.getUniteDefaut().getId()));
                    spinnerQuantite.setSelection(produitSelectionne.getQuantiteDefaut()-1);

                    actionLigneProduit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LigneCourse ligneCourse = new LigneCourse(course, produitSelectionne, spinnerQuantite.getSelectedItemPosition()+1, false);
                            ligneCourse.setUnite(listeUnites.get(spinnerUnite.getSelectedItemPosition()));
                            course.getMesLignesCourse().add(ligneCourse);
                            envoyerMessagePourActualisation("produits");
                        }
                    });
                }
                textViewNomProduit.setText(produitSelectionne.getNom().toLowerCase());

                }

            return convertView;
        }
        return convertView;

    }

    private void envoyerMessagePourActualisation(String message) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent(VueAjouterCourse.EVENT_RECHARGER_AFFICHAGE);
        // You can also include some extra data.
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
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

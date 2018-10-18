package ca.qc.cgmatane.informatique.monmagasinage.adaptater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.UniteDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Unites;

public class ListViewLigneFaireCourseAdapter extends ArrayAdapter<LigneCourse> {
    private LignesCourse listeCourse;
    private Course course;
    private Unites listeUnites;
    private Course courseActuelle;
    private Context monContext;
    private ArrayAdapter<String> adaptaterQuantite;
    private int lastPosition = -1;
    private static class VueBloque {
        TextView textViewNomProduit;
        TextView textViewQuantite;
        TextView textViewUnite;
        CheckBox checkBoxLigneProduit;
    }
    public ListViewLigneFaireCourseAdapter(LignesCourse listeLignes,Course course,  Activity context) {
        super(context, R.layout.ligne_listview_faire_course);
        this.listeCourse = listeCourse;
        this.monContext = context;
        courseActuelle = course;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LigneCourse ligneCourse = (LigneCourse) this.getItem(position);
        VueBloque vueBloque;
        final View result;
        if (convertView == null) {
            //convertView = layoutInflater.inflate(R.layout.ligne_listview_produit, null);
            vueBloque = new VueBloque();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ligne_listview_produit, parent, false);

            vueBloque.textViewNomProduit = convertView.findViewById(R.id.ligne_listview_faire_course_nom_produit);
            vueBloque.textViewQuantite =  convertView.findViewById(R.id.ligne_listview_faire_course_quantite);
            vueBloque.textViewUnite = convertView.findViewById(R.id.ligne_listview_faire_course_unite);
            vueBloque.checkBoxLigneProduit = convertView.findViewById(R.id.ligne_listview_faire_course_check_box);


            result=convertView;
            convertView.setTag(vueBloque);
        }else {
            vueBloque = (VueBloque) convertView.getTag();
            result=convertView;
        }
        Animation animation = AnimationUtils.loadAnimation(monContext, (position > lastPosition) ? R.anim.haut_vers_le_bas : R.anim.bas_vers_le_haut);
        result.startAnimation(animation);
        lastPosition = position;


        vueBloque.textViewNomProduit.setText(ligneCourse.getProduit().getNom());
        vueBloque.textViewUnite.setText(ligneCourse.getUnite().getLibelle());
        vueBloque.textViewQuantite.setText(ligneCourse.getQuantite());
        vueBloque.checkBoxLigneProduit.setSelected(ligneCourse.isCoche());
        vueBloque.checkBoxLigneProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast message = Toast.makeText(monContext, //display toast message
                        "Check", Toast.LENGTH_SHORT);
                message.show();
            }
        });
        return convertView;
    }
}

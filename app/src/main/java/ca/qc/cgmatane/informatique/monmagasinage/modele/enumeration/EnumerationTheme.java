package ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.monmagasinage.R;

public enum EnumerationTheme {
    SOMBRE("Théme sombre", R.style.ThemeSombre),
    LUMINEUX("Théme lumineux", R.style.ThemeLumineux),
    LUMINEUX_2("Théme lumineux test", R.style.ThemeLumineux)
    ;

    private String nom ="";
    private int idLien;
    private static EnumerationTheme themeSelectionne = SOMBRE;

    EnumerationTheme(String nom, int lien) {
        this.nom = nom;
        this.idLien = lien;
    }

    public String getNom() {
        return nom;
    }

    public int getIdLien() {
        return idLien;
    }

    public static EnumerationTheme getThemeSelectionne() {
        return themeSelectionne;
    }


    public static void setThemeSelectionne(EnumerationTheme themeSelectionne) {
        EnumerationTheme.themeSelectionne = themeSelectionne;
    }

    /***
     * Adapter avec une liste de nom de théme
     * @param context Context
     * @return ArrayAdaptater
     */
    public static ArrayAdapter recuperereListeThemesPourSpinner(Context context){
        //TODO changer pour eviter de recréé un objet
        List<String> listPourSpinner = new ArrayList<String>();
        for (EnumerationTheme theme: EnumerationTheme.values()){
            listPourSpinner.add(theme.getNom());
        }
        ArrayAdapter<String> adapterThemes = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listPourSpinner);

        return  adapterThemes;
    }

    public static EnumerationTheme retournerThemeParPosition(int postition){
        //TODO changer pour eviter de recréé un objet
        int i=0;
        for (EnumerationTheme theme: EnumerationTheme.values()){
            if(postition == i){
                return theme;
            }
            i++;
        }
        return  null;
    }
    /***
     * Changer le théme de l'activity
     * @param context activity
     */
    public static void changerTheme(Context context){
        System.out.println(" Choix du théme");
        context.setTheme(themeSelectionne.idLien);
    }

    /***
     * Relance l'activité pour afficher le nouveau théme
     * @param appCompatActivity
     */
    public static void reactualiserActiviteAvecTheme(AppCompatActivity appCompatActivity){
        Intent intent = new Intent(appCompatActivity, appCompatActivity.getClass());
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish();
    }
}

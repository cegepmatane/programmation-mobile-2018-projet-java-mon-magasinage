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
    DEFAULT("Théme par defaut", R.style.AppTheme, R.style.AppTheme_NoActionBar),
    SOMBRE("Théme sombre", R.style.ThemeSombre, R.style.ThemeSombreNoActionBar),
    LUMINEUX("Théme lumineux", R.style.ThemeLumineux, R.style.ThemeLumineuxNoActionBar),
    VERT("Théme vert", R.style.ThemeVert, R.style.ThemeVertNoActionBar)
    ;

    private String nom ="";
    private int idLien;
    private int idLienSansActionBar;
    private static EnumerationTheme themeSelectionne = DEFAULT;
    private static ArrayList<EnumerationTheme> mesThemes = new ArrayList<EnumerationTheme>();

    EnumerationTheme(String nom, int idLien, int idLienSansActionBar) {
        this.nom = nom;
        this.idLien = idLien;
        this.idLienSansActionBar = idLienSansActionBar;
    }

    public static ArrayList<EnumerationTheme> getMesThemes() {
        /** Charge les thémes dans une arrayliste pour éviter de recréer des instances de thémes à chaque .values()*/
        if(mesThemes.size() <1){
            for (EnumerationTheme theme: EnumerationTheme.values()){
                mesThemes.add(theme);
            }
        }
        return mesThemes;
    }

    public String getNom() {
        return nom;
    }

    public int getIdLien() {
        return idLien;
    }

    public int getIdLienSansActionBar() {
        return idLienSansActionBar;
    }

    public void setIdLienSansActionBar(int idLienSansActionBar) {
        this.idLienSansActionBar = idLienSansActionBar;
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
        for (EnumerationTheme theme: EnumerationTheme.getMesThemes()){
            listPourSpinner.add(theme.getNom());
        }
        ArrayAdapter<String> adapterThemes = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listPourSpinner);

        return  adapterThemes;
    }

    public static EnumerationTheme retournerThemeParPosition(int postition){
        //TODO changer pour eviter de recréé un objet
        int i=0;
        for (EnumerationTheme theme: EnumerationTheme.getMesThemes()){
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

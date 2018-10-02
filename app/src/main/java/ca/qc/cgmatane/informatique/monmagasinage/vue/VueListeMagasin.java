package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Magasins;


public class VueListeMagasin extends AppCompatActivity {
    static final public int ACTIVITE_AJOUTER_MAGASIN = 1;
    static final public int ACTIVITE_MODIFIER_MAGASIN= 2;

    protected ListView vueListeMagasin;
    protected Magasins listeMagasinsAffichage;
    protected Magasins listeMagasins;

    protected Intent intentionNaviguerAjouterMagasin;
    /*protected Intent intentionNaviguerAjouterAlarme;*/
    protected MagasinDAO accesseurMagasin ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_magasin);

  /*      BaseDeDonnees.getInstance(getApplicationContext());*/
        accesseurMagasin = MagasinDAO.getInstance();
        listeMagasinsAffichage = new Magasins();
        listeMagasins = accesseurMagasin.listerMagasins();

        vueListeMagasin = (ListView) findViewById(R.id.vue_liste_titre);

        actualisationAffichage();

        vueListeMagasin.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View vue,
                                            int positionDansAdapteur,
                                            long positionItem) {
                        ListView vueListeMagasin = (ListView)vue.getParent();

                        @SuppressWarnings("unchecked")
                        HashMap<String,String> magasin =
                                (HashMap<String, String>)
                                        vueListeMagasin.getItemAtPosition((int)positionItem);






                        Intent intentionNaviguerModiferMagasin = new Intent(
                                VueListeMagasin.this,
                                VueModifierMagasin.class
                        );
                        intentionNaviguerModiferMagasin.putExtra(Magasin.CHAMP_ID,
                                magasin.get(Magasin.CHAMP_ID));
                        System.out.println(magasin.get(Magasin.CHAMP_ID));
                        startActivityForResult(intentionNaviguerModiferMagasin,
                                ACTIVITE_MODIFIER_MAGASIN);

                    }}
        );
        intentionNaviguerAjouterMagasin = new Intent(this,
                VueAjouterMagasin.class);

        Button actionNaviguerAjouterMagasin =
                (Button)findViewById(R.id.action_naviguer_ajouter_magasin);

        actionNaviguerAjouterMagasin.setOnClickListener(

                new View.OnClickListener()
                {
                    public void onClick(View arg0) {
                        startActivityForResult(intentionNaviguerAjouterMagasin, ACTIVITE_AJOUTER_MAGASIN);
                    }
                }
        );


    }
    public Magasins simulerListeMagasins(){
        Magasins liste = new Magasins();
        Magasin mag1 = new Magasin();
        mag1.setId(1);
        mag1.setNom("Magasin1");
        Magasin mag2 = new Magasin();
        mag2.setId(2);
        mag2.setNom("Magasin2");
        Magasin mag3 = new Magasin();
        mag3.setId(3);
        mag3.setNom("Magasin3");
        liste.add(mag1);
        liste.add(mag2);
        liste.add(mag3);
        return liste;
    }



    protected void onActivityResult(int activite, int resultat, Intent donnees)
    {
        switch(activite)
        {
            case ACTIVITE_MODIFIER_MAGASIN:
                actualisationAffichage();
                break;
            case ACTIVITE_AJOUTER_MAGASIN:
               actualisationAffichage();
                break;
        }

    }
    private void actualisationAffichage() {
        listeMagasinsAffichage.clear();
        listeMagasins = accesseurMagasin.getListeMagasins();
        for(Magasin magasin: listeMagasins){
                listeMagasinsAffichage.add(magasin);
        }

        afficherTousLesMagasins();
    }

    private void afficherTousLesMagasins(){
        SimpleAdapter adapterListeCourses = new SimpleAdapter(this, listeMagasinsAffichage.recuperereListeMagasinPourAdapteur(), android.R.layout.two_line_list_item,
                new String[]{Magasin.CHAMP_NOM, Magasin.CHAMP_ADRESSE},
                new int[]{ android.R.id.text1, android.R.id.text2});

        vueListeMagasin.setAdapter(adapterListeCourses);
    }
}
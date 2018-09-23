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


public class VueListeMagasin extends AppCompatActivity {
    static final public int ACTIVITE_AJOUTER_MAGASIN = 1;
    static final public int ACTIVITE_MODIFIER_MAGASIN= 2;

    protected ListView vueListeMagasin;
    protected List<HashMap<String, String>> listeMagasinPourAdapteur;

    protected Intent intentionNaviguerAjouterMagasin;
    /*protected Intent intentionNaviguerAjouterAlarme;*/
    protected MagasinDAO accesseurMagasin ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_magasin);

  /*      BaseDeDonnees.getInstance(getApplicationContext());*/
        accesseurMagasin = MagasinDAO.getInstance();

        vueListeMagasin = (ListView) findViewById(R.id.vue_liste_titre);

        afficherTousLesMagasins();

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
                        intentionNaviguerModiferMagasin.putExtra("id_magasin",
                                magasin.get("id_magasin"));
                        System.out.println(magasin.get("id_magasin"));
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

    protected void afficherTousLesMagasins()
    {
        listeMagasinPourAdapteur = accesseurMagasin.recuperereListeMagasinPourAdapteur();
        SimpleAdapter adapteur = new SimpleAdapter(
                this,
                listeMagasinPourAdapteur,
                android.R.layout.two_line_list_item,
                new String[] {"Nom","Lieu"},
                new int[] {android.R.id.text1, android.R.id.text2});

        vueListeMagasin.setAdapter(adapteur);
    }

   /* public Intent getIntentionNaviguerAjouterAlarme() {
        return intentionNaviguerAjouterAlarme;
    }*/

    protected void onActivityResult(int activite, int resultat, Intent donnees)
    {
        switch(activite)
        {
            case ACTIVITE_MODIFIER_MAGASIN:
                afficherTousLesMagasins();
                break;
            case ACTIVITE_AJOUTER_MAGASIN:
                afficherTousLesMagasins();
                break;
          /*  case ACTIVITE_AJOUTER_ALARME:
                afficherTousLesMagasins();
                break;*/
        }

    }
}
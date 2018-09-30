package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueModifierMagasin extends AppCompatActivity{
    private MagasinDAO accesseurMagasins;
    private Magasin magasin;
    private EditText champNom;
    private EditText champAdresse;
    private EditText champVille;
    private EditText champCoorX;
    private EditText champCoorY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_magasin);

        this.accesseurMagasins = MagasinDAO.getInstance();

        Bundle parametres = this.getIntent().getExtras();
        String parametre_id_magasin = (String) parametres.get("id_magasin");
        int id_magasin = Integer.parseInt(parametre_id_magasin);

        magasin = accesseurMagasins.trouverMagasin(id_magasin);

        champNom = (EditText)findViewById(R.id.vue_modifier_magasin_champ_nom);
        champAdresse = (EditText)findViewById(R.id.vue_modifier_magasin_champ_adresse);
        champVille= (EditText)findViewById(R.id.vue_modifier_magasin_champ_ville);
        champCoorX = (EditText)findViewById(R.id.vue_modifier_magasin_champ_coorx);
        champCoorY = (EditText)findViewById(R.id.vue_modifier_magasin_champ_coory);

        champNom.setText(magasin.getNom());
        champAdresse.setText(magasin.getAdresse());
        champVille.setText(magasin.getVille());
        champCoorX.setText(magasin.getCoorX().toString());
        champCoorY.setText(magasin.getCoorY().toString());

        Button actionModifierMagasin =
                (Button)findViewById(R.id.action_modifier_magasin);

        actionModifierMagasin.setOnClickListener(

                new View.OnClickListener()
                {
                    public void onClick(View arg0) {
                        modifierMagasin();
                    }
                }
        );

    }
    public void modifierMagasin(){
        Magasin magasin = new Magasin(this.magasin.getId(),
                champNom.getText().toString(),
                champAdresse.getText().toString(),
                champVille.getText().toString(),
                Double.valueOf(champCoorX.getText().toString()),
                Double.valueOf(champCoorY.getText().toString()));


        accesseurMagasins.modifierMagasin(magasin);
        naviguerRetourListeMagasins();

    }
    public void naviguerRetourListeMagasins(){
        this.finish();
    }
}

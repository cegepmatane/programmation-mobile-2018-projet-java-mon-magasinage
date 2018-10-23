package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueModifierTheme extends AppCompatActivity {
    protected Button actionModifierTheme;
    protected Spinner listeDeroulanteTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_theme);
        getSupportActionBar().setTitle("Changer le theme");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionModifierTheme = findViewById(R.id.vue_modifier_theme_action_actualiser_theme);
        listeDeroulanteTheme = findViewById(R.id.vue_modifier_theme_spinner_theme);
        changerTextAction();

        listeDeroulanteTheme.setAdapter(EnumerationTheme.recuperereListeThemesPourSpinner(this));
        //TODO à oter pour optimiser
        int i =0;
        for (EnumerationTheme theme : EnumerationTheme.values()){
            if(theme.getIdLien() == EnumerationTheme.getThemeSelectionne().getIdLien()){
                listeDeroulanteTheme.setSelection(i);
                break;
            }
            i++;
        }

        actionModifierTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EnumerationTheme.reactualiserActiviteAvecTheme(VueModifierTheme.this);
            }
        });



        listeDeroulanteTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EnumerationTheme enumerationTheme = EnumerationTheme.getMesThemes().get(position);
                if(enumerationTheme != null){
                    EnumerationTheme.setThemeSelectionne(enumerationTheme, getApplicationContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    protected void changerTextAction(){

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

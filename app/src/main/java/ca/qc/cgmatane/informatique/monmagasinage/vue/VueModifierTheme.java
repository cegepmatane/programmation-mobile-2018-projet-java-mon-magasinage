package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueModifierTheme extends AppCompatActivity {
    protected Button actionModifierTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(EnumerationTheme.isThemeSombre() ? R.style.ThemeSombre : R.style.ThemeLumineux);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_theme);
        actionModifierTheme = findViewById(R.id.vue_modifier_theme_action_changer_theme);
        changerTextAction();
        actionModifierTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnumerationTheme.setThemeSombre();
                changerTextAction();
                setTheme(EnumerationTheme.isThemeSombre() ? R.style.ThemeSombre : R.style.ThemeLumineux);
                Intent intent = new Intent(VueModifierTheme.this, VueModifierTheme.class);
                startActivity(intent);
                finish();
            }
        });

        Button actionQuitter = findViewById(R.id.vue_modifier_theme_action_quitter);
        actionQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void changerTextAction(){
        if(EnumerationTheme.isThemeSombre()){
            actionModifierTheme.setText(" Changer pour theme lumineux");
        }else {
            actionModifierTheme.setText("Changer pour theme sombre");
        }
    }
}

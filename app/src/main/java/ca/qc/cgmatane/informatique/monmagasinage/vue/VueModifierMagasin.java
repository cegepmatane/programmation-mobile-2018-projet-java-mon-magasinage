package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueModifierMagasin extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(EnumerationTheme.isThemeSombre() ? R.style.ThemeSombre : R.style.ThemeLumineux);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_magasin);
    }
}

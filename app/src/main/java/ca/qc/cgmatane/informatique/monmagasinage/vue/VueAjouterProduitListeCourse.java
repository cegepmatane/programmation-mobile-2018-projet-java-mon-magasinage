package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.qc.cgmatane.informatique.monmagasinage.R;

public class VueAjouterProduitListeCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_produit_liste_course);
        Button actionNaviguerAjouterProduitCourse = (Button) findViewById(R.id.vue_ajouter_produit_liste_course_action_enregistrer);
        actionNaviguerAjouterProduitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

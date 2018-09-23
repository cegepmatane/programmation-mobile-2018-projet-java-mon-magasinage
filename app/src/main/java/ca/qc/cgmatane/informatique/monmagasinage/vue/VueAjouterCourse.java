package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.qc.cgmatane.informatique.monmagasinage.R;

public class VueAjouterCourse extends AppCompatActivity {

    private final int AJOUTER_PRODUIT_ID_RETOUR=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_course);

        Button actionNaviguerAjouterProduitCourse = (Button) findViewById(R.id.vue_ajouter_course_action_ajouter_produit);
        actionNaviguerAjouterProduitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionNaviguerAjouterProduitCourse = new Intent(VueAjouterCourse.this, VueAjouterProduitListeCourse.class);
                startActivityForResult(intentionNaviguerAjouterProduitCourse, AJOUTER_PRODUIT_ID_RETOUR);
            }
        });
    }
}

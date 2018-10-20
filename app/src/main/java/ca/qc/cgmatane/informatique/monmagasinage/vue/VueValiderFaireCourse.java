package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;

public class VueValiderFaireCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_valider_faire_course);

        Bundle parametres = this.getIntent().getExtras();
        assert parametres != null;
        Bitmap imageBitmap = (Bitmap) parametres.get("photo");

        ImageView vueImage =  findViewById(R.id.vue_valider_course_vue_image);
        vueImage.setImageBitmap(imageBitmap);

    }
}

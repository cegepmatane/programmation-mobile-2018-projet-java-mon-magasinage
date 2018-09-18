package ca.qc.cgmatane.informatique.monmagasinage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import ca.qc.cgmatane.informatique.monmagasinage.vue.VueAjouterCourse;
import ca.qc.cgmatane.informatique.monmagasinage.vue.VueListeMagasin;

public class ListeCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton actionNaviguerAjouterCourse = (FloatingActionButton) findViewById(R.id.vue_liste_course_action_naviguer_ajouter_course);
        actionNaviguerAjouterCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intentionNaviguerAjouterCourse = new Intent(ListeCourse.this, VueAjouterCourse.class);
                startActivity(intentionNaviguerAjouterCourse);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_gestion_magasin) {
            Intent intentionNaviguerVueGestionMagasin = new Intent(this, VueListeMagasin.class);
            startActivity(intentionNaviguerVueGestionMagasin);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

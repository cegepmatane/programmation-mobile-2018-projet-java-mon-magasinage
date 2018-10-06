package ca.qc.cgmatane.informatique.monmagasinage.vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.MagasinDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration.EnumerationTheme;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class VueAjouterMagasin extends AppCompatActivity {
    protected EditText champNom;
    protected EditText champAdresse;
    protected EditText champVille;
    protected EditText champCoorX;
    protected EditText champCoorY;
    private String nom;
    private String adresse;
    private String ville;
    private double coorX;
    private double coorY;
    protected MagasinDAO accesseurMagasin = MagasinDAO.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnumerationTheme.changerTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_magasin);
        champNom = (EditText)findViewById(R.id.vue_ajouter_magasin_champ_nom);
        champAdresse = (EditText)findViewById(R.id.vue_ajouter_magasin_champ_adresse);
        champVille= (EditText)findViewById(R.id.vue_ajouter_magasin_champ_ville);
        champCoorX = (EditText)findViewById(R.id.vue_ajouter_magasin_champ_coorx);
        champCoorY = (EditText)findViewById(R.id.vue_ajouter_magasin_champ_coory);


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("CA")
                .build();

        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                    String adresseComplete = place.getAddress().toString();
                    String adresse[] = adresseComplete.split(", ");
                    champNom.setText(place.getName());
                    champAdresse.setText(adresse[0]);
                    champVille.setText(adresse[1]);
                    champCoorX.setText(String.valueOf(place.getLatLng().latitude));
                    champCoorY.setText(String.valueOf(place.getLatLng().longitude));
            }

            @Override
            public void onError(Status status) {

                System.out.println("error");
            }
        });
        Button actionEnregistrerMagasin =
                (Button)findViewById(R.id.action_enregistrer_magasin);

        actionEnregistrerMagasin.setOnClickListener(

                new View.OnClickListener()
                {
                    public void onClick(View arg0) {
                        if ((champNom.getText().toString().equals(""))||(champCoorX.getText().toString().equals(""))||(champCoorY.getText().toString().equals(""))){
                            Toast message = Toast.makeText(getApplicationContext(), //display toast message
                                    "Vous devez choisir un nom et et des coordonnées", Toast.LENGTH_SHORT);
                            message.show();
                        }else{
                            enregisterMagasin();
                        }

                    }
                }
        );


    }
    public void naviguerRetourListeMagasins()
    {
        this.finish();
    }

    public void enregisterMagasin(){
        nom = champNom.getText().toString();
        adresse = champAdresse.getText().toString();
        ville = champVille.getText().toString();
        coorX = Double.valueOf(champCoorX.getText().toString());
        coorY = Double.valueOf(champCoorY.getText().toString());


        Magasin magasin = new Magasin(nom, adresse, ville, coorX, coorY);
        accesseurMagasin.ajouterMagasin(magasin);
        naviguerRetourListeMagasins();
    }
}

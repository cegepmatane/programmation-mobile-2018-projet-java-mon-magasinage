package ca.qc.cgmatane.informatique.monmagasinage.vue.historique;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.adaptater.ListViewLigneFaireCourseAdapter;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.CourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.LigneCourseDAO;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.vue.VueFaireCourse;
import ca.qc.cgmatane.informatique.monmagasinage.vue.historique.dummy.DummyContent;

import java.time.format.DateTimeFormatter;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link VueHistoriqueCourse}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");


    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private Course course;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int id_course= Integer.parseInt(getArguments().getString(ARG_ITEM_ID));
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            course = CourseDAO.getInstance().trouverParId(id_course);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.

        //((TextView) rootView.findViewById(R.id.item_detail)).setText("Erreur sur le chargement de la course");

        if (course != null) {
            ((TextView) rootView.findViewById(R.id.vue_historique_nom_course)).setText(course.getNom());
            ((TextView) rootView.findViewById(R.id.vue_historique_date_realisation)).setText(course.getDateRealisation().format(formatter));
            LigneCourseDAO.getInstance().chargerListeLigneCoursePourUneCourse(course);
            ListViewLigneFaireCourseAdapter listViewLigneFaireCourseAdapter = new ListViewLigneFaireCourseAdapter(course, getActivity());
            ((ListView) rootView.findViewById(R.id.vue_historique_liste_panier)).setAdapter(listViewLigneFaireCourseAdapter);

        }

        return rootView;
    }
}

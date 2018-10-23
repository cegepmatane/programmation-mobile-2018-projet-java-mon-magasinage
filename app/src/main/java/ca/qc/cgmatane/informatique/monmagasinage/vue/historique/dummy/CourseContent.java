package ca.qc.cgmatane.informatique.monmagasinage.vue.historique.dummy;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Courses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseContent {
    /**
     * An array of sample (dummy) items.
     */
    public static final ArrayList<Course> ITEMS = new ArrayList<Course>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Course> ITEM_MAP = new HashMap<String, Course>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Course item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId()+"", item);
    }

    private static Course createDummyItem(int position) {
        Course course = new Course(position, "course "+position, LocalDateTime.now(), LocalDateTime.now());
        return course;
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}

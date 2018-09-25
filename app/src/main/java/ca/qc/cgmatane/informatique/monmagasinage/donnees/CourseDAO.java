package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.database.Cursor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.CourseSQL;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Courses;

public class CourseDAO implements CourseSQL{
    private static CourseDAO instance = null;
    private BaseDeDonnees accesseurBaseDeDonnees;

    protected Courses listeCourses;

    public static CourseDAO getInstance(){
        if(instance == null){
            instance = new CourseDAO();
        }
        return instance;
    }

    public CourseDAO() {
        this.listeCourses = new Courses();
        this.accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        listerCourses();
    }

    public Courses listerCourses(){
        Cursor curseurCourses = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_COURSE, null);
        this.listeCourses.clear();
        Course course;

        int indexId = curseurCourses.getColumnIndex(Course.CHAMP_ID_COURSE);
        int indexNom = curseurCourses.getColumnIndex(Course.CHAMP_NOM);
        int indexDateNotification = curseurCourses.getColumnIndex(Course.CHAMP_DATE_NOTIFICATION);
        int indexDateRealisation = curseurCourses.getColumnIndex(Course.CHAMP_DATE_REALISATION);
        //TODO à gérer plus tard
        int indexIdCourseOriginal = curseurCourses.getColumnIndex(Course.CHAMP_ID_COURSE_ORIGINAL);

        for(curseurCourses.moveToFirst();!curseurCourses.isAfterLast();curseurCourses.moveToNext()){
            int id_course= curseurCourses.getInt(indexId);
            String nom= curseurCourses.getString(indexNom);
            String dateNotification = curseurCourses.getString(indexDateNotification);
            String dateRealisation = curseurCourses.getString(indexDateNotification);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
            /*LocalDateTime dateTime = LocalDateTime.parse(str, formatter);*/
            course = new Course(id_course, nom, LocalDateTime.parse(dateNotification, formatter), LocalDateTime.parse(dateRealisation, formatter));

            this.listeCourses.add(course);
        }

        curseurCourses.close();

        return this.listeCourses;
    }

    public Course recupererCourseAvecId(int id) {
        for (Course course : this.listeCourses){
            if (course.getId()==id){
                return course;
            }
        }
        return null;
    }
}

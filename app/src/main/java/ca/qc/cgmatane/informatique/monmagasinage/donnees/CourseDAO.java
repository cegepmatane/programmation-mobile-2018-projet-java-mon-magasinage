package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.LocaleDisplayNames;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.xml.validation.Validator;

import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.CourseSQL;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Courses;

public class CourseDAO implements CourseSQL{
    private MagasinDAO magasinDAO;
    private static CourseDAO instance = null;
    private BaseDeDonnees accesseurBaseDeDonnees;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    protected Courses listeCourses;

    public static CourseDAO getInstance(){
        if(instance == null){
            instance = new CourseDAO();
        }
        return instance;
    }

    private CourseDAO() {
        this.listeCourses = new Courses();
        this.accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        this.magasinDAO = MagasinDAO.getInstance();
    }

    public Courses listerCourses(){
        Cursor curseurCourses = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_COURSE, null);
        this.listeCourses.clear();

        magasinDAO.listerMagasins();//Chargement des magasins
        Course course;

        int indexId = curseurCourses.getColumnIndex(Course.CHAMP_ID_COURSE);
        int indexNom = curseurCourses.getColumnIndex(Course.CHAMP_NOM);
        int indexDateNotification = curseurCourses.getColumnIndex(Course.CHAMP_DATE_NOTIFICATION);
        int indexDateRealisation = curseurCourses.getColumnIndex(Course.CHAMP_DATE_REALISATION);
        int indexIdMagasin = curseurCourses.getColumnIndex(Course.CHAMP_ID_MAGASIN);
        //TODO à gérer plus tard
        int indexIdCourseOriginal = curseurCourses.getColumnIndex(Course.CHAMP_ID_COURSE_ORIGINAL);

        for(curseurCourses.moveToFirst();!curseurCourses.isAfterLast();curseurCourses.moveToNext()){
            int id_course= curseurCourses.getInt(indexId);
            String nom= curseurCourses.getString(indexNom);
            String str_dateNotification = curseurCourses.getString(indexDateNotification);
            String str_realisation = curseurCourses.getString(indexDateRealisation);
            int id_magasin = curseurCourses.getInt(indexIdMagasin);

            LocalDateTime dateNotification;
            LocalDateTime dateRealisation;
            if(!str_dateNotification.equals("")){
                dateNotification = LocalDateTime.parse(str_dateNotification, formatter);
            }else {
                dateNotification =null;
            }
            if(!str_dateNotification.equals("")){
                 dateRealisation = LocalDateTime.parse(str_realisation, formatter);
            }else {
                 dateRealisation = null;
            }

            course = new Course(id_course, nom, dateNotification, dateRealisation);
            course.setMonMagasin(magasinDAO.getListeMagasins().trouverAvecId(id_magasin));
            this.listeCourses.add(course);
        }

        curseurCourses.close();

        return this.listeCourses;
    }

    public int creerCourse(String nom, String dateNotification, String dateRealisation, int idOriginal, Magasin magasin)
    {

        ContentValues values = new ContentValues();
        values.put(Course.CHAMP_NOM,nom);
        values.put(Course.CHAMP_DATE_NOTIFICATION,dateNotification);
        values.put(Course.CHAMP_DATE_REALISATION,dateRealisation);
        values.put(Course.CHAMP_ID_COURSE_ORIGINAL,idOriginal);


        int newId = (int) accesseurBaseDeDonnees.getWritableDatabase().insert(Course.NOM_TABLE,null, values);

        LocalDateTime dateNotificationFormatted = null;
        LocalDateTime dateRealisationFormatted = null;

        if (dateNotification != null && !"".equals(dateNotification))
            dateNotificationFormatted = LocalDateTime.parse(dateNotification, formatter);

        if (dateRealisation != null && !"".equals(dateRealisation))
            dateRealisationFormatted = LocalDateTime.parse(dateRealisation, formatter);

        Course course = new Course(newId,
                nom,
                dateNotificationFormatted,
                dateRealisationFormatted);
        course.setMonMagasin(magasin);
        this.listeCourses.add(course);

        return 0;
    }


    public void modifierCourse(int id, String nom, String dateNotification, String dateRealisation, int idOriginal, Magasin magasin)
    {

        ContentValues values = new ContentValues();
        values.put(Course.CHAMP_NOM,nom);
        values.put(Course.CHAMP_DATE_NOTIFICATION,dateNotification);
        values.put(Course.CHAMP_DATE_REALISATION,dateRealisation);
        values.put(Course.CHAMP_ID_COURSE_ORIGINAL,idOriginal);
        values.put(Course.CHAMP_ID_COURSE_ORIGINAL,idOriginal);

        LocalDateTime dateNotificationFormatted = null;
        LocalDateTime dateRealisationFormatted = null;

        accesseurBaseDeDonnees.getWritableDatabase().update(Course.NOM_TABLE,
                values,
                Course.CHAMP_ID_COURSE+"="+id,
                null);

        if (dateNotification != null && !"".equals(dateNotification))
            dateNotificationFormatted = LocalDateTime.parse(dateNotification, formatter);

        if (dateRealisation != null && !"".equals(dateRealisation))
            dateRealisationFormatted = LocalDateTime.parse(dateRealisation, formatter);

        Course course = new Course(id,
                nom,
                dateNotificationFormatted,
                dateRealisationFormatted);
        course.setMonMagasin(magasin);

        Course courseAmodifier = this.getListeCourses().trouverAvecId(id);
        courseAmodifier.setNom(nom);
        courseAmodifier.setDateNotification(dateNotificationFormatted);
        courseAmodifier.setDateRealisation(dateRealisationFormatted);
        courseAmodifier.setCourseOriginal(null);
        courseAmodifier.setMonMagasin(magasin);
    }

    public Courses getListeCourses() {
        return listeCourses;
    }

    public void setListeCourses(Courses listeCourses) {
        this.listeCourses = listeCourses;
    }
}

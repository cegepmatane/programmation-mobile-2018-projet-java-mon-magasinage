package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import android.content.ContentValues;
import android.database.Cursor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.BaseDeDonnees;
import ca.qc.cgmatane.informatique.monmagasinage.donnees.base.CourseSQL;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Courses;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;

public class CourseDAO implements CourseSQL{
    private MagasinDAO magasinDAO;
    private LigneCourseDAO ligneCourseDAO;
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
        this.ligneCourseDAO = LigneCourseDAO.getInstance();
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
            String str_dateRealisation = curseurCourses.getString(indexDateRealisation);
            int id_magasin = curseurCourses.getInt(indexIdMagasin);

            LocalDateTime dateNotification;
            LocalDateTime dateRealisation;
            if(!str_dateNotification.equals("")){
                dateNotification = LocalDateTime.parse(str_dateNotification, formatter);
            }else {
                dateNotification =null;
            }
            if(!str_dateRealisation.equals("")){
                 dateRealisation = LocalDateTime.parse(str_dateRealisation, formatter);
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

    public void creerCourse(String nom, String dateNotification, String dateRealisation, int idOriginal, Magasin magasin, LignesCourse ligneCourses)
    {

        ContentValues values = new ContentValues();
        values.put(Course.CHAMP_NOM,nom);
        values.put(Course.CHAMP_DATE_NOTIFICATION,dateNotification);
        values.put(Course.CHAMP_DATE_REALISATION,dateRealisation);
        values.put(Course.CHAMP_ID_COURSE_ORIGINAL,idOriginal);
        values.put(Course.CHAMP_ID_MAGASIN, magasin.getId());


        int newId = (int) accesseurBaseDeDonnees.getWritableDatabase().insert(Course.NOM_TABLE,null, values);

        LocalDateTime dateNotificationFormatted = null;
        LocalDateTime dateRealisationFormatted = null;

        if (dateNotification != null && !"".equals(dateNotification))
            dateNotificationFormatted = LocalDateTime.parse(dateNotification, formatter);

        if (dateRealisation != null && !"".equals(dateRealisation))
            dateRealisationFormatted = LocalDateTime.parse(dateRealisation, formatter);
        if(ligneCourseDAO.enregistrerListeLigneCoursePourUneCourse(newId, ligneCourses)){
            Course course = new Course(newId, nom, dateNotificationFormatted, dateRealisationFormatted);
            course.setMonMagasin(magasin);
            course.setMesLignesCourse(ligneCourses);
            this.listeCourses.add(course);
        }

    }


    public void modifierCourse(int id, String nom, String dateNotification, String dateRealisation, int idOriginal, Magasin magasin)
    {

        ContentValues values = new ContentValues();
        values.put(Course.CHAMP_NOM,nom);
        values.put(Course.CHAMP_DATE_NOTIFICATION,dateNotification);
        values.put(Course.CHAMP_DATE_REALISATION,dateRealisation);
        values.put(Course.CHAMP_ID_COURSE_ORIGINAL,idOriginal);
        values.put(Course.CHAMP_ID_MAGASIN, magasin.getId());

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

        Course courseAmodifier = this.getListeCourses().trouverAvecId(id);
        if(ligneCourseDAO.enregistrerListeLigneCoursePourUneCourse(courseAmodifier.getId(), courseAmodifier.getMesLignesCourse())){
            courseAmodifier.setNom(nom);
            courseAmodifier.setDateNotification(dateNotificationFormatted);
            courseAmodifier.setDateRealisation(dateRealisationFormatted);
            courseAmodifier.setCourseOriginal(null);
            courseAmodifier.setMonMagasin(magasin);
        }

    }

    public Courses getListeCourses() {
        return listeCourses;
    }

    public void setListeCourses(Courses listeCourses) {
        this.listeCourses = listeCourses;
    }
}

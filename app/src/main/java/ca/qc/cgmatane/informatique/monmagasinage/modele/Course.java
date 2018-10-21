package ca.qc.cgmatane.informatique.monmagasinage.modele;

import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.LignesCourse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Course {
    public static final String NOM_TABLE = "course";

    public static final String CHAMP_ID_COURSE = "id_course";
    public static final String CHAMP_NOM = "nom";
    public static final String CHAMP_DATE_NOTIFICATION = "notification";
    public static final String CHAMP_DATE_REALISATION = "realisation";
    public static final String CHAMP_ID_COURSE_ORIGINAL = "id_course_original";
    public static final String CHAMP_ID_MAGASIN = "id_magasin";


    private int id;
    private String nom;
    private LocalDateTime dateNotification;
    private LocalDateTime dateRealisation;
    private Course courseOriginal;
    private Magasin monMagasin;
    private LignesCourse mesLignesCourse;

    public Course(int id) {
        this.id = id;
    }

    public Course(int id, String nom, LocalDateTime dateNotification, LocalDateTime dateRealisation) {
        this.id = id;
        this.nom = nom;
        this.dateNotification = dateNotification;
        this.dateRealisation = dateRealisation;
        mesLignesCourse = new LignesCourse();
    }

    public Course() {
        mesLignesCourse = new LignesCourse();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDateTime getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(LocalDateTime dateNotification) {
        this.dateNotification = dateNotification;
    }

    public LocalDateTime getDateRealisation() {
        return dateRealisation;
    }

    public void setDateRealisation(LocalDateTime dateRealisation) {
        this.dateRealisation = dateRealisation;
    }

    public Course getCourseOriginal() {
        return courseOriginal;
    }

    public void setCourseOriginal(Course courseOriginal) {
        this.courseOriginal = courseOriginal;
    }

    public Magasin getMonMagasin() {
        return monMagasin;
    }

    public void setMonMagasin(Magasin monMagasin) {
        this.monMagasin = monMagasin;
    }

    public LignesCourse getMesLignesCourse() {
        return mesLignesCourse;
    }

    public void setMesLignesCourse(LignesCourse mesLignesCourse) {
        this.mesLignesCourse = mesLignesCourse;
    }

    public HashMap<String, String> obtenirObjetPourAdapteur() {
        HashMap<String, String> coursePourAdapteur = new HashMap<String, String>();
        coursePourAdapteur.put(CHAMP_ID_COURSE, String.valueOf(this.id));
        coursePourAdapteur.put(CHAMP_NOM, this.nom);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        if(this.dateNotification != null){
            coursePourAdapteur.put(CHAMP_DATE_NOTIFICATION,"pour le : "+ this.dateNotification.format(formatter));

        }else {
            coursePourAdapteur.put(CHAMP_DATE_NOTIFICATION," non daté ");
        }
        if(this.dateRealisation != null){
            coursePourAdapteur.put(CHAMP_DATE_REALISATION,"pour le : "+ this.dateRealisation.format(formatter));

        }else {
            coursePourAdapteur.put(CHAMP_DATE_REALISATION," non daté ");
        }

        return coursePourAdapteur;
    }

}

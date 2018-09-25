package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;

public interface CourseSQL {
    static String LISTER_COURSE = "select * from course";
    static String CREER_COURSE = String.format("insert into course (%s,%s,%s,%s,%s) values (?,?,?,?,?); SELECT SCOPE_IDENTITY();",
            Course.CHAMP_ID_COURSE_ORIGINAL,
            Course.CHAMP_NOM,
            Course.CHAMP_DATE_NOTIFICATION,
            Course.CHAMP_DATE_REALISATION,
            Course.CHAMP_ID_MAGASIN);

}

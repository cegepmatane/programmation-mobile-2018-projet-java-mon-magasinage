package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;

public interface CourseSQL {
    static String LISTER_COURSE = "select * from "+ Course.NOM_TABLE;
    static String LISTER_COURSE_ACTUELLES = "select * from "+Course.NOM_TABLE+" WHERE "+Course.CHAMP_DATE_REALISATION+"=?";



}

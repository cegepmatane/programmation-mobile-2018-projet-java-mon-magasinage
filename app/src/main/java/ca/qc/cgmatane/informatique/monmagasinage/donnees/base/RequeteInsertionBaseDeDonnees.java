package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;

public interface RequeteInsertionBaseDeDonnees {
    /** Course*/
    public final static String INSERT_COURSE_1=String.format("insert into %s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL,  "Une course exeptionnelle", "2018/10/10", "2018/10/10", null);

    public final static String INSERT_COURSE_2=String.format("insert into %s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL,  "Une course exeptionnelle", "2018/10/10", "2018/10/10", null);

    public final static String INSERT_COURSE_3=String.format("insert into %s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL,  "Une course exeptionnelle", "2018/10/10", "2018/10/10", null);

}

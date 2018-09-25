package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;

public interface RequeteInsertionEchafautBaseDeDonnees {
    /** Magasin */
    public final static String INSERT_MAGASIN_1= String.format("insert into %s (%s, %s, %s, %s, %s, %s) VALUES (%s, '%s', '%s', '%s', %s, %s)",
            Magasin.NOM_TABLE, Magasin.CHAMP_ID, Magasin.CHAMP_NOM, Magasin.CHAMP_ADRESSE, Magasin.CHAMP_VILLE, Magasin.CHAMP_COOR_X, Magasin.CHAMP_COOR_Y,
            1, "IGA", "rue sans nom", "Matane", 54.45, 15.97);

    /** Course*/
    public final static String INSERT_COURSE_1=String.format("insert into %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN, "Une course exeptionnelle", "2016-01-01-10:20", "2016-01-01-10:20", null, 1);

    public final static String INSERT_COURSE_2=String.format("insert into %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN,  "Course du mardi", "2016-01-01-10:20", "2016-01-01-10:20", null,  1);

    public final static String INSERT_COURSE_3=String.format("insert into %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN,  "Course du samedi", "2016-01-01-10:20", "2016-01-01-10:20", null,  1);

}

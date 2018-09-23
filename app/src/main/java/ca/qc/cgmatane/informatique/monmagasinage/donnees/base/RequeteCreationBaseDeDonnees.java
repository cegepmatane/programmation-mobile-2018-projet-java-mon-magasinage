package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;

public interface RequeteCreationBaseDeDonnees {
    /** Course */
    public final static String DELETE_TABLE_COURSE ="drop table IF EXISTS "+ Course.NOM_TABLE;
    public final static String CREATE_TABLE_COURSE = String.format("create table %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, " +
                    "FOREIGN KEY("+Course.CHAMP_ID_COURSE_ORIGINAL+") REFERENCES "+Course.NOM_TABLE+"("+Course.CHAMP_ID_COURSE+")," +
                    "FOREIGN KEY("+Course.CHAMP_ID_MAGASIN+") REFERENCES "+Magasin.NOM_TABLE+"("+Magasin.CHAMP_ID+")  )",
            Course.NOM_TABLE, Course.CHAMP_ID_COURSE, Course.CHAMP_NOM,Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN);

    /** Magasin */
    public final static String DELETE_TABLE_MAGASIN ="drop table IF EXISTS "+ Magasin.NOM_TABLE;
    public final static String CREATE_TABLE_MAGASIN = String.format("create table %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s REAL, %s REAL)",
            Magasin.NOM_TABLE, Magasin.CHAMP_ID, Magasin.CHAMP_NOM, Magasin.CHAMP_ADRESSE, Magasin.CHAMP_VILLE, Magasin.CHAMP_COOR_X, Magasin.CHAMP_COOR_Y);

}

package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;

public interface RequeteCreationBaseDeDonnees {
    /** Course */
    public final static String DELETE_TABLE_COURSE ="drop table IF EXISTS "+ Course.NOM_TABLE;
    public final static String CREATE_TABLE_COURSE = String.format("create table %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
            Course.NOM_TABLE, Course.CHAMP_ID_COURSE, Course.CHAMP_NOM,Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL);

    /** Magasin */
    public final static String DELETE_TABLE_MAGASIN ="drop table IF EXISTS "+ Magasin.NOM_TABLE;
    public final static String CREATE_TABLE_MAGASIN = String.format("create table %s ( %s INTEGER PRIMARY KEY, %s TEXT)",
            Magasin.NOM_TABLE, Magasin.CHAMP_ID, Magasin.CHAMP_NOM);

}

package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Unite;

public interface RequeteCreationBaseDeDonnees {
    /** Course */
    final static String DELETE_TABLE_COURSE ="drop table IF EXISTS "+ Course.NOM_TABLE;
    final static String CREATE_TABLE_COURSE = String.format("create table %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, " +
                    "FOREIGN KEY("+Course.CHAMP_ID_COURSE_ORIGINAL+") REFERENCES "+Course.NOM_TABLE+"("+Course.CHAMP_ID_COURSE+")," +
                    "FOREIGN KEY("+Course.CHAMP_ID_MAGASIN+") REFERENCES "+Magasin.NOM_TABLE+"("+Magasin.CHAMP_ID+")  )",
            Course.NOM_TABLE, Course.CHAMP_ID_COURSE, Course.CHAMP_NOM,Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN);

    /** Magasin */
    final static String DELETE_TABLE_MAGASIN ="drop table IF EXISTS "+ Magasin.NOM_TABLE;
    final static String CREATE_TABLE_MAGASIN = String.format("create table %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s REAL, %s REAL)",
            Magasin.NOM_TABLE, Magasin.CHAMP_ID, Magasin.CHAMP_NOM, Magasin.CHAMP_ADRESSE, Magasin.CHAMP_VILLE, Magasin.CHAMP_COOR_X, Magasin.CHAMP_COOR_Y);

    /** Unite*/
    final static String DELETE_TABLE_UNITE ="drop table IF EXISTS "+ Unite.NOM_TABLE;
    final static String CREATE_TABLE_UNTITE = String.format("create table %s ( %s INTEGER PRIMARY KEY, %s TEXT )",
            Unite.NOM_TABLE, Unite.CHAMP_ID, Unite.CHAMP_ID);

    /** Produit*/
    final static String DELETE_TABLE_PRODUIT ="drop table IF EXISTS "+ Produit.NOM_TABLE;
    final static String CREATE_TABLE_PRODUIT = String.format("create table %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER, " +
                    "FOREIGN KEY("+Produit.CHAMP_ID_UNITE+") REFERENCES "+Unite.NOM_TABLE+"("+Unite.CHAMP_ID +") )",
            Produit.NOM_TABLE, Produit.CHAMP_ID, Produit.CHAMP_NOM, Produit.CHAMP_QUANTITE_DEFAUT, Produit.CHAMP_RECURENCE_ACHAT, Produit.CHAMP_ID_UNITE);

    /** LigneCourse */
    final static String DELETE_TABLE_LIGNE_COURSE ="drop table IF EXISTS "+ LigneCourse.NOM_TABLE;
    final static String CREATE_TABLE_LIGNE_COURSE = String.format("create table %s (%s INTEGER, %s INTEGER, %s INTEGER, %s BOOLEAN, " +
            "PRIMARY KEY("+LigneCourse.CHAMP_ID_COURSE+", "+LigneCourse.CHAMP_ID_PRODUIT+"),"+
            "FOREIGN KEY("+LigneCourse.CHAMP_ID_COURSE+") REFERENCES "+Course.NOM_TABLE+"("+Course.CHAMP_ID_COURSE +")," +
            "FOREIGN KEY("+LigneCourse.CHAMP_ID_PRODUIT+") REFERENCES "+Produit.NOM_TABLE+"("+Produit.CHAMP_ID +") )",
            LigneCourse.NOM_TABLE, LigneCourse.CHAMP_ID_COURSE, LigneCourse.CHAMP_ID_PRODUIT, LigneCourse.CHAMP_QUANTITE, LigneCourse.CHAMP_COCHE);

}

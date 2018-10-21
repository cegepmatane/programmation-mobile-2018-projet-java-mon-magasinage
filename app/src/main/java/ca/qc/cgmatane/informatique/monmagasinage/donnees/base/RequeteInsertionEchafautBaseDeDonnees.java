package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.LigneCourse;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Unite;

public interface RequeteInsertionEchafautBaseDeDonnees {
    /** Magasin */
    final static String INSERT_MAGASIN_1= String.format("insert into %s (%s, %s, %s, %s, %s, %s) VALUES (%s, '%s', '%s', '%s', %s, %s)",
            Magasin.NOM_TABLE, Magasin.CHAMP_ID, Magasin.CHAMP_NOM, Magasin.CHAMP_ADRESSE, Magasin.CHAMP_VILLE, Magasin.CHAMP_COOR_X, Magasin.CHAMP_COOR_Y,
            1, "IGA ", "rue sans nom", "Matane", 48.851713, -67.511569);
    final static String INSERT_MAGASIN_2= String.format("insert into %s (%s, %s, %s, %s, %s, %s) VALUES (%s, '%s', '%s', '%s', %s, %s)",
            Magasin.NOM_TABLE, Magasin.CHAMP_ID, Magasin.CHAMP_NOM, Magasin.CHAMP_ADRESSE, Magasin.CHAMP_VILLE, Magasin.CHAMP_COOR_X, Magasin.CHAMP_COOR_Y,
            2, "Maxi ", "rue sans nom", "Matane", 48.851829, -67.508942);
    final static String INSERT_MAGASIN_3= String.format("insert into %s (%s, %s, %s, %s, %s, %s) VALUES (%s, '%s', '%s', '%s', %s, %s)",
            Magasin.NOM_TABLE, Magasin.CHAMP_ID, Magasin.CHAMP_NOM, Magasin.CHAMP_ADRESSE, Magasin.CHAMP_VILLE, Magasin.CHAMP_COOR_X, Magasin.CHAMP_COOR_Y,
            3, "Super C", "rue sans nom", "Matane",  48.852420, -67.537355 );
    final static String INSERT_MAGASIN_4= String.format("insert into %s (%s, %s, %s, %s, %s, %s) VALUES (%s, '%s', '%s', '%s', %s, %s)",
            Magasin.NOM_TABLE, Magasin.CHAMP_ID, Magasin.CHAMP_NOM, Magasin.CHAMP_ADRESSE, Magasin.CHAMP_VILLE, Magasin.CHAMP_COOR_X, Magasin.CHAMP_COOR_Y,
            4, "Walmart", "150 Rue Piuze", "Matane", 48.843892, -67.556330);


    /** Course*/
    final static String INSERT_COURSE_1=String.format("insert into %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN, "Une course exeptionnelle", "2016-01-01-10:20", "", null, 1);

    final static String INSERT_COURSE_2=String.format("insert into %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN,  "Course du mardi", "2016-01-01-10:20", "", null,  1);

    final static String INSERT_COURSE_3=String.format("insert into %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN,  "Course du samedi", "2016-01-01-10:20", "", null,  1);

    /** Unite */
    final static String INSERT_UNITE=String.format("insert into %s (%s, %s) VALUES (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s')",
            Unite.NOM_TABLE, Unite.CHAMP_ID, Unite.CHAMP_LIBELLE,
            '1', "Pièce",
            '2', "Kilogramme",
            '3', "Gramme",
            '4', "Livre",
            '5', "Boite",
            '6', "Sachet",
            '7', "Litre",
            '8', "Centilitre");

    /* LigneCourse*/
    final static String INSERT_LIGNE_COURSE_1=String.format("INSERT INTO %s (%s, %s, %s, %s, %s)VALUES (1, 1, 0, 0, 1)",
            LigneCourse.NOM_TABLE, LigneCourse.CHAMP_ID_COURSE, LigneCourse.CHAMP_ID_PRODUIT, LigneCourse.CHAMP_QUANTITE, LigneCourse.CHAMP_COCHE, LigneCourse.CHAMP_ID_UNITE);

    /** Produit*/
    final static String INSERT_FRUIT_ET_LEGUMES_1 ="INSERT INTO produit VALUES (1, 'Ail', 3, 1, 0), "+
    "(2, 'Banane', 4, 1, 0)," +
     "(3, 'Lait ', 1, 7, 0),"+
     "(4, 'Nutella', 1, 1, 0), " +
     "(5, 'Asperge', 1, 1, 0), "+
     "(6, 'oeuf', 1, 1, 0), "+
     "(7, 'Asperge', 1, 1, 0), "+
     "(8, 'Aubergine', 1, 1, 0);" ;



}

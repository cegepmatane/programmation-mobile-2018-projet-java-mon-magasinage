package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Magasin;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
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
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN, "Une course exeptionnelle", "2016-01-01-10:20", "2016-01-01-10:20", null, 1);

    final static String INSERT_COURSE_2=String.format("insert into %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN,  "Course du mardi", "2016-01-01-10:20", "2016-01-01-10:20", null,  1);

    final static String INSERT_COURSE_3=String.format("insert into %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
            Course.NOM_TABLE,  Course.CHAMP_NOM, Course.CHAMP_DATE_NOTIFICATION, Course.CHAMP_DATE_REALISATION, Course.CHAMP_ID_COURSE_ORIGINAL, Course.CHAMP_ID_MAGASIN,  "Course du samedi", "2016-01-01-10:20", "2016-01-01-10:20", null,  1);

    /** Unite */
    final static String INSERT_UNITE=String.format("insert into %s (%s, %s) VALUES (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s'), (%s, '%s')",
            Unite.NOM_TABLE, Unite.CHAMP_ID, Unite.CHAMP_LIBELLE,
            '1', "Kilogramme",
            '2', "Pièce",
            '3', "Gramme",
            '4', "Livre",
            '5', "Boite",
            '6', "Sachet",
            '7', "Litre",
            '8', "Centilitre");

    /** Produit*/
    final static String INSERT_FRUIT_ET_LEGUMES ="BEGIN TRANSACTION;INSERT INTO produit VALUES (1, 'AIL', 1, 0);\n" +
            "INSERT INTO produit VALUES (2, 'ARGOUSE', 1, 0);\n" +
            "INSERT INTO produit VALUES (3, 'ARMOISE COMMUNE', 1, 0);\n" +
            "INSERT INTO produit VALUES (4, 'ARTICHAUT', 1, 0);\n" +
            "INSERT INTO produit VALUES (5, 'ASPERGE', 1, 0);\n" +
            "INSERT INTO produit VALUES (6, 'AUBERGINE', 1, 0);\n" +
            "INSERT INTO produit VALUES (7, 'AULNE TARDIF MÂLE (POIVRE DES DUNES)', 1, 0);\n" +
            "INSERT INTO produit VALUES (8, 'BETTE À CARDE', 1, 0);\n" +
            "INSERT INTO produit VALUES (9, 'BETTERAVE', 1, 0);\n" +
            "INSERT INTO produit VALUES (10, 'BLEUET', 1, 0);\n" +
            "INSERT INTO produit VALUES (11, 'BOK CHOY', 1, 0);\n" +
            "INSERT INTO produit VALUES (12, 'BROCOLI', 1, 0);\n" +
            "INSERT INTO produit VALUES (13, 'CAMERISE', 1, 0);\n" +
            "INSERT INTO produit VALUES (14, 'CANNEBERGE', 1, 0);\n" +
            "INSERT INTO produit VALUES (15, 'CAROTTE', 1, 0);\n" +
            "INSERT INTO produit VALUES (16, 'CASSIS', 1, 0);\n" +
            "INSERT INTO produit VALUES (17, 'CÉLERI', 1, 0);\n" +
            "INSERT INTO produit VALUES (18, 'CÉLERI SAUVAGE - LIVÈCHE', 1, 0);\n" +
            "INSERT INTO produit VALUES (19, 'CÉLERI-RAVE', 1, 0);\n" +
            "INSERT INTO produit VALUES (20, 'CERISE', 1, 0);\n" +
            "INSERT INTO produit VALUES (21, 'CERISE DE TERRE', 1, 0);\n" +
            "INSERT INTO produit VALUES (22, 'CHAMPIGNON', 1, 0);\n" +
            "INSERT INTO produit VALUES (23, 'CHICORÉE', 1, 0);\n" +
            "INSERT INTO produit VALUES (24, 'CHOU', 1, 0);\n" +
            "INSERT INTO produit VALUES (25, 'CHOU-FLEUR', 1, 0);\n" +
            "INSERT INTO produit VALUES (26, 'CHOU-RAVE', 1, 0);\n" +
            "INSERT INTO produit VALUES (27, 'CIBOULE', 1, 0);\n" +
            "INSERT INTO produit VALUES (28, 'CITROUILLE', 1, 0);\n" +
            "INSERT INTO produit VALUES (29, 'COMPTONIE VOYAGEUSE', 1, 0);\n" +
            "INSERT INTO produit VALUES (30, 'CONCOMBRE', 1, 0);\n" +
            "INSERT INTO produit VALUES (31, 'CORNICHON', 1, 0);\n" +
            "INSERT INTO produit VALUES (32, 'COURGE', 1, 0);\n" +
            "INSERT INTO produit VALUES (33, 'COURGETTE', 1, 0);\n" +
            "INSERT INTO produit VALUES (34, 'DAÏKON', 1, 0);\n" +
            "INSERT INTO produit VALUES (35, 'ÉCHALOTE', 1, 0);\n" +
            "INSERT INTO produit VALUES (36, 'EDAMAME', 1, 0);\n" +
            "INSERT INTO produit VALUES (37, 'ENDIVE', 1, 0);\n" +
            "INSERT INTO produit VALUES (38, 'ÉPINARD', 1, 0);\n" +
            "INSERT INTO produit VALUES (39, 'FENOUIL', 1, 0);\n" +
            "INSERT INTO produit VALUES (40, 'FEUILLES DE KALE', 1, 0);\n" +
            "INSERT INTO produit VALUES (41, 'FÈVE', 1, 0);\n" +
            "INSERT INTO produit VALUES (42, 'FINES HERBES', 1, 0);\n" +
            "INSERT INTO produit VALUES (43, 'FLEUR D'AIL BIO', 1, 0);\n" +
            "INSERT INTO produit VALUES (44, 'FLEURS COMESTIBLES', 1, 0);\n" +
            "INSERT INTO produit VALUES (45, 'FRAISE', 1, 0);\n" +
            "INSERT INTO produit VALUES (46, 'FRAMBOISE', 1, 0);\n" +
            "INSERT INTO produit VALUES (47, 'GAULTHÉRIE COUCHÉE', 1, 0);\n" +
            "INSERT INTO produit VALUES (48, 'GOURGANE', 1, 0);\n" +
            "INSERT INTO produit VALUES (49, 'GROSEILLE', 1, 0);\n" +
            "INSERT INTO produit VALUES (50, 'HARICOT', 1, 0);\n" +
            "INSERT INTO produit VALUES (51, 'LAITUE', 1, 0);\n" +
            "INSERT INTO produit VALUES (52, 'MAÏS SUCRÉ', 1, 0);\n" +
            "INSERT INTO produit VALUES (53, 'MÉLILOT BLANC', 1, 0);\n" +
            "INSERT INTO produit VALUES (54, 'MELON', 1, 0);\n" +
            "INSERT INTO produit VALUES (55, 'MICRO POUSSE', 1, 0);\n" +
            "INSERT INTO produit VALUES (56, 'MYRIQUE BAUMIER', 1, 0);\n" +
            "INSERT INTO produit VALUES (57, 'NAVET', 1, 0);\n" +
            "INSERT INTO produit VALUES (58, 'OIGNON', 1, 0);\n" +
            "INSERT INTO produit VALUES (59, 'PANAIS', 1, 0);\n" +
            "INSERT INTO produit VALUES (60, 'PATATE', 1, 0);\n" +
            "INSERT INTO produit VALUES (61, 'PÂTISSON', 1, 0);\n" +
            "INSERT INTO produit VALUES (62, 'PIMBINA', 1, 0);\n" +
            "INSERT INTO produit VALUES (63, 'PIMENT', 1, 0);\n" +
            "INSERT INTO produit VALUES (64, 'POIRE', 1, 0);\n" +
            "INSERT INTO produit VALUES (65, 'POIREAU', 1, 0);\n" +
            "INSERT INTO produit VALUES (66, 'POIS', 1, 0);\n" +
            "INSERT INTO produit VALUES (67, 'POIVRON', 1, 0);\n" +
            "INSERT INTO produit VALUES (68, 'POMME', 1, 0);\n" +
            "INSERT INTO produit VALUES (69, 'POMME DE TERRE', 1, 0);\n" +
            "INSERT INTO produit VALUES (70, 'POUSSE DE LUZERNE', 1, 0);\n" +
            "INSERT INTO produit VALUES (71, 'POUSSE DE LUZERNE RADIS', 1, 0);\n" +
            "INSERT INTO produit VALUES (72, 'POUSSE DE POIS MANGE-TOUT', 1, 0);\n" +
            "INSERT INTO produit VALUES (73, 'POUSSE DE TOURNESOL', 1, 0);\n" +
            "INSERT INTO produit VALUES (74, 'PRUNE', 1, 0);\n" +
            "INSERT INTO produit VALUES (75, 'RABIOLE', 1, 0);\n" +
            "INSERT INTO produit VALUES (76, 'RADICCHIO', 1, 0);\n" +
            "INSERT INTO produit VALUES (77, 'RADIS', 1, 0);\n" +
            "INSERT INTO produit VALUES (78, 'RAISIN', 1, 0);\n" +
            "INSERT INTO produit VALUES (79, 'RAPINI', 1, 0);\n" +
            "INSERT INTO produit VALUES (80, 'RHUBARBE', 1, 0);\n" +
            "INSERT INTO produit VALUES (81, 'ROQUETTE', 1, 0);\n" +
            "INSERT INTO produit VALUES (82, 'RUTABAGA', 1, 0);\n" +
            "INSERT INTO produit VALUES (83, 'SUREAU', 1, 0);\n" +
            "INSERT INTO produit VALUES (84, 'TÊTE DE VIOLON', 1, 0);\n" +
            "INSERT INTO produit VALUES (85, 'THÉ DU LABRADOR', 1, 0);\n" +
            "INSERT INTO produit VALUES (86, 'TOMATE', 1, 0);\n" +
            "INSERT INTO produit VALUES (87, 'TOPINAMBOUR', 1, 0);\n" +
            "COMMIT;";


}

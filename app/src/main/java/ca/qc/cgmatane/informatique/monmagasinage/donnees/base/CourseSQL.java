package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import ca.qc.cgmatane.informatique.monmagasinage.modele.Course;

public interface CourseSQL {
    static String LISTER_COURSE = "select * from course";
    static String RECUPERER_ID = "SELECT last_insert_rowid()";



}

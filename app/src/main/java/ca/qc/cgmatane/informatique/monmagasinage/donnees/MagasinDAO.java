package ca.qc.cgmatane.informatique.monmagasinage.donnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MagasinDAO {
    private static MagasinDAO instance;

    private MagasinDAO() {
    }


    public static MagasinDAO getInstance(){
        if(null == instance){
            instance = new MagasinDAO();
        }
        return instance;
    }
}

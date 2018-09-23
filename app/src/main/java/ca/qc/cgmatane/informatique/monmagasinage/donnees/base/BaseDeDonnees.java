package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnees extends SQLiteOpenHelper implements RequeteCreationBaseDeDonnees, RequeteInsertionBaseDeDonnees {
    private static final int VERSION_BDD = 2;
    private static BaseDeDonnees instance = null;

    public static BaseDeDonnees getInstance(Context contexte)
    {
        if(null == instance) instance = new BaseDeDonnees(contexte);
        return instance;
    }

    public static BaseDeDonnees getInstance()
    {
        return instance;
    }

    private BaseDeDonnees(Context contexte) {
        super(contexte, "MonMagasinage", null, VERSION_BDD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.create(db);
        this.insertion(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        this.suppression(db);
        this.create(db);
        this.insertion(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int arg1, int arg2) {
        this.suppression(db);
        this.create(db);
        this.insertion(db);

    }


    @Override
    public void onOpen(SQLiteDatabase db) {
    }


    private void suppression(SQLiteDatabase db){
        db.execSQL(DELETE_TABLE_COURSE);
        db.execSQL(DELETE_TABLE_MAGASIN);
    }

    private void create(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_MAGASIN);

    }

    private void insertion(SQLiteDatabase db){
        db.execSQL(INSERT_COURSE_1);
        db.execSQL(INSERT_COURSE_2);
        db.execSQL(INSERT_COURSE_3);

    }
}
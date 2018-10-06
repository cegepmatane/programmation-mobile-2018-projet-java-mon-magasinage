package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnees extends SQLiteOpenHelper implements RequeteCreationBaseDeDonnees, RequeteInsertionEchafautBaseDeDonnees, RequeteInsertionProduit {
    private static final int VERSION_BDD = 9;
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
        System.out.println("onCreate()");
        this.create(db);
        this.insertion(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        System.out.println("onUpgrade()");
        this.suppression(db);
        this.create(db);
        this.insertion(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int arg1, int arg2) {
        System.out.println("onDowngrade()");
        this.suppression(db);
        this.create(db);
        this.insertion(db);

    }


    @Override
    public void onOpen(SQLiteDatabase db) {
    }


    private void suppression(SQLiteDatabase db){
        db.execSQL(DELETE_TABLE_LIGNE_COURSE);
        db.execSQL(DELETE_TABLE_PRODUIT);
        db.execSQL(DELETE_TABLE_UNITE);
        db.execSQL(DELETE_TABLE_COURSE);
        db.execSQL(DELETE_TABLE_MAGASIN);
    }

    private void create(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_MAGASIN);
        db.execSQL(CREATE_TABLE_UNTITE);
        db.execSQL(CREATE_TABLE_PRODUIT);
        db.execSQL(CREATE_TABLE_LIGNE_COURSE);

    }

    private void insertion(SQLiteDatabase db){
        db.execSQL(INSERT_UNITE);
        db.execSQL(INSERT_MAGASIN_1);
        db.execSQL(INSERT_MAGASIN_2);
        db.execSQL(INSERT_MAGASIN_3);
        db.execSQL(INSERT_MAGASIN_4);
        db.execSQL(INSERT_COURSE_1);
        db.execSQL(INSERT_COURSE_2);
        db.execSQL(INSERT_COURSE_3);

        db.execSQL(INSERT_FRUIT_ET_LEGUMES_2);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_3);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_4);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_5);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_6);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_7);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_8);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_9);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_10);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_12);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_13);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_14);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_15);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_16);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_21);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_22);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_23);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_24);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_25);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_26);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_27);
        db.execSQL(INSERT_FRUIT_ET_LEGUMES_28);
    }
}
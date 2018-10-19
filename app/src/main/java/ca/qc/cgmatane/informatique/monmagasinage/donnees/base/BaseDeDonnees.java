package ca.qc.cgmatane.informatique.monmagasinage.donnees.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ca.qc.cgmatane.informatique.monmagasinage.modele.Produit;
import ca.qc.cgmatane.informatique.monmagasinage.modele.pluriel.Produits;

public class BaseDeDonnees extends SQLiteOpenHelper implements RequeteCreationBaseDeDonnees, RequeteInsertionEchafautBaseDeDonnees, RequeteInsertionProduit {
    private static final int VERSION_BDD = 24;
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

        insertionProduit(db);
    }

    private void insertionProduit(SQLiteDatabase db){
        db.beginTransaction();
        ContentValues values = new ContentValues(1);
        int idProduit=1;
        for (Produits listeProduits: tousMesProduits){
            for (Produit produit : listeProduits){
                values.put(Produit.CHAMP_ID, idProduit);
                values.put(Produit.CHAMP_NOM, produit.getNom());
                values.put(Produit.CHAMP_QUANTITE_DEFAUT, produit.getQuantiteDefaut());
                values.put(Produit.CHAMP_RECURRENCE_ACHAT, produit.getRecurenceAchat());
                values.put(Produit.CHAMP_UNITE_DEFAUT, 1);
                db.insert(Produit.NOM_TABLE, null, values);
                idProduit++;
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
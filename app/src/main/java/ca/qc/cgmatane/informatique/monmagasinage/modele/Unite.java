package ca.qc.cgmatane.informatique.monmagasinage.modele;

public class Unite {
    public static final String NOM_TABLE = "unite";

    public static final String CHAMP_ID = "id_unite";
    public static final String CHAMP_LIBELLE = "libelle";

    private int id;
    private String libelle;

    public Unite(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}

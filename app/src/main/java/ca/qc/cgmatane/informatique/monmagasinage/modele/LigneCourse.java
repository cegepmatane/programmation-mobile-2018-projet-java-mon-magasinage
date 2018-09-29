package ca.qc.cgmatane.informatique.monmagasinage.modele;

public class LigneCourse {
    public static final String NOM_TABLE = "ligneCourse";

    public static final String CHAMP_ID_COURSE ="id_course";
    public static final String CHAMP_ID_PRODUIT ="id_produit";
    public static final String CHAMP_QUANTITE ="quantite";
    public static final String CHAMP_COCHE ="coche";
    public static final String CHAMP_ID_UNITE = "id_unite";

    private Course course;
    private Produit produit;
    private int quantite;
    private boolean coche;

    public LigneCourse(Course course, Produit produit, int quantite, boolean coche) {
        this.course = course;
        this.produit = produit;
        this.quantite = quantite;
        this.coche = coche;
    }

    public LigneCourse() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public boolean isCoche() {
        return coche;
    }

    public void setCoche(boolean coche) {
        this.coche = coche;
    }
}

package ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration;

public enum EnumerationTheme {
    ;
    private static boolean themeSombre = true;

    public static boolean isThemeSombre() {
        return themeSombre;
    }

    public static void setThemeSombre() {
        EnumerationTheme.themeSombre = !themeSombre;
    }
}

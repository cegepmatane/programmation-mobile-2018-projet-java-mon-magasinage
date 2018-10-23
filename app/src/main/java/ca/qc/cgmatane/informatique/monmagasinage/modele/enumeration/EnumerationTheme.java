package ca.qc.cgmatane.informatique.monmagasinage.modele.enumeration;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static android.content.Context.MODE_PRIVATE;

public enum EnumerationTheme {
    DEFAULT("Thème par defaut", R.style.AppTheme, R.style.AppTheme_NoActionBar,"AppTheme"),
    SOMBRE("Thème sombre", R.style.ThemeSombre, R.style.ThemeSombreNoActionBar, "ThemeSombre"),
    LUMINEUX("Thème lumineux", R.style.ThemeLumineux, R.style.ThemeLumineuxNoActionBar, "ThemeLumineux"),
    VERT("Thème vert", R.style.ThemeVert, R.style.ThemeVertNoActionBar, "ThemeVert");

    private String nom = "";
    private int idLien;
    private int idLienSansActionBar;
    private String idNomTheme;
    private static EnumerationTheme themeSelectionne = DEFAULT;
    private static ArrayList<EnumerationTheme> mesThemes = new ArrayList<EnumerationTheme>();

    EnumerationTheme(String nom, int idLien, int idLienSansActionBar, String idNomTheme) {
        this.nom = nom;
        this.idLien = idLien;
        this.idLienSansActionBar = idLienSansActionBar;
        this.idNomTheme = idNomTheme;
    }

    public static ArrayList<EnumerationTheme> getMesThemes() {
        /** Charge les thémes dans une arrayliste pour éviter de recréer des instances de thémes à chaque .values()*/
        if (mesThemes.size() < 1) {
            for (EnumerationTheme theme : EnumerationTheme.values()) {
                mesThemes.add(theme);
            }
        }
        return mesThemes;
    }

    public String getNom() {
        return nom;
    }

    public int getIdLien() {
        return idLien;
    }

    public int getIdLienSansActionBar() {
        return idLienSansActionBar;
    }

    public void setIdLienSansActionBar(int idLienSansActionBar) {
        this.idLienSansActionBar = idLienSansActionBar;
    }

    public String getIdNomTheme() {
        return idNomTheme;
    }

    public void setIdNomTheme(String idNomTheme) {
        this.idNomTheme = idNomTheme;
    }

    public static EnumerationTheme getThemeSelectionne() {
        return themeSelectionne;
    }


    public static void setThemeSelectionne(EnumerationTheme themeSelectionne, Context context) {
        EnumerationTheme.themeSelectionne = themeSelectionne;

        try {
            InputStream in = new FileInputStream("/sdcard/Android/data/ca.qc.cgmatane.informatique.monmagasinage/enregistrementStyle.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(in);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();


            Node noeuxAModifier = doc.getElementById("1");
            Node child = noeuxAModifier.getChildNodes().item(0);
            child.setTextContent(themeSelectionne.getIdNomTheme());


            FileOutputStream ouputFile = new FileOutputStream("/sdcard/Android/data/ca.qc.cgmatane.informatique.monmagasinage/enregistrementStyle.xml");
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(ouputFile);
            transformer.transform(source, streamResult);

            ouputFile.close();

            System.out.println("nouvelle valeur :");

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    /***
     * Adapter avec une liste de nom de théme
     * @param context Context
     * @return ArrayAdaptater
     */
    public static ArrayAdapter recuperereListeThemesPourSpinner(Context context) {

        List<String> listPourSpinner = new ArrayList<String>();
        for (EnumerationTheme theme : EnumerationTheme.getMesThemes()) {
            listPourSpinner.add(theme.getNom());
        }
        ArrayAdapter<String> adapterThemes = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listPourSpinner);

        return adapterThemes;
    }

    public static EnumerationTheme retournerThemeParPosition(int postition) {
        int i = 0;
        for (EnumerationTheme theme : EnumerationTheme.getMesThemes()) {
            if (postition == i) {
                return theme;
            }
            i++;
        }
        return null;
    }

    /***
     * Changer le théme de l'activity
     * @param context activity
     */
    public static void changerTheme(Context context) {
        System.out.println(" Choix du théme");
        context.setTheme(themeSelectionne.idLien);
    }

    /***
     * Relance l'activité pour afficher le nouveau théme
     * @param appCompatActivity
     */
    public static void reactualiserActiviteAvecTheme(AppCompatActivity appCompatActivity) {
        Intent intent = new Intent(appCompatActivity, appCompatActivity.getClass());
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish();
    }

    public static void appliquerStyle(String nomStyle){
        System.out.println(nomStyle);
        if (!"".equals(nomStyle)) {
            for (EnumerationTheme theme : mesThemes) {
                if (nomStyle.equals(theme.getIdNomTheme())) {
                    themeSelectionne = theme;
                }
            }
        }

    }

    /***
     * Recupere dans le fichier xml enregistrementStyle.xml le nom du theme
     * @param context
     * @throws XmlPullParserException
     */
    public static void recupererThemeSelectionnee(Context context) {

        getMesThemes();

        try {
            InputStream in = new FileInputStream("/sdcard/Android/data/ca.qc.cgmatane.informatique.monmagasinage/enregistrementStyle.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(in);
            //NodeList nodeList = doc.getElementsByTagName("themeSelectionne");
            NodeList nodeList = doc.getChildNodes();
            for (int i=0; i<nodeList.getLength();i++){
                System.out.println("noeux nom : "+nodeList.item(i).getNodeName());
                System.out.println("noeux valeur : "+nodeList.item(i).getTextContent());
                appliquerStyle(nodeList.item(i).getTextContent().replace("\"", "").replace("\n", "").replace(" ", ""));
            }

        } catch (IOException e) {
            try {
                System.out.println("create file");
                File file = new File("/sdcard/Android/data/ca.qc.cgmatane.informatique.monmagasinage/enregistrementStyle.xml");
                File folder = new File("/sdcard/Android/data/ca.qc.cgmatane.informatique.monmagasinage/");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                file.createNewFile();
                FileOutputStream ouputFile = new FileOutputStream("/sdcard/Android/data/ca.qc.cgmatane.informatique.monmagasinage/enregistrementStyle.xml");
                ouputFile.write(new StringBuilder().append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n").append("<themeSelectionne>\n").append("    <string name=\"themeSelectionne\" id=\"1\">AppTheme</string>\n").append("</themeSelectionne>").toString().getBytes());
                ouputFile.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
}
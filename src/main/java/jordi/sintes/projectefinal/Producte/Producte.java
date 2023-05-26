package jordi.sintes.projectefinal.Producte;

import java.io.Serializable;

public class Producte implements Serializable {
    private String nom;
    private double preu;
    private String Descripcio;
    private String DataCaducitat;

    static final String fitxer = "productes.csv";

    public Producte() {

    }

    public Producte(String nom, double preu, String Descripcio, String DataCaducitat) {
        this.nom = nom;
        this.preu = preu;
        this.Descripcio = Descripcio;
        this.DataCaducitat = DataCaducitat;
    }

    public String getNom() {
        return nom;
    }

    public double getPreu() {
        return preu;
    }

    public String getDescripcio() {
        return Descripcio;
    }

    public String getDataCaducitat() {
        return DataCaducitat;
    }

    public String getFitxer() {
        return fitxer;
    }
}
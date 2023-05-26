package jordi.sintes.projectefinal;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import jordi.sintes.projectefinal.Producte.Producte;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private TextField entradaNom;
    @FXML
    private TextField entradaPreu;
    @FXML
    private Button entradaGuarda;
    @FXML
    private Button entradaCerca;
    @FXML
    private TextArea llistaEntrada;
    @FXML
    private Text resultatNom;
    @FXML
    private Text resultatPreu;
    @FXML
    private Text entradaCaducitat02;
    @FXML
    private DatePicker entradaCad;
    @FXML
    private TextField entradaDesc;
    @FXML
    private TextField sortidaNom;
    @FXML
    private TextField sortidaPreu;
    @FXML
    private Button sortidaCerca;
    @FXML
    private TextArea llistaSortida;
    @FXML
    private Text resultatSortidaNom;
    @FXML
    private Text resultatSortidaPreu;
    @FXML
    private Text sortidaCaducitat02;
    @FXML
    private DatePicker sortidaCad;
    @FXML
    private Button dreta;
    @FXML
    private Button totDreta;
    @FXML
    private Button esquerra;
    @FXML
    private Button totEsquerra;
    @FXML
    private Button sortidaTreu;
    @FXML
    private TextField sortidaDesc;

    private String ruta = ".data/productes.bin";

    //Guardar el producte a l'arxiu "productes.bin"
    public void guardarProducte(){
        String nom = entradaNom.getText();
        double preu = Double.parseDouble(entradaPreu.getText());
        String descripcio = entradaDesc.getText();
        LocalDate dataCaducitat = entradaCad.getValue();

        Producte producte = new Producte(nom, preu, descripcio, dataCaducitat.toString());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta, true))) {
            oos.writeObject(producte);
        } catch (IOException e) {
            System.out.println(e);
        }
        resultatNom.setText(producte.getNom());
        String preuStr = Double.toString(producte.getPreu());
        resultatPreu.setText(preuStr);
        String llistaStr = dataCaducitat.toString();
        llistaEntrada.setText(llistaStr);

    }

    //Buscar els productes de l'arxiu binari amb una llista, i mostrar els resultats amb el mètode "mostrarResultats"
    public void buscarProductes() {
        String nomBuscar = sortidaNom.getText();
        String descBuscar = sortidaDesc.getText();
        LocalDate dataBuscar = sortidaCad.getValue();

        List<Producte> productesTrobats = new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))){
            while (true) {
                try{
                Producte producte = (Producte) ois.readObject();
                if ((nomBuscar.isEmpty() || producte.getNom().equalsIgnoreCase(nomBuscar)) &&
                        (descBuscar.isEmpty() || producte.getDescripcio().equalsIgnoreCase(descBuscar)) &&
                        (dataBuscar == null || producte.getDataCaducitat().equalsIgnoreCase(dataBuscar.toString()))) {
                    productesTrobats.add(producte);
                }
                }catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
        mostrarResultats(productesTrobats);
    }

    // Mètode per concatenar els camps dels productes trobats a la nevera i mostrar-los per pantalla
    public void mostrarResultats(List<Producte> productesTrobats) {
        //Fora d'ús, ara s'ensenyen els resultats fent ús de els camps "resultatSortida"
        /*StringBuilder sb = new StringBuilder();
        for (Producte producte : productesTrobats) {
            sb.append("Nom: ").append(producte.getNom()).append("\n");
            sb.append("Preu: ").append(producte.getPreu()).append("\n");
            sb.append("Descripcio: ").append(producte.getDescripcio()).append("\n");
            sb.append("Data Caducitat: ").append(producte.getDataCaducitat()).append("\n");
            sb.append("---------------------\n");
        }
        llistaSortida.setText(sb.toString());*/

        for (Producte producte : productesTrobats) {
            resultatSortidaNom.setText(producte.getNom());
            String preuStr = Double.toString(producte.getPreu());
            resultatSortidaPreu.setText(preuStr);
            llistaSortida.setText(producte.getDataCaducitat());
        }
    }


    // Botons de navegació (NO FUNCIONA "invalid type code: AC")
    private List<Producte> productesTrobats;
    private int posActual;

    // Mètode que estarà en tots els mètodes dels botons per mostrar el producte actual per pantalla
    public void mostrarProductePosActual() {
        Producte producte = productesTrobats.get(posActual);
        StringBuilder sb = new StringBuilder();
            sb.append("Nom: ").append(producte.getNom()).append("\n");
            sb.append("Preu: ").append(producte.getPreu()).append("\n");
            sb.append("Descripcio: ").append(producte.getDescripcio()).append("\n");
            sb.append("Data Caducitat: ").append(producte.getDataCaducitat()).append("\n");
            sb.append("---------------------\n");
            llistaSortida.setText(sb.toString());
    }
    public void mostrarPrimerResultat() {
        if (productesTrobats != null && !productesTrobats.isEmpty()) {
            posActual = 0;
            //Sempre mostrar el producte actual en tots els mètodes
            mostrarProductePosActual();
        }
    }
    public void mostrarUltimResultat() {
        if (productesTrobats != null && !productesTrobats.isEmpty()) {
            posActual = -1;
            //Sempre mostrar el producte actual en tots els mètodes
            mostrarProductePosActual();
        }
    }
    public void mostrarSeguentResultat() {
        if (productesTrobats != null && !productesTrobats.isEmpty()) {
            posActual++;
            //Sempre mostrar el producte actual en tots els mètodes
            mostrarProductePosActual();
        }
    }
    public void mostrarAnteriorResultat() {
        if (productesTrobats != null && !productesTrobats.isEmpty()) {
            posActual--;
            //Sempre mostrar el producte actual en tots els mètodes
            mostrarProductePosActual();
        }
    }

    public void treureProducte() {
        if(productesTrobats != null && !productesTrobats.isEmpty()) {
            //Seleccionar producte
            Producte producte = productesTrobats.get((posActual));
            //Eliminar producte
            productesTrobats.remove(posActual);
            //guardarProducte();
            //Guardar canvis
            guardarProductesActuals();
            //Si encara hi han productes disponibles, mostrar-los
            if(!productesTrobats.isEmpty()) {
                if (posActual >= productesTrobats.size()) {
                    posActual = productesTrobats.size() -1;
                }
                mostrarProductePosActual();
                //Si no, la llista en blanc
            }else {
                llistaSortida.setText("");
            }
        }
    }

    public void guardarProductesActuals() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            for(Producte producte : productesTrobats) {
                oos.writeObject(producte);
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }



}
package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.model.Artista;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	
    	List<Adiacenza> adiacenze = this.model.getAdiacenze();
    	
    	for(Adiacenza a : adiacenze) {
    		txtResult.appendText(a.toString() + "\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
    		Integer artistaId = Integer.parseInt(txtArtista.getText());

    		if(!this.model.graphConteinsArtist(artistaId)) {
    			txtResult.appendText("Inserire un Artista ID valido. \n");
    			return;
    		}
    		
    		List<Artista> result = this.model.trovaPercorso(artistaId);
    		
    		for(Artista a : result) {
    			txtResult.appendText(a.toString() + "\n");
    		}
    		
    		
    		
    		
    		
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    		return;
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String role = boxRuolo.getValue();
    	if(role == null) {
    		txtResult.appendText("Inserire un ruolo.");
    		return;
    	}
    	
    	txtResult.appendText("Creo il grafo...\n");
    	this.model.creaGrafo(role);
    	txtResult.appendText(String.format("Grafo creato! #Vertici %d #Archi %d", 
    								this.model.getNumberVertex(), 
    								this.model.getNumberEdges()));
    }

    public void setModel(Model model) {
    	this.model = model;
    	boxRuolo.getItems().addAll(this.model.getAllRole());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}

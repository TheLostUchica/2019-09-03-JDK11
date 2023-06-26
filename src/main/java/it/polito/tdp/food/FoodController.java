/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	String S = this.boxPorzioni.getValue();
    	if(S!=null) {
    		String ii = this.txtPassi.getText();
    		try {
    			int i = Integer.parseInt(ii);
    			
	    		for(String s : model.getBestPath(S, i)) {
	    			this.txtResult.appendText(s+"\n");
	    		}
    		}catch(NumberFormatException e ) {
    			e.printStackTrace();
        		txtResult.appendText("Parametro inserito nel formato sbagliato.\n");
    		}
    	}else {
    	txtResult.appendText("Inserire tipo di porzione.");}
    }
    	

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	String S = this.boxPorzioni.getValue();
    	if(S!=null) {
    		TreeMap<String, Integer> map = model.getConnessa(S);
    		for(String s : map.keySet()) {
    			this.txtResult.appendText(s+" : "+map.get(s)+"\n");
    		}
    	}else {
    	txtResult.appendText("Inserire tipo di porzione.");}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String s = this.txtCalorie.getText();
    	try {
    		int i = Integer.parseInt(s);
    		if(i>0) {
    			model.creaGrafo(i);
    			txtResult.appendText("Grafo creato con "+model.getGrafo().vertexSet().size()+" e "+model.getGrafo().edgeSet().size()+" archi.\n");
    			this.setCombo();
    		}else {
    			txtResult.appendText("Inserire un numero maggiore di 0.\n");
    		}
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.appendText("Parametro inserito nel formato sbagliato.\n");
    	}    	
    }

    private void setCombo() {
		this.boxPorzioni.getItems().addAll(model.setCombo());		
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

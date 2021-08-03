package highwaymonitor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HighwayController implements Initializable {

   
    @FXML
    private ListView<Terminal> TerminalList;
    @FXML
    private ListView<String> VehicalFlowList;
    @FXML
    private TextField VehicalId;
    @FXML
    private TextField VehicleJourney;
    @FXML
    private TextField Processing;
    @FXML
    private TextField VehicalTotal;
    @FXML
    private TextField TotalToll;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Highway highway = Highway.getInstance();
            highway.startVehicalSimulation();
            
//          initializes gui with terminal list
            ObservableList<Terminal> terminalListItems = FXCollections.observableArrayList(Highway.getTerminals());
            TerminalList.setItems(terminalListItems);

//          customizes Terminal object to show its name and processing vehicle in listview
            TerminalList.setCellFactory((ListView<Terminal> param) -> {
                ListCell<Terminal> cell = new ListCell<Terminal>() {
                    
                    @Override
                    protected void updateItem(Terminal item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            String processedVehical = "idle";
                            if (item.currentlyProcessedVehical() != null) {
                                processedVehical = item.currentlyProcessedVehical().getIdNumber();
                                
                            }
                            setText(item.getName() + "   " + processedVehical);
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            });

//          on change of selection in gui in terminal list this code fires and fills the text boxes
            TerminalList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Terminal> observable, Terminal oldValue, Terminal newValue) -> {
                Processing.setText("idle");
                VehicalTotal.setText("0");
                VehicalTotal.setText(String.valueOf(newValue.getTotalVehicleProcessed()));
                if (newValue.currentlyProcessedVehical() != null) {
                    Processing.setText(newValue.currentlyProcessedVehical().getIdNumber());
                }
                TotalToll.setText(String.valueOf(newValue.getTotalIncome()));
                ObservableList<Terminal> terminalListItems1 = FXCollections.observableArrayList(Highway.getTerminals());
                TerminalList.setItems(terminalListItems1);
            });
            
            
            
//          initializes vehicle flow list
            ObservableList<String> VehicalFlow = Highway.getVehicalFlowListItems();
            VehicalFlowList.setItems(VehicalFlow);
         
//          on change of selection in vehicle flow list it fills up the text boxes vhicle id and journey            
            VehicalFlowList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                Vehical v = Highway.getVehicals().get(VehicalFlowList.getSelectionModel().getSelectedIndex());
                VehicalId.setText(v.getIdNumber());
                VehicleJourney.setText(String.valueOf(v.getJourney()));
            });
//       This exception is thrown when vehicle tries to enter a unknown enterance  
        } catch (InvalidEnteranceException ex) {
            
            Logger.getLogger(HighwayController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // This method exits the program gui window
    @FXML
    private void exit(MouseEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        System.out.print("exited");
    }

}

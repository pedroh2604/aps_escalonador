package UI;

import UI.Gantt.GanttChart;
import UI.Gantt.GanttChart.ExtraData;
import UI.Gantt.GanttController;
import UI.Helpers.UIHelpers;
import aps.ALGORITHM;
import aps.PCB;
import aps.Scheduler;
import data_structures.List;
import data_structures.Queue;
import helpers.FileHelpers;
import helpers.RandomGenerator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;


public class MainController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_load;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_run;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_rand;

    @FXML
    private Spinner<Integer> spn_rand;

    @FXML
    private RadioButton radio_round_robin;

    @FXML
    private ToggleGroup algorithm;

    @FXML
    private RadioButton radio_priority;

    @FXML
    private Spinner<Integer> spn_quantum;

    @FXML
    private TableView<PCB> table_pcbs;
    
    @FXML
    private TableColumn<PCB, String> col_PID;

    @FXML
    private TableColumn<PCB, Integer> col_arrival;

    @FXML
    private TableColumn<PCB, Integer> col_duration;

    @FXML
    private TableColumn<PCB, Integer> col_priority;

    @FXML
    private TableColumn<PCB, String> col_iorequests;

    @FXML
    void handleQuantum(ActionEvent event) {
        this.spn_quantum.setDisable(this.radio_priority.isSelected());
        this.spn_quantum.getValueFactory().setValue(1);
    }
    
    @FXML
    void handleLoad(ActionEvent event) {
        var chooser = new FileChooser();
        chooser.getExtensionFilters().add(new ExtensionFilter("JSON files", "*.json"));
        chooser.setTitle("Selecionar Arquivo");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                UIHelpers.setTableData(FileHelpers.load(file.getPath()), table_pcbs);
            } catch (IOException e) {
                UIHelpers.showErrorMessage(e.getMessage());
            }        
        }
    }

    @FXML
    void handleSave(ActionEvent event) {
        var chooser = new FileChooser();
        chooser.getExtensionFilters().add(new ExtensionFilter("JSON files", "*.json"));
        chooser.setTitle("Salvar");
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                FileHelpers.save(UIHelpers.getTableData(table_pcbs), file.getPath());
            } catch (IOException e) {
                UIHelpers.showErrorMessage(e.getMessage());
            }        
        }
    }
    
    @FXML
    void handleAdd(ActionEvent event) {
        // gets previous list table items
        List<PCB> list = UIHelpers.getTableData(table_pcbs);
        list.add(RandomGenerator.generatePCB(1, 10));
        UIHelpers.setTableData(list, table_pcbs);
    }
    
    @FXML
    void handleEdit(ActionEvent event) {
        System.out.println("Editing");
        System.out.println("event: " + event);
    }

    @FXML
    void handleContextMenu(ContextMenuEvent event) {
        if (table_pcbs.getItems().size() > 0) {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem remove = new MenuItem("Remover Processo");
            remove.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removePCB();
                }
            });

            contextMenu.getItems().addAll(remove);
            contextMenu.show((Node) event.getTarget(), event.getScreenX(), event.getScreenY());
        }
    }
    
    private void removePCB () {
        int index = table_pcbs.getSelectionModel().selectedIndexProperty().get();
        // TODO - change PIDs... they're not being updated as a PCB is removed
        List<PCB> list = UIHelpers.getTableData(table_pcbs);
        list.removeAt(index);
        UIHelpers.setTableData(list, table_pcbs);
    }

    @FXML
    void handleRandom(ActionEvent event) {
        int size = this.spn_rand.valueProperty().getValue();
        
        // gets previous list table items
        List<PCB> list = UIHelpers.getTableData(table_pcbs);
        
        list.addAll(RandomGenerator.generatePCBList(size, 1, 10));
        UIHelpers.setTableData(list, table_pcbs);
    }

    @FXML
    void handleRun(ActionEvent event) throws IOException {
        ALGORITHM algorithm = this.radio_round_robin.isSelected() ? ALGORITHM.ROUND_ROBIN : ALGORITHM.PRIORITY_PREEMPTIVE;
        int quantum = this.spn_quantum.valueProperty().getValue();
        List<PCB> list = UIHelpers.getTableData(table_pcbs);
        if (list.getSize() > 0) {
            var scheduler = new Scheduler(algorithm);
            scheduler.addProcesses(UIHelpers.getTableData(table_pcbs));
            scheduler.setQuantum(this.spn_quantum.valueProperty().getValue());
            scheduler.execute();            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Gantt/Gantt.fxml"));
            Parent gantt = loader.load();
            GanttController ganttController = loader.getController();
            ganttController.setData(scheduler);
            Scene scene = new Scene(gantt);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Gantt");
            stage.show();                
        }
    }

    @FXML
    public void initialize() {
        assert btn_load != null : "fx:id=\"btn_load\" was not injected: check your FXML file 'Main.fxml'.";
        assert btn_save != null : "fx:id=\"btn_save\" was not injected: check your FXML file 'Main.fxml'.";
        assert btn_run != null : "fx:id=\"btn_run\" was not injected: check your FXML file 'Main.fxml'.";
        assert btn_add != null : "fx:id=\"btn_add\" was not injected: check your FXML file 'Main.fxml'.";
        assert btn_rand != null : "fx:id=\"btn_rand\" was not injected: check your FXML file 'Main.fxml'.";
        assert spn_rand != null : "fx:id=\"spn_rand\" was not injected: check your FXML file 'Main.fxml'.";
        assert radio_round_robin != null : "fx:id=\"radio_round_robin\" was not injected: check your FXML file 'Main.fxml'.";
        assert algorithm != null : "fx:id=\"algorithm\" was not injected: check your FXML file 'Main.fxml'.";
        assert radio_priority != null : "fx:id=\"radio_priority\" was not injected: check your FXML file 'Main.fxml'.";
        assert spn_quantum != null : "fx:id=\"spn_quantum\" was not injected: check your FXML file 'Main.fxml'.";
        assert table_pcbs != null: "fx:id=\"table_pcbs\" was not injected: check your FXML file 'Main.fxml'.";
        assert col_PID != null : "fx:id=\"col_PID\" was not injected: check your FXML file 'Main.fxml'.";
        assert col_arrival != null : "fx:id=\"col_arrival\" was not injected: check your FXML file 'Main.fxml'.";
        assert col_duration != null : "fx:id=\"col_duration\" was not injected: check your FXML file 'Main.fxml'.";
        assert col_priority != null : "fx:id=\"col_priority\" was not injected: check your FXML file 'Main.fxml'.";
        assert col_iorequests != null : "fx:id=\"col_iorequests\" was not injected: check your FXML file 'Main.fxml'.";
        col_PID.setCellValueFactory(new PropertyValueFactory<>("PID"));
        col_arrival.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        col_priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        col_iorequests.setCellValueFactory(new PropertyValueFactory<>("ioRequestsString"));
        col_PID.setStyle( "-fx-alignment: CENTER;");        
        col_arrival.setStyle( "-fx-alignment: CENTER;");        
        col_duration.setStyle( "-fx-alignment: CENTER;");        
        col_priority.setStyle( "-fx-alignment: CENTER;");        
        col_iorequests.setStyle( "-fx-alignment: CENTER;");
        SpinnerValueFactory<Integer> quantumValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        this.spn_quantum.setValueFactory(quantumValueFactory);
        SpinnerValueFactory<Integer> randomValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        this.spn_rand.setValueFactory(randomValueFactory);
    }
    
}

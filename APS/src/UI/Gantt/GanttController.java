/**
 * Sample Skeleton for 'Gantt.fxml' Controller Class
 */

package UI.Gantt;

import UI.Helpers.UIHelpers;
import aps.ALGORITHM;
import aps.PCB;
import aps.Scheduler;
import data_structures.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GanttController {
    static int cssClassIndex = 0;
    String cssClasses[] = {"status-red", "status-green", "status-blue"};
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tab_log"
    private Tab tab_log; // Value injected by FXMLLoader

    @FXML // fx:id="pane_log"
    private AnchorPane pane_log; // Value injected by FXMLLoader

    @FXML // fx:id="txt_log"
    private TextArea txt_log; // Value injected by FXMLLoader

    @FXML // fx:id="tab_gantt"
    private Tab tab_gantt; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_algorithm"
    private Label lbl_algorithm; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_turnaround"
    private Label lbl_turnaround; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_waiting"
    private Label lbl_waiting; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_quantum"
    private Label lbl_quantum; // Value injected by FXMLLoader

    @FXML // fx:id="pane_grid"
    private ScrollPane pane_grid; // Value injected by FXMLLoader

    @FXML // fx:id="pane_gantt"
    private AnchorPane pane_gantt; // Value injected by FXMLLoader

    @FXML // fx:id="split_pane_main"
    private SplitPane split_pane_main; // Value injected by FXMLLoader

    @FXML // fx:id="anchor_pane_side"
    private AnchorPane anchor_pane_side; // Value injected by FXMLLoader

    @FXML // fx:id="pane_info"
    private AnchorPane pane_info; // Value injected by FXMLLoader

    @FXML // fx:id="split_pane_chart"
    private SplitPane split_pane_chart; // Value injected by FXMLLoader

    @FXML
    private TableView<PCB> table;
    
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
    private TableColumn<PCB, Integer> col_turnaround;

    @FXML
    private TableColumn<PCB, Integer> col_waiting;

    public void setData(Scheduler scheduler) {
        this.setLog(scheduler);
        this.setInfo(scheduler);
        List<PCB> pcbs = scheduler.getTimeLines();
        UIHelpers.setTableData(pcbs, this.table);
        this.renderGrid(pcbs, scheduler.getAlgorithm());
        this.renderChart(pcbs, scheduler.getAlgorithm());
    }
    
    private void setLog(Scheduler scheduler) {
        var builder = new StringBuilder();
        builder.append(scheduler.getAlgorithm() == ALGORITHM.ROUND_ROBIN ? "ROUND ROBIN" : "PRIORIDADE PREEMPTIVO");
        builder.append("\n");
        if (scheduler.getAlgorithm() == ALGORITHM.ROUND_ROBIN) {
            builder.append("Quantum: ").append(scheduler.getQuantum()).append("\n");
        }
        builder.append("Avg. Turnaround: ").append(scheduler.avgTurnaround()).append("\n");
        builder.append("Avg. Waiting: ").append(scheduler.avgWaiting()).append("\n");
        builder.append("\n");
        builder.append("Log: ").append("\n\n").append(scheduler.getLogAsString()).append("\n\n");
        builder.append("Timelines:").append("\n\n").append(scheduler.getTimeLinesAsString());
        this.txt_log.setText(builder.toString());
    }
    
    private void setInfo(Scheduler scheduler) {
        this.lbl_algorithm.setText(scheduler.getAlgorithm() == ALGORITHM.ROUND_ROBIN ? "Round Robin" : "Prioridade Preemptivo");
        this.lbl_turnaround.setText("AvgTurnaround: " + scheduler.avgTurnaround());
        this.lbl_waiting.setText("avgWaiting: " + scheduler.avgWaiting());
        this.lbl_quantum.setText("Quantum: " + scheduler.getQuantum());
        this.lbl_quantum.setVisible(scheduler.getAlgorithm() == ALGORITHM.ROUND_ROBIN);
    }
    
    private void renderGrid(List<PCB> data, ALGORITHM algorithm) {
        
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setGridLinesVisible(false);

        for (int i = 0; i < data.getSize(); i++) {
            
            PCB currentPCB = data.get(i);
            int timeline[] = currentPCB.getTimeLine();
            
            for (int j = 0; j < timeline.length; j++) {
                
                String title = currentPCB.getPID();
                if (algorithm == ALGORITHM.PRIORITY_PREEMPTIVE) {
                    title += " (" + currentPCB.getPriority() + ")";
                }
                
                Text pid = new Text(title);
                pid.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                pid.setTextAlignment(TextAlignment.CENTER);
                StackPane pidPane = new StackPane();
                pidPane.getStyleClass().add(cssClasses[cssClassIndex]);
                pidPane.getStyleClass().add("timeline-pid");
                pidPane.getChildren().addAll(pid);
                StackPane.setAlignment(pid, Pos.CENTER);
                StackPane.setMargin(pid, new Insets(2, 10, 2, 10));
                GridPane.setFillHeight(pidPane, true);
                GridPane.setFillWidth(pidPane, true);
                grid.add(pidPane, timeline[j], 0);
                
                StackPane burstPane = new StackPane();
                Label burst = new Label(Integer.toString(timeline[j]));
                burst.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
                burst.setTextAlignment(TextAlignment.LEFT);
                burstPane.getChildren().addAll(burst);
                burstPane.getStyleClass().add("timeline-burst");
                StackPane.setAlignment(burst, Pos.CENTER_LEFT);
                StackPane.setMargin(burst, new Insets(2));
                grid.add(burstPane, timeline[j], 1);
                GridPane.setFillHeight(burstPane, true);
                GridPane.setFillWidth(burstPane, true);
            }
            cssClassIndex = (cssClassIndex + 1) % cssClasses.length;
        }
        
        this.pane_grid.getStylesheets().add(getClass().getResource("timeline.css").toExternalForm());
        this.pane_grid.setContent(grid);
    }

    private void renderChart(List<PCB> data, ALGORITHM algorithm) {
        
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final var chart = new GanttChart<Number,String>(xAxis,yAxis);
        
        for (int i = 0; i < data.getSize(); i++) {
            
            PCB currentPCB = data.get(i);
            XYChart.Series series = new XYChart.Series();
            int timeline[] = currentPCB.getTimeLine();
            
            for (int j = 0; j < timeline.length; j++) {
                
                String title = currentPCB.getPID();
                
                if (algorithm == ALGORITHM.PRIORITY_PREEMPTIVE) {
                    title += "\n(" + currentPCB.getPriority() + ")";
                }
                series.getData().add(new XYChart.Data(timeline[j], title ,new GanttChart.ExtraData(1, cssClasses[cssClassIndex])));
            }
            
            cssClassIndex = (cssClassIndex + 1) % cssClasses.length;
            chart.getData().add(series);
        }
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        chart.setLegendVisible(false);
        this.pane_gantt.getChildren().addAll(chart);
        AnchorPane.setBottomAnchor(chart, 0.0);
        AnchorPane.setLeftAnchor(chart, 0.0);
        AnchorPane.setRightAnchor(chart, 0.0);
        AnchorPane.setTopAnchor(chart, 0.0);
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tab_log != null : "fx:id=\"tab_log\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert pane_log != null : "fx:id=\"pane_log\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert txt_log != null : "fx:id=\"txt_log\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert tab_gantt != null : "fx:id=\"tab_gantt\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert lbl_algorithm != null : "fx:id=\"lbl_algorithm\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert lbl_turnaround != null : "fx:id=\"lbl_turnaround\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert lbl_waiting != null : "fx:id=\"lbl_waiting\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert lbl_quantum != null : "fx:id=\"lbl_quantum\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert pane_grid != null : "fx:id=\"pane_grid\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert pane_gantt != null : "fx:id=\"pane_gantt\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert split_pane_main != null : "fx:id=\"split_pane_main\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert anchor_pane_side != null : "fx:id=\"anchor_pane_side\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert pane_info != null : "fx:id=\"pane_info\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert split_pane_chart != null : "fx:id=\"split_pane_chart\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert col_PID != null : "fx:id=\"col_PID\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert col_turnaround != null : "fx:id=\"col_turnaround\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert col_waiting != null : "fx:id=\"col_waiting\" was not injected: check your FXML file 'Gantt.fxml'.";
        SplitPane.setResizableWithParent(this.anchor_pane_side, Boolean.FALSE);
        SplitPane.setResizableWithParent(this.pane_info, Boolean.FALSE);
        SplitPane.setResizableWithParent(this.pane_grid, Boolean.FALSE);
        this.col_PID.setCellValueFactory(new PropertyValueFactory<>("PID"));
        this.col_arrival.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        this.col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        this.col_priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        this.col_iorequests.setCellValueFactory(new PropertyValueFactory<>("ioRequestsString"));
        this.col_turnaround.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        this.col_waiting.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        this.col_PID.setStyle( "-fx-alignment: CENTER;");        
        this.col_arrival.setStyle( "-fx-alignment: CENTER;");        
        this.col_duration.setStyle( "-fx-alignment: CENTER;");        
        this.col_priority.setStyle( "-fx-alignment: CENTER;");        
        this.col_iorequests.setStyle( "-fx-alignment: CENTER;");
        this.col_turnaround.setStyle( "-fx-alignment: CENTER;");        
        this.col_waiting.setStyle( "-fx-alignment: CENTER;");                
    }
}
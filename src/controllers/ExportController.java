package controllers;

import businesslogic.KeyValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class ExportController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private Label summe;
    @FXML
    private Label percent;

    private double sum = 0;
    private ObservableList<KeyValue> test = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectKeyValue((KeyValue) newValue)));

        TableColumn workerName = new TableColumn("Workername");
        TableColumn avg = new TableColumn("Average");

        workerName.setStyle("-fx-alignment: CENTER;");
        avg.setStyle("-fx-alignment: CENTER;");

        workerName.setCellValueFactory(new PropertyValueFactory<KeyValue, String>("key"));
        avg.setCellValueFactory(new PropertyValueFactory<KeyValue, Double>("value"));

        tableView.setItems(test);
        tableView.getColumns().add(workerName);
        tableView.getColumns().add(avg);
    }

    public void initData(HashMap<String, Double> avgs) {
        for (Double d : avgs.values()) {
            sum += d;
        }
        summe.setText(String.valueOf(sum));
        convertAndAdd(avgs);
        test.forEach(System.out::println);
    }

    private void convertAndAdd(HashMap<String, Double> map) {
        Set<Map.Entry<String, Double>> entries = map.entrySet();
        for (Map.Entry<String, Double> entry : entries) {
            String key = entry.getKey();
            Double value = entry.getValue();
            KeyValue keyValue = new KeyValue(key, value);
            test.add(keyValue);
        }
    }

    private void selectKeyValue(KeyValue keyValue) {
        percent.setText(keyValue.getKey());
    }
}
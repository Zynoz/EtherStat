package controllers;

import businesslogic.JsonWorker;
import businesslogic.Util;
import businesslogic.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class StartController implements Initializable{

    private ObservableList<JsonWorker> jsonWorkers = FXCollections.observableArrayList();
    private ObservableList<Worker> workers = FXCollections.observableArrayList();

    @FXML
    private TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jsonWorkers.addAll(Util.getWorkers());
        TableColumn workerName = new TableColumn("Name");
        TableColumn avg = new TableColumn("Average");
        TableColumn current = new TableColumn("Current");
        TableColumn valid = new TableColumn("Valid");
        TableColumn stale = new TableColumn("Stale");
        TableColumn lastSeen = new TableColumn("Last Seen");
        TableColumn time = new TableColumn("Time");

        convertWorkers();
        workerName.setCellValueFactory(new PropertyValueFactory<Worker, String>("worker"));
        avg.setCellValueFactory(new PropertyValueFactory<Worker, String>("averageHashrate"));
        current.setCellValueFactory(new PropertyValueFactory<Worker, String>("currentHashrate"));
        valid.setCellValueFactory(new PropertyValueFactory<Worker, String>("validShares"));
        stale.setCellValueFactory(new PropertyValueFactory<Worker, String>("staleShares"));
        lastSeen.setCellValueFactory(new PropertyValueFactory<Worker, String>("lastSeen"));
        time.setCellValueFactory(new PropertyValueFactory<Worker, String>("time"));

        table.setItems(workers);
        table.getColumns().add(workerName);
        table.getColumns().add(avg);
        table.getColumns().add(current);
        table.getColumns().add(valid);
        table.getColumns().add(stale);
        table.getColumns().add(lastSeen);
        table.getColumns().add(time);
    }

    @FXML
    private void reload() {
        jsonWorkers.clear();
        workers.clear();
        jsonWorkers.addAll(Util.getWorkers());
        convertWorkers();
        for (Worker worker : workers) {
            System.out.println(worker.toString());
        }
    }

    private void convertWorkers() {
        for (JsonWorker json : jsonWorkers) {
            if (json.getAverageHashrate().doubleValue() > 1000) {
                if (json.getAverageHashrate() != null) {
                    json.setAverageHashrate(json.getAverageHashrate().divide(BigDecimal.valueOf(1000000)));
                }
                if (json.getCurrentHashrate() != null) {
                    json.setCurrentHashrate(json.getCurrentHashrate().divide(BigDecimal.valueOf(1000000)));
                }
                if (json.getReportedHashrate() != null) {
                    json.setReportedHashrate(json.getReportedHashrate().divide(BigDecimal.valueOf(1000000)));
                }
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:");
            LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(json.getTime()), ZoneId.systemDefault());
            LocalDateTime lastSeen = LocalDateTime.ofInstant(Instant.ofEpochSecond(json.getLastSeen()), ZoneId.systemDefault());
            if (json.getCurrentHashrate() != null) {
                workers.add(new Worker(json.getWorker(), time, lastSeen, json.getReportedHashrate(), json.getCurrentHashrate().setScale(2, RoundingMode.DOWN), json.getValidShares(), json.getInvalidShares(), json.getStaleShares(), json.getAverageHashrate().setScale(2, RoundingMode.DOWN)));
            } else {
                workers.add(new Worker(json.getWorker(), time, lastSeen, json.getReportedHashrate(), json.getCurrentHashrate(), json.getValidShares(), json.getInvalidShares(), json.getStaleShares(), json.getAverageHashrate().setScale(2, RoundingMode.DOWN)));
            }

        }
    }
}
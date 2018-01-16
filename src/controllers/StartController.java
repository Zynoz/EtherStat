package controllers;

import businesslogic.Jdbc;
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
    private ObservableList<Worker> testWorkers = FXCollections.observableArrayList();
    private Jdbc jdbc;
    private boolean db = true;

    @FXML
    private TableView table;

    @FXML
    private TableView testTable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        jsonWorkers.addAll(Util.getWorkers());
        TableColumn workerName = new TableColumn("Name");
        TableColumn avg = new TableColumn("Average");
        TableColumn current = new TableColumn("Current");
        TableColumn valid = new TableColumn("Valid");
        TableColumn stale = new TableColumn("Stale");
        TableColumn lastSeen = new TableColumn("Last Seen");
        TableColumn time = new TableColumn("Time");

        TableColumn workerNameDB = new TableColumn("Name");
        TableColumn avgDB = new TableColumn("Average");
        TableColumn currentDB = new TableColumn("Current");
        TableColumn validDB = new TableColumn("Valid");
        TableColumn staleDB = new TableColumn("Stale");
        TableColumn lastSeenDB = new TableColumn("Last Seen");
        TableColumn timeDB = new TableColumn("Time");

        convertWorkers();
        workerName.setCellValueFactory(new PropertyValueFactory<Worker, String>("worker"));
        avg.setCellValueFactory(new PropertyValueFactory<Worker, String>("averageHashrate"));
        current.setCellValueFactory(new PropertyValueFactory<Worker, String>("currentHashrate"));
        valid.setCellValueFactory(new PropertyValueFactory<Worker, String>("validShares"));
        stale.setCellValueFactory(new PropertyValueFactory<Worker, String>("staleShares"));
        lastSeen.setCellValueFactory(new PropertyValueFactory<Worker, String>("lastSeen"));
        time.setCellValueFactory(new PropertyValueFactory<Worker, String>("time"));

        workerNameDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("worker"));
        avgDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("averageHashrate"));
        currentDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("currentHashrate"));
        validDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("validShares"));
        staleDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("staleShares"));
        lastSeenDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("lastSeen"));
        timeDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("time"));

        table.setItems(workers);
        table.getColumns().add(workerName);
        table.getColumns().add(avg);
        table.getColumns().add(current);
        table.getColumns().add(valid);
        table.getColumns().add(stale);
        table.getColumns().add(lastSeen);
        table.getColumns().add(time);

        testTable.setItems(testWorkers);
        testTable.getColumns().add(workerNameDB);
        testTable.getColumns().add(avgDB);
        testTable.getColumns().add(currentDB);
        testTable.getColumns().add(validDB);
        testTable.getColumns().add(staleDB);
    }

    private void init() {
        jdbc = new Jdbc();
        jdbc.loadDriver();
        jdbc.establishConnection();
        testTable.setVisible(false);
    }

    @FXML
    private void switchView() {
        if (db) {
            testTable.setVisible(true);
            table.setVisible(false);
            reload();
            db = false;
        } else {
            testTable.setVisible(false);
            table.setVisible(true);
            db = true;
        }
    }

    @FXML
    private void reload() {
        jsonWorkers.clear();
        workers.clear();
        testWorkers.clear();
        convertWorkers();
        jsonWorkers.addAll(Util.getWorkers());
        testWorkers.addAll(jdbc.getDbEntries());
    }

    @FXML
    private void dbEntry() {
        for (Worker worker : workers) {
            System.out.println(worker.getWorker());
            jdbc.insert(worker);
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy/HH:mm:ss");
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
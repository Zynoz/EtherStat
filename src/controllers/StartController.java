package controllers;

import businesslogic.Jdbc;
import businesslogic.JsonWorker;
import businesslogic.Util;
import businesslogic.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    private ObservableList<JsonWorker> jsonWorkers = FXCollections.observableArrayList();
    private ObservableList<Worker> workers = FXCollections.observableArrayList();
    private ObservableList<Worker> dbEntries = FXCollections.observableArrayList();
    private ObservableList<String> workerNames = FXCollections.observableArrayList();

    private Jdbc jdbc;
    private boolean db = true;

    @FXML
    private TableView table;

    @FXML
    private TableView dbTable;

    @FXML
    private ComboBox dropDown;

    @FXML
    private Label calcAvg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        TableColumn workerName = new TableColumn("Name");
        TableColumn avg = new TableColumn("Average");
        TableColumn current = new TableColumn("Current");
        TableColumn valid = new TableColumn("Valid");
        TableColumn stale = new TableColumn("Stale");
        TableColumn lastSeen = new TableColumn("Last Seen");
        TableColumn time = new TableColumn("Time");

        TableColumn uuidDB = new TableColumn("ID");
        TableColumn workerNameDB = new TableColumn("Name");
        TableColumn avgDB = new TableColumn("Average");
        TableColumn currentDB = new TableColumn("Current");
        TableColumn validDB = new TableColumn("Valid");
        TableColumn staleDB = new TableColumn("Stale");
        TableColumn<Worker, String> lastSeenDB = new TableColumn<>("Last Seen");
        TableColumn<Worker, String> timeDB = new TableColumn<>("Time");

        workerName.setCellValueFactory(new PropertyValueFactory<Worker, String>("worker"));
        avg.setCellValueFactory(new PropertyValueFactory<Worker, String>("averageHashrate"));
        current.setCellValueFactory(new PropertyValueFactory<Worker, String>("currentHashrate"));
        valid.setCellValueFactory(new PropertyValueFactory<Worker, String>("validShares"));
        stale.setCellValueFactory(new PropertyValueFactory<Worker, String>("staleShares"));
        lastSeen.setCellValueFactory(new PropertyValueFactory<Worker, String>("lastSeen"));
        time.setCellValueFactory(new PropertyValueFactory<Worker, String>("time"));

        uuidDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("id"));
        workerNameDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("worker"));
        avgDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("averageHashrate"));
        currentDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("currentHashrate"));
        validDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("validShares"));
        staleDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("staleShares"));
        lastSeenDB.setCellValueFactory(new PropertyValueFactory<>("lastSeen"));
        timeDB.setCellValueFactory(new PropertyValueFactory<>("time"));

        table.setItems(workers);
        table.getColumns().add(workerName);
        table.getColumns().add(avg);
        table.getColumns().add(current);
        table.getColumns().add(valid);
        table.getColumns().add(stale);
        table.getColumns().add(lastSeen);
        table.getColumns().add(time);

        dbTable.setItems(dbEntries);
        dbTable.getColumns().add(uuidDB);
        dbTable.getColumns().add(workerNameDB);
        dbTable.getColumns().add(avgDB);
        dbTable.getColumns().add(currentDB);
        dbTable.getColumns().add(validDB);
        dbTable.getColumns().add(staleDB);
    }

    private void init() {
        dropDown.setItems(workerNames);
        table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> test((Worker) newValue)));
        dbTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> test((Worker) newValue)));
        jsonWorkers.addAll(Util.getWorkers());
        workers.clear();
        convertWorkers();
        jdbc = new Jdbc();
        jdbc.loadDriver();
        jdbc.establishConnection();
        dbTable.setVisible(false);
        getWorkerNames();
    }

    private void test(Worker worker) {
        if (worker != null) {
            for (String w : workerNames) {
                if (worker.getWorker().equals(w)) {
                    dropDown.getSelectionModel().select(w);
                }
            }
        }
    }

    @FXML
    private void switchView() {
        if (db) {
            dbEntries.clear();
            dbEntries.addAll(jdbc.getDbEntries());
            dbTable.setVisible(true);
            table.setVisible(false);
            db = false;
        } else {
            reload();
            dbTable.setVisible(false);
            table.setVisible(true);
            db = true;
        }
    }

    @FXML
    private void reload() {
        jsonWorkers.clear();
        workers.clear();
        jsonWorkers.addAll(Util.getWorkers());
        convertWorkers();
    }

    @FXML
    private void dbEntry() {
        for (Worker worker : workers) {
            System.out.println(worker.getWorker());
            jdbc.insert(worker);
        }
    }

    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private void chooseMiner() {

    }

    @FXML
    private void about() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/about.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("About");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
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
                workers.add(new Worker(0, json.getWorker(), time, lastSeen, json.getReportedHashrate(), json.getCurrentHashrate().setScale(2, RoundingMode.DOWN), json.getValidShares(), json.getInvalidShares(), json.getStaleShares(), json.getAverageHashrate().setScale(2, RoundingMode.DOWN)));
            } else {
                workers.add(new Worker(0, json.getWorker(), time, lastSeen, json.getReportedHashrate(), json.getCurrentHashrate(), json.getValidShares(), json.getInvalidShares(), json.getStaleShares(), json.getAverageHashrate().setScale(2, RoundingMode.DOWN)));
            }
        }
    }

    private void getWorkerNames() {
        for (Worker worker : workers) {
            workerNames.add(worker.getWorker());
        }
    }
    public void calculateAvg() {
        int index = dropDown.getSelectionModel().getSelectedIndex();
        int count = 0;
        double sum = 0;
        for (Worker w : jdbc.getDbEntries()) {
            if (workerNames.get(index).equals(w.getWorker())) {
                sum += w.getCurrentHashrate().doubleValue();
                count++;
            }
        }
        double avg = sum / count;
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        calcAvg.setText(decimalFormat.format(avg) + " MH/s");
    }
}
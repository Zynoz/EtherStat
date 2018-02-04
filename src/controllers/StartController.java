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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    private ObservableList<JsonWorker> jsonWorkers = FXCollections.observableArrayList();
    private ObservableList<Worker> workers = FXCollections.observableArrayList();
    private ObservableList<Worker> dbEntries = FXCollections.observableArrayList();
    private ObservableList<String> workerNames = FXCollections.observableArrayList();

    private Jdbc jdbc;
    private boolean db = true;
    private String minerAddress = "7b1101df6f19c9c6fa5a2b4d2c579aeb52de07b9";

    @FXML
    private Button viewDB;

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
    }

    private void init() {
        dropDown.setItems(workerNames);

        table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectWorker((Worker) newValue)));
        dbTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectWorker((Worker) newValue)));
        jsonWorkers.addAll(Util.getWorkers(minerAddress));
        workers.clear();
        //convertWorkers();
        jdbc = new Jdbc();
        dbTable.setVisible(false);
        getWorkerNames();

        TableColumn workerName = new TableColumn("Name");
        TableColumn avg = new TableColumn("Average");
        TableColumn current = new TableColumn("Current");
        TableColumn valid = new TableColumn("Valid");
        TableColumn stale = new TableColumn("Stale");
        TableColumn time = new TableColumn("Timestamp");

        TableColumn uuidDB = new TableColumn("ID");
        TableColumn workerNameDB = new TableColumn("Name");
        TableColumn avgDB = new TableColumn("Average");
        TableColumn currentDB = new TableColumn("Current");
        TableColumn validDB = new TableColumn("Valid");
        TableColumn staleDB = new TableColumn("Stale");
        TableColumn timeDB = new TableColumn("Timestamp");

        workerName.setStyle( "-fx-alignment: CENTER;");
        avg.setStyle( "-fx-alignment: CENTER;");
        current.setStyle( "-fx-alignment: CENTER;");
        valid.setStyle( "-fx-alignment: CENTER;");
        stale.setStyle( "-fx-alignment: CENTER;");
        time.setStyle( "-fx-alignment: CENTER;");

        uuidDB.setStyle( "-fx-alignment: CENTER;");
        workerNameDB.setStyle( "-fx-alignment: CENTER;");
        avgDB.setStyle( "-fx-alignment: CENTER;");
        currentDB.setStyle( "-fx-alignment: CENTER;");
        validDB.setStyle( "-fx-alignment: CENTER;");
        staleDB.setStyle( "-fx-alignment: CENTER;");
        timeDB.setStyle( "-fx-alignment: CENTER;");

        workerName.setCellValueFactory(new PropertyValueFactory<Worker, String>("worker"));
        current.setCellValueFactory(new PropertyValueFactory<Worker, String>("currentHashrate"));
        avg.setCellValueFactory(new PropertyValueFactory<Worker, String>("averageHashrate"));
        valid.setCellValueFactory(new PropertyValueFactory<Worker, String>("validShares"));
        stale.setCellValueFactory(new PropertyValueFactory<Worker, String>("staleShares"));
        time.setCellValueFactory(new PropertyValueFactory<Worker, String>("timest"));

        uuidDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("id"));
        workerNameDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("worker"));
        currentDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("currentHashrate"));
        avgDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("averageHashrate"));
        validDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("validShares"));
        staleDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("staleShares"));
        timeDB.setCellValueFactory(new PropertyValueFactory<Worker, String>("timest"));

        table.setItems(workers);
        table.getColumns().add(workerName);
        table.getColumns().add(current);
        table.getColumns().add(avg);
        table.getColumns().add(valid);
        table.getColumns().add(stale);
        table.getColumns().add(time);

        dbTable.setItems(dbEntries);
        dbTable.getColumns().add(uuidDB);
        dbTable.getColumns().add(workerNameDB);
        dbTable.getColumns().add(currentDB);
        dbTable.getColumns().add(avgDB);
        dbTable.getColumns().add(validDB);
        dbTable.getColumns().add(staleDB);
        dbTable.getColumns().add(time);


        dropDown.getSelectionModel().selectFirst();

        Runnable runnable = () -> {
            while (true) {
                dbEntry();
                System.out.println("done");
                try {
                    Thread.sleep(600000);
                    //workers.forEach(System.out::println);
                    //Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void selectWorker(Worker worker) {
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
            reload();
            dbTable.setVisible(true);
            table.setVisible(false);
            viewDB.setText("show recent");
            db = false;
            dropDown.getSelectionModel().selectFirst();
        } else {
            reload();
            dbTable.setVisible(false);
            table.setVisible(true);
            viewDB.setText("show database");
            db = true;
            dropDown.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void reload() {
        dbEntries.clear();
        jsonWorkers.clear();
        workers.clear();
        workerNames.clear();
        jsonWorkers.addAll(Util.getWorkers(minerAddress));
        convertWorkers();
        dbEntries.addAll(jdbc.getDbEntries());
        getWorkerNames();
    }

    @FXML
    private void dbEntry() {
        for (Worker worker : workers) {
            jdbc.insert(worker);
        }
    }

    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private void chooseMiner() {
        TextInputDialog textInputDialog = new TextInputDialog("7b1101df6f19c9c6fa5a2b4d2c579aeb52de07b9");
        textInputDialog.setTitle("Choose Miner");
        textInputDialog.setHeaderText("Choose a miner");
        textInputDialog.setContentText("Please enter a miner address");
        Optional<String> result = textInputDialog.showAndWait();
        result.ifPresent(name -> minerAddress = name);
    }

    @FXML
    private void setIP() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Choose database server");
        textInputDialog.setContentText("Enter IP or Domain of database server");
        Optional<String> result = textInputDialog.showAndWait();
        result.ifPresent(ip -> jdbc.setIP(ip));
    }

    @FXML
    private void setUsername() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Choose username for database");
        textInputDialog.setContentText("Enter username to connect to the database server");
        Optional<String> result = textInputDialog.showAndWait();
        result.ifPresent(username -> jdbc.setUsername(username));
    }

    @FXML
    private void setPassword() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Choose password for database");
        textInputDialog.setContentText("Enter password for the specified username");
        Optional<String> result = textInputDialog.showAndWait();
        result.ifPresent(pw -> jdbc.setPassword(pw));
    }

    @FXML
    private void connectDB() {
        jdbc.loadDriver();
        jdbc.establishConnection();
        reload();
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
            if (json.getCurrentHashrate() != null) {
                workers.add(new Worker(0, json.getWorker(), json.getReportedHashrate(), json.getCurrentHashrate().setScale(2, RoundingMode.DOWN), json.getValidShares(), json.getInvalidShares(), json.getStaleShares(), json.getAverageHashrate().setScale(2, RoundingMode.DOWN), Timestamp.valueOf(LocalDateTime.now())));
            } else {
                workers.add(new Worker(0, json.getWorker(), json.getReportedHashrate(), json.getCurrentHashrate(), json.getValidShares(), json.getInvalidShares(), json.getStaleShares(), json.getAverageHashrate().setScale(2, RoundingMode.DOWN), Timestamp.valueOf(LocalDateTime.now())));
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
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        calcAvg.setText(decimalFormat.format(avg) + " MH/s");
    }
}
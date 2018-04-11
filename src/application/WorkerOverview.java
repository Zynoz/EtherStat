package application;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Worker;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class WorkerOverview extends TableView<Worker> {
    private TableColumn<Worker, String> name;
    private TableColumn<Worker, BigDecimal> currentHashrate;
    private TableColumn<Worker, Integer> validShares, staleShares;
    private TableColumn<Worker, Timestamp> timeStamp;

    public WorkerOverview() {
        initAndAdd();
        setFactory();
    }

    private void setFactory() {
        name.setCellValueFactory(new PropertyValueFactory<>("worker"));
        currentHashrate.setCellValueFactory(new PropertyValueFactory<>("currentHashrate"));
        validShares.setCellValueFactory(new PropertyValueFactory<>("validShares"));
        staleShares.setCellValueFactory(new PropertyValueFactory<>("staleShares"));
        timeStamp.setCellValueFactory(new PropertyValueFactory<>("timest"));
    }

    private void initAndAdd() {
        name = new TableColumn<>("Name");
        currentHashrate = new TableColumn<>("Current");
        validShares = new TableColumn<>("Valid");
        staleShares = new TableColumn<>("Stale");
        timeStamp = new TableColumn<>("Timestamp");

        //noinspection unchecked
        getColumns().addAll(name, currentHashrate, validShares, staleShares, timeStamp);
    }

    public void updateWorkers(ArrayList<Worker> workers){
        getItems().addAll(workers);
    }
}